package schema;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "toys")
public class Toy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  //muzou byt i videa, nejspis teda
  @OneToMany(mappedBy="id")
  private List<DBFile> images;

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

  public List<DBFile> getImages() {
    return images;
  }

  public void setImages(List<DBFile> images) {
    this.images = images;
  }

  public void addImage(DBFile image) {
    if (images != null) {
      images.add(image);
    } else {
      images = new ArrayList<>();
      images.add(image);
    }
  }
}