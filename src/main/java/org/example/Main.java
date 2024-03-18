package org.example;

import lombok.RequiredArgsConstructor;
import org.example.repository.AppBaseRepository;
import org.example.util.DataLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaRepositories(repositoryBaseClass = AppBaseRepository.class)
public class Main {

  @Value("${load.post-office:false}")
  private boolean requireToLoadData;

  private final DataLoader dataLoader;

  public static void main(String[] args) {
    SpringApplication.run(Main.class);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    if (this.requireToLoadData) {
      this.dataLoader.loadPostOfficeData();
    }
  }
}
