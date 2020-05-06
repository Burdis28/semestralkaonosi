package upce.sem.semestralkabe.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import upce.sem.semestralkabe.schema.Bid;
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
    Query query = entityManager.createQuery("SELECT e FROM Bid e");
    return query.getResultList();
  }

  @Transactional
  public List<Bid> getAllBidsForOffer(Long offerId) {
    Query q = entityManager.createQuery(
        "SELECT u FROM Bid u WHERE u.offerId = :offerId");
    q.setParameter("offerId", offerId);
    return q.getResultList();
  }

  @Transactional
  public List<Bid> getAllBidsOfUser(int userId, boolean active) {
    if (active) {
      Query query = entityManager.createQuery("SELECT e FROM Bid e where e.active = :active and e.user.id = :userId");
      query.setParameter("active", true);
      query.setParameter("userId", userId);
      return query.getResultList();
    } else {
      Query query = entityManager.createQuery("SELECT e FROM Bid e where e.user.id = :userId");
      query.setParameter("userId", userId);
      return query.getResultList();
    }
  }

  @Override
  @Transactional
  public void save(Bid bid) {
    entityManager.persist(bid);
  }

  @Override
  @Transactional
  public void update(Bid bid) {
    entityManager.merge(bid);
  }

  @Override
  @Transactional
  public void delete(Bid bid) {
    entityManager.remove(bid);
  }

}
