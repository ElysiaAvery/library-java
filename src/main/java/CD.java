import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class CD extends LibraryItem {
  private String artist;
  public static final String DATABASE_TYPE = "cd";

  public CD(String title, String artist, String genre) {
    this.title = title;
    this.artist = artist;
    this.genre = genre;
    type = DATABASE_TYPE;
  }

  public String getArtist() {
    return this.artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO libraryItems (title, artist, genre) VALUES (:title, :artist, :genre)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("artist", this.artist)
      .addParameter("genre", this.genre)
      .executeUpdate()
      .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE libraryItems SET title = :title, artist = :artist, genre = :genre WHERE id = :id";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("title", this.title)
      .addParameter("artist", this.artist)
      .addParameter("genre", this.genre)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM libraryItems where id = :id";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static List<CD> search(String search) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems WHERE title ~* :search OR artist ~* :search OR genre ~* :search";
    return con.createQuery(sql)
      .addParameter("search", ".*" + search + ".*")
      .throwOnMappingFailure(false)
      .executeAndFetch(CD.class);
    }
  }

  public static CD find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems where id=:id";
    CD book = con.createQuery(sql)
      .addParameter("id", id)
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(CD.class);
    return book;
    }
  }

  public static List<CD> findOverdue() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems WHERE dueDate < now()";
    return con.createQuery(sql)
      .executeAndFetch(CD.class);
    }
  }

  public static List<CD> all() {
    String sql = "SELECT * FROM libraryItems WHERE type = 'cd'";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(CD.class);
    }
  }

  @Override
  public boolean equals(Object otherCD){
    if (!(otherCD instanceof CD)) {
      return false;
    } else {
      CD newCD = (CD) otherCD;
      return this.getTitle().equals(newCD.getTitle()) &&
             this.getId() == newCD.getId();
    }
  }

}
