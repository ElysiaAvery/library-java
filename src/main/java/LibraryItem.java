import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public abstract class LibraryItem {
  public String type;
  public int id;
  public String title;
  public String genre;

  public int getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getGenre() {
    return this.genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO libraryItems (genre, title, type) VALUES (:genre, :title, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("genre", this.genre)
        .addParameter("title", this.title)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }




}
