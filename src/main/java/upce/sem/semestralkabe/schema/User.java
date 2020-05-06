package upce.sem.semestralkabe.schema;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;

  @Id
  private String username;

  private String password;
  private String name;
  private String phoneNumber;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
  private List<Offer> offers;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Offer> biddedOffers;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Bid> bids;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Toy> toys;

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

  public List<Bid> getBids() {
    return bids;
  }

  public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

  public void addOffer(Offer offer) {
    if(offers != null) {
      offers.add(offer);
    } else {
      offers = new ArrayList<>();
      offers.add(offer);
    }
  }

  public void addBid(Bid bid) {
    if(bids != null) {
      bids.add(bid);
    } else {
      bids = new ArrayList<>();
      bids.add(bid);
    }
  }

  public void addToy(Toy toy) {
    if (toys != null) {
      toys.add(toy);
    } else {
      toys = new ArrayList<>();
      toys.add(toy);
    }
  }
}
