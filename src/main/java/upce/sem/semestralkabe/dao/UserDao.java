package upce.sem.semestralkabe.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import upce.sem.semestralkabe.schema.User;
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
    Query query = entityManager.createQuery("SELECT e FROM User e");
    return query.getResultList();
  }

  @Transactional
  public User getByUsername(String username) {
    Query q = entityManager.createQuery(
        "SELECT u FROM User u WHERE u.username = :username");
    q.setParameter("username", username);
    return (User) q.getSingleResult();
  }

  @Override
  @Transactional
  public void save(User user) {
    entityManager.persist(user);
  }

  @Override
  @Transactional
  public void update(User user) {
    entityManager.merge(user);
  }

  @Override
  @Transactional
  public void delete(User user) {
    entityManager.remove(user);
  }


}
