package org.example.repository;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import org.apache.commons.lang3.ObjectUtils;
import org.example.util.BatchExecutor;
import org.example.util.SpringContext;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NEVER)
public class AppBaseRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements BatchRepository<T, ID> {

  private final EntityManager entityManager;

  public AppBaseRepository(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public <S extends T> void saveInBatch(Iterable<S> entities) {
    if (ObjectUtils.isEmpty(entities)) {
      throw new IllegalArgumentException("Entities can not be null");
    }
    SpringContext.getBean(BatchExecutor.class).saveInBatch(entities);
  }
}
