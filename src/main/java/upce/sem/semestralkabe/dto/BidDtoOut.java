package upce.sem.semestralkabe.dto;

import java.util.List;
import upce.sem.semestralkabe.schema.Toy;

public class BidDtoOut {

  private Long id;
  private Long offerId;
  private String nameOfPerson;
  private String caption;
  private String description;
  private Boolean active;
  private List<ToyDtoOut> toys;

  public BidDtoOut(Long id, Long offerId, String nameOfPerson, String caption, String description,Boolean active, List<ToyDtoOut> toys) {
    this.id = id;
    this.offerId = offerId;
    this.nameOfPerson = nameOfPerson;
    this.caption = caption;
    this.description = description;
    this.active = active;
    this.toys = toys;
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

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public List<ToyDtoOut> getToys() {
    return toys;
  }

  public void setToys(List<ToyDtoOut> toys) {
    this.toys = toys;
  }
}
