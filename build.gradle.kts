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
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("openApiVersion")}")
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:${property("hibernateJpaMetaModelVersion")}")
    implementation("com.opencsv:opencsv:${property("openCsvVersion")}")
    implementation ("org.mapstruct:mapstruct:${property("mapstructVersion")}")
    annotationProcessor ("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")
    implementation("org.apache.commons:commons-lang3:${property("apacheCommonsLang3")}")


}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless.java {
    googleJavaFormat()
}