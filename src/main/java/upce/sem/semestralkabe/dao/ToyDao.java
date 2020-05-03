package upce.sem.semestralkabe.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import upce.sem.semestralkabe.schema.Toy;
import org.springframework.stereotype.Repository;

@Repository
public class ToyDao implements Dao<Toy> {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public Optional<Toy> get(long id) {
    return Optional.ofNullable(entityManager.find(Toy.class, id));
  }

  @Override
  @Transactional
  public List<Toy> getAll() {
    Query query = entityManager.createQuery("SELECT e FROM Toy e");
    return query.getResultList();
  }

  @Override
  @Transactional
  public void save(Toy toy) {
    executeInsideTransaction(entityManager -> entityManager.persist(toy));
  }

  @Override
  @Transactional
  public void update(Toy toy) {
    executeInsideTransaction(entityManager -> entityManager.merge(toy));
  }

  @Override
  @Transactional
  public void delete(Toy toy) {
    executeInsideTransaction(entityManager -> entityManager.remove(toy));
  }

  private void executeInsideTransaction(Consumer<EntityManager> action) {
    EntityTransaction tx = entityManager.getTransaction();
    try {
      tx.begin();
      action.accept(entityManager);
      tx.commit();
    }
    catch (RuntimeException e) {
      tx.rollback();
      throw e;
    }
  }
}
