package org.example.util;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchExecutor<T> {

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
  private int batchSize;

  private final EntityManagerFactory entityManagerFactory;

  public <S extends T> void saveInBatch(Iterable<S> entities) {
    final var entityManager = this.entityManagerFactory.createEntityManager();
    final var transaction = entityManager.getTransaction();

    try {
      transaction.begin();
      int count = 0;
      for (var entity : entities) {
        if (count % batchSize == 0 && count > 0) {
          log.info("Flushing {} entities into database", batchSize);
          transaction.commit();
          transaction.begin();
          entityManager.clear();
        }
        entityManager.persist(entity);
        count++;
      }

    } catch (RuntimeException ex) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      throw ex;
    } finally {
      entityManager.close();
    }
  }
}
