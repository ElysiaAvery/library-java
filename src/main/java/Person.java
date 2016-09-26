import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Date;

public class Person {
  private int id;
  private String name;
  private List<Book> allBooks;
  public static final int MAX_CHECKOUTS = 2;
  public static final int MAX_BOOKS = 5;
  private List<Person> overduePatrons;

  public Person(String name) {
    this.name=name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<Book> getAllBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books LEFT JOIN bookhistories ON books.Id = bookhistories.bookId WHERE history.personId = :personId";
      return con.createQuery(sql)
      .executeAndFetch(Person.class);
    }
  }

  public List<Person> getOverduePatrons() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT persons.* FROM persons LEFT JOIN books ON books.personId = persons.Id WHERE books.dueDate > now()"
      return con.createQuery(sql)
      .executeAndFetch(Person.class);
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE personId = :id";
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Book.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO (name) VALUE (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey()
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE persons SET name = :name WHERE id = :id";
        con.createQuery(sql)
          .addParameter("name", this.name)
          .addParameter("id", this.id)
          .executeUpdate(;)
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM persons WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static Person find(int id) {
    try(Connection con = DB.sql2o.opne()) {
      String sql = "SELECT * FROM persons WHERE id = :id";
      Person person = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Person.class);
      return person;
    }
  }

  public static List<Person> all() {
    String sql = "SELECT * FROM persons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Person.class);
    }
  }

  private long twoWeeksFromNow() {
    Calendar calendar = Calendar.getInstance();
    Timestamp now = new Timestamp(new Date().getTime());
    calendar.setTime(now);
    calendar.add(Calendar.DAY_OF_WEEK, 14);
    return calendar.getTime().getTime();
  }

  public void checkOut(Integer personId) {
    this.personId = personId;
    this.checkedOut = new Timestamp(new Date().getTime());
    this.timesCheckedOut = 0;
    this.dueDate = new Timestamp(twoWeeksFromNow());
  }

  public boolean extendCheckOut() {
    if(timesCheckedOut < this.MAX_CHECKOUTS) {
      this.timesCheckedOut++;
      this.dueDate.setTime(twoWeeksFromNow());
      return true;
    } else {
      throw new UnsupportedOperationException("You cannot check out a book more than twice!");
    }
  }

  @Override
  public boolean equals(otherPerson) {
    if (!(otherPerson instanceof Person)) {
      return false;
    } else {
      Person newPerson = (Person) otherPerson;
      return this.getName().equals(newPerson.getName()) &&
             this.getId() == newPerson.getId();
    }
  }

  public static List<Person> searchForPerson(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM persons WHERE name ~* :name";
      return con.createQuery(sql)
        .addParameter("name", ".*" + name + ".*")
        .executeAndFetch(Person.class);
    }
  }

}
