package upce.sem.semestralkabe.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import upce.sem.semestralkabe.schema.Offer;
import org.springframework.stereotype.Repository;

@Repository
public class OfferDao implements Dao<Offer>{

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public Optional<Offer> get(long id) {
    return Optional.ofNullable(entityManager.find(Offer.class, id));
  }

  @Override
  @Transactional
  public List<Offer> getAll() {
    Query query = entityManager.createQuery("SELECT e FROM Offer e");
    return query.getResultList();
  }

  @Override
  @Transactional
  public void save(Offer offer) {
    executeInsideTransaction(entityManager -> entityManager.persist(offer));
  }

  @Override
  @Transactional
  public void update(Offer offer) {
    executeInsideTransaction(entityManager -> entityManager.merge(offer));
  }

  @Override
  @Transactional
  public void delete(Offer offer) {
    executeInsideTransaction(entityManager -> entityManager.remove(offer));
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
