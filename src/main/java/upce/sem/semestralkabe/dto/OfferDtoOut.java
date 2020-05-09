package upce.sem.semestralkabe.dto;

import java.util.List;

public class OfferDtoOut {

  private Long offerId;
  private String nameOfPerson;
  private String caption;
  private String description;
  private Boolean active;
  private Long winner;
  private List<ToyDtoOut> toys;
  private String username;

  public OfferDtoOut() {

  }

  public OfferDtoOut(Long offerId, String nameOfPerson, String caption, String description, Boolean active, Long winner, List<ToyDtoOut> toys, String username) {
    this.offerId = offerId;
    this.nameOfPerson = nameOfPerson;
    this.caption = caption;
    this.description = description;
    this.active = active;
    this.winner = winner;
    this.toys = toys;
    this.username = username;
  }

  public Long getOfferId() {
    return offerId;
  }

  public void setOfferId(Long offerId) {
    this.offerId = offerId;
  }

  public String getNameOfPerson() {
    return nameOfPerson;
  }

  public void setNameOfPerson(String nameOfPerson) {
    this.nameOfPerson = nameOfPerson;
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

  public List<ToyDtoOut> getToys() {
    return toys;
  }

  public void setToys(List<ToyDtoOut> toys) {
    this.toys = toys;
  }

  public Long getWinner() {
    return winner;
  }

  public void setWinner(Long winner) {
    this.winner = winner;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
