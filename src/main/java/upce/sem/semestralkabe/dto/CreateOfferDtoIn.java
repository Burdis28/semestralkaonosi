package upce.sem.semestralkabe.dto;

import java.util.List;
import upce.sem.semestralkabe.schema.Toy;

public class CreateOfferDtoIn {

  private String caption;
  private String description;
  private List<Long> toys;
  private String username;

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

  public List<Long> getToys() {
    return toys;
  }

  public void setToys(List<Long> toys) {
    this.toys = toys;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
