package schema;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private int id;

  private String username;
  private String password;
  private String name;
  private String phoneNumber;

  @OneToMany(mappedBy="id")
  private List<Offer> offers;

  @OneToMany(mappedBy="id")
  private List<Offer> biddedOffers;

  public User() {

  }

  public User(String username, String password, String name, String phoneNumber, List<Offer> offers, List<Offer> biddedOffers) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.phoneNumber = phoneNumber;
    if(offers == null) {
      this.offers = new ArrayList<>();
    } else {
      this.offers = offers;
    }
    if(biddedOffers == null) {
      this.biddedOffers = new ArrayList<>();
    } else {
      this.biddedOffers = biddedOffers;
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  public List<Offer> getBiddedOffers() {
    return biddedOffers;
  }

  public void setBiddedOffers(List<Offer> biddedOffers) {
    this.biddedOffers = biddedOffers;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
