package upce.sem.semestralkabe.schema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class DBFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  private String fileName;

  private String fileType;

  @Lob
  private byte[] data;

  public DBFile() {

  }

  public DBFile(String fileName, String fileType, byte[] data) {
    this.fileName = fileName;
    this.fileType = fileType;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
}
