package upce.sem.semestralkabe.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "toys")
public class Toy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Boolean usable;

  @Column(columnDefinition = "LONGTEXT")
  private String image;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  @JsonIgnore
  private User user;

  public Toy() {
    usable = true;
  }

  public Toy(String name, User user) {
    this.name = name;
    this.user = user;
    usable = true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getUsable() {
    return usable;
  }

  public void setUsable(Boolean usable) {
    this.usable = usable;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
