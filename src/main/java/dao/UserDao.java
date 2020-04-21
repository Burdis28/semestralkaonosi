package dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import schema.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao implements Dao<User> {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public Optional<User> get(long id) {
    return Optional.ofNullable(entityManager.find(User.class, id));
  }

  @Override
  @Transactional
  public List<User> getAll() {
    Query query = entityManager.createQuery("SELECT e FROM users e");
    return query.getResultList();
  }


  public User getByUsername(String username) {
    Query q = entityManager.createQuery(
        "SELECT u FROM users u WHERE u.username = :username");
    q.setParameter("username", username);
    return (User) q.getSingleResult();
  }

  @Override
  @Transactional
  public void save(User user) {
    executeInsideTransaction(entityManager -> entityManager.persist(user));
  }

  @Override
  @Transactional
  public void update(User user) {
    executeInsideTransaction(entityManager -> entityManager.merge(user));
  }

  @Override
  @Transactional
  public void delete(User user) {
    executeInsideTransaction(entityManager -> entityManager.remove(user));
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
