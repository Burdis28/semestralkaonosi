package upce.sem.semestralkabe.schema;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
  private Boolean active;

  @OneToMany(mappedBy="id")
  private List<Toy> toys;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="user_id")
  private User user;

  public Bid() {

  }

  public Bid(Long offerId, String caption, String description, List<Toy> toys, User user) {
    this.offerId = offerId;
    this.caption = caption;
    this.description = description;
    this.toys = toys;
    this.user = user;
    this.active = true;
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

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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

  public void addToy(Toy toy) {
    if(toys != null) {
      toys.add(toy);
    } else {
      toys = new ArrayList<>();
      toys.add(toy);
    }
  }
}
