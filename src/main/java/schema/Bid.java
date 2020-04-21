package schema;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bids")
public class Bid {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long offerId;

  private String caption;
  private String description;

  @OneToMany(mappedBy="id")
  private List<Toy> toys;

  private User user;

  public Bid(Long offerId, String caption, String description, List<Toy> toys, User user) {
    this.offerId = offerId;
    this.caption = caption;
    this.description = description;
    this.toys = toys;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOfferId() {
    return offerId;
  }

  public void setOfferId(Long offerId) {
    this.offerId = offerId;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Toy> getToys() {
    return toys;
  }

  public void setToys(List<Toy> toys) {
    this.toys = toys;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
