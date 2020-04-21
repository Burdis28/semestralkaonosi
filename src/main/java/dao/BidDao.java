package dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import schema.Bid;
import org.springframework.stereotype.Repository;

@Repository
public class BidDao implements Dao<Bid>{

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public Optional<Bid> get(long id) {
    return Optional.ofNullable(entityManager.find(Bid.class, id));
  }

  @Override
  @Transactional
  public List<Bid> getAll() {
    Query query = entityManager.createQuery("SELECT e FROM bids e");
    return query.getResultList();
  }

  @Override
  @Transactional
  public void save(Bid bid) {
    executeInsideTransaction(entityManager -> entityManager.persist(bid));
  }

  @Override
  @Transactional
  public void update(Bid bid) {
    executeInsideTransaction(entityManager -> entityManager.merge(bid));
  }

  @Override
  @Transactional
  public void delete(Bid bid) {
    executeInsideTransaction(entityManager -> entityManager.remove(bid));
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
