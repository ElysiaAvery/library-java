import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Book {
  private int id;
  private String title;
  private String author;
  private String genre;

  public Book(String title, String author, String genre) {
    this.title = title;
    this.author = author;
    this.genre = genre;
  }

  public int getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getGenre() {
    return this.genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO books (title, author, genre) VALUES (:title, :author, :genre)";
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
    String sql = "UPDATE books SET title = :title, author = :author, genre = :genre WHERE id = :id";
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
    String sql = "DELETE FROM books where id = :id";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static List<Book> search(String search) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM books WHERE title ~* :search OR author ~* :search OR genre ~* :search";
    return con.createQuery(sql)
      .addParameter("search", ".*" + search + ".*")
      .executeAndFetch(Book.class);
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM books where id=:id";
    Book book = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Book.class);
    return book;
    }
  }

  public static List<Book> findOverdue() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM books WHERE dueDate < now()";
    return con.createQuery(sql)
      .executeAndFetch(Book.class);
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
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
