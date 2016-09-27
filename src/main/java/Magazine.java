import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Magazine extends LibraryItem {
  private int issueNumber;
  public static final String DATABASE_TYPE = "magazine";

  public Magazine(String title, String genre, int issueNumber) {
    this.title = title;
    this.genre = genre;
    this.issueNumber = issueNumber;
    type = DATABASE_TYPE;
  }

  public int getIssueNumber() {
    return this.issueNumber;
  }

  public void setIssueNumber(int issueNumber) {
    this.issueNumber = issueNumber;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO libraryItems (title, issueNumber, genre, type) VALUES (:title, :issueNumber, :genre, 'magazine')";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("issueNumber", this.issueNumber)
      .addParameter("genre", this.genre)
      .executeUpdate()
      .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE libraryItems SET title = :title, issueNumber = :issueNumber, genre = :genre WHERE id = :id";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("title", this.title)
      .addParameter("issueNumber", this.issueNumber)
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

  public static List<Magazine> search(String search) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems WHERE title ~* :search OR genre ~* :search";
    return con.createQuery(sql)
      .addParameter("search", ".*" + search + ".*")
      .throwOnMappingFailure(false)
      .executeAndFetch(Magazine.class);
    }
  }

  public static Magazine find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems where id=:id";
    Magazine book = con.createQuery(sql)
      .addParameter("id", id)
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(Magazine.class);
    return book;
    }
  }

  public static List<Magazine> findOverdue() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems WHERE dueDate < now()";
    return con.createQuery(sql)
      .executeAndFetch(Magazine.class);
    }
  }

  public static List<Magazine> all() {
    String sql = "SELECT * FROM libraryItems WHERE type = 'magazine'";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Magazine.class);
    }
  }

  @Override
  public boolean equals(Object otherMagazine){
    if (!(otherMagazine instanceof Magazine)) {
      return false;
    } else {
      Magazine newMagazine = (Magazine) otherMagazine;
      return this.getTitle().equals(newMagazine.getTitle()) &&
             this.getId() == newMagazine.getId();
    }
  }

}
