import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Book extends LibraryItem {
  private String author;
  public static final String DATABASE_TYPE = "book";

  public Book(String title, String author, String genre) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    type = DATABASE_TYPE;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO libraryItems (title, author, genre, type) VALUES (:title, :author, :genre, 'book')";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("author", this.author)
      .addParameter("genre", this.genre)
      .executeUpdate()
      .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE libraryItems SET title = :title, author = :author, genre = :genre WHERE id = :id";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("title", this.title)
      .addParameter("author", this.author)
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

  public static List<Book> search(String search) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems WHERE title ~* :search OR author ~* :search OR genre ~* :search";
    return con.createQuery(sql)
      .addParameter("search", ".*" + search + ".*")
      .throwOnMappingFailure(false)
      .executeAndFetch(Book.class);
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems where id=:id";
    Book book = con.createQuery(sql)
      .addParameter("id", id)
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(Book.class);
    return book;
    }
  }

  public static List<Book> findOverdue() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM libraryItems WHERE dueDate < now()";
    return con.createQuery(sql)
      .executeAndFetch(Book.class);
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM libraryItems WHERE type = 'book'";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Book.class);
    }
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getId() == newBook.getId();
    }
  }

}
