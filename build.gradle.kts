plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0"
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Handle HTTP request
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Process database queries
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    // Help in removing bolier plate code gettters, setters, hashcode, equals etc.
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Test library for sring boot
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // For documentation Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("openApiVersion")}")

    // Entity metamodel generation
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:${property("hibernateJpaMetaModelVersion")}")

    // Process csv files
    implementation("com.opencsv:opencsv:${property("openCsvVersion")}")

    // Java bean mapping
    implementation ("org.mapstruct:mapstruct:${property("mapstructVersion")}")
    annotationProcessor ("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")

    // Common utility library
    implementation("org.apache.commons:commons-lang3:${property("apacheCommonsLang3")}")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless.java {
    googleJavaFormat()
}