package upce.sem.semestralkabe.schema;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "offers")
public class Offer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String caption;
  private String description;
  private Boolean active;

  @OneToMany(mappedBy="id")
  private List<Toy> toys;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  private Long winnerBidId;

  public Offer() {
  }

  public Offer(String caption, String description, List<Toy> toys, User user) {
    this.caption = caption;
    this.description = description;
    this.toys = toys;
    this.user = user;
    this.active = true;
    this.winnerBidId = null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getWinner() {
    return winnerBidId;
  }

  public void setWinner(Long winner) {
    this.winnerBidId = winner;
  }
}