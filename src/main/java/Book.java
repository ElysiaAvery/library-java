import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Book {
  private int id;
  private String title;
  private String author;
  private String genre;
  private Timestamp checkedOut;
  private Timestamp dueDate;
  private Integer personId;
  private int timesCheckedOut;


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

  public Integer getPersonId() {
    return this.personId;
  }

  public Timestamp getTimeCheckedOut() {
    return this.checkedOut;
  }

  public int getTimesCheckedOut() {
    return this.timesCheckedOut;
  }

  public Timestamp getDueDate() {
    return this.dueDate;
  }

  public boolean isCheckedOut() {
    if(this.personId != null) {
      return true;
    } else {
      return false;
    }
  }



  public void checkIn() {
    this.personId = null;
    this.checkedOut = null;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO books (title, author, genre, checkedOut, timesCheckedOut, dueDate, personId) VALUES (:title, :author, :genre, :checkedOut, :timesCheckedOut, :dueDate, :personId)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("author", this.author)
      .addParameter("genre", this.genre)
      .addParameter("checkedOut", this.checkedOut)
      .addParameter("timesCheckedOut", this.timesCheckedOut)
      .addParameter("dueDate", this.dueDate)
      .addParameter("personId", this.personId)
      .executeUpdate()
      .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE books SET title = :title, author = :author, genre = :genre, checkedOut = :checkedOut, timesCheckedOut = :timesCheckedOut, dueDate = :dueDate, personId = :personId WHERE id = :id";
    con.createQuery(sql)
      .addParameter("id", this.id)
      .addParameter("title", this.title)
      .addParameter("author", this.author)
      .addParameter("genre", this.genre)
      .addParameter("checkedOut", this.checkedOut)
      .addParameter("timesCheckedOut", this.timesCheckedOut)
      .addParameter("dueDate", this.dueDate)
      .addParameter("personId", this.personId)
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
