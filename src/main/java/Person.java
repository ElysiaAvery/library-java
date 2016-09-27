import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Date;

public class Person {
  private int id;
  private String name;
  private List<Object> allLibraryItems;
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

  public void setName(String name) {
    this.name = name;
  }

  public List<LibraryItem> getAllLibraryItems() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT libraryItems.* FROM libraryItems LEFT JOIN bookhistories ON libraryItems.Id = bookhistories.bookId WHERE history.personId = :personId";
      return con.createQuery(sql)
      .executeAndFetch(LibraryItem.class);
    }
  }

  public List<Person> getOverduePatrons() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT persons.* FROM persons LEFT JOIN libraryItems ON libraryItems.personId = persons.Id WHERE libraryItems.dueDate > now()";
      return con.createQuery(sql)
      .executeAndFetch(Person.class);
    }
  }

  public List<Object> getLibraryItems() {
    List<Object> allLibraryItems = new ArrayList<Object>();
    try(Connection con = DB.sql2o.open()) {
      String sqlBook = "SELECT libraryItems.* FROM libraryItems LEFT JOIN bookHistories ON bookHistories.libraryItemsId = libraryItems.id WHERE bookHistories.personId = :id AND type='book';";
      List<Book> books = con.createQuery(sqlBook)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Book.class);
      allLibraryItems.addAll(books);

      String sqlCD = "SELECT libraryItems.* FROM libraryItems LEFT JOIN bookHistories ON bookHistories.libraryItemsId = libraryItems.id WHERE bookHistories.personId = :id AND type='cd';";
      List<CD> cds = con.createQuery(sqlCD)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(CD.class);
      allLibraryItems.addAll(cds);

      String sqlMagazine = "SELECT libraryItems.* FROM libraryItems LEFT JOIN bookHistories ON bookHistories.libraryItemsId = libraryItems.id WHERE bookHistories.personId = :id AND type='magazine';";
      List<Magazine> magazines = con.createQuery(sqlMagazine)
      .addParameter("id", this.id)
      .throwOnMappingFailure(false)
      .executeAndFetch(Magazine.class);
      allLibraryItems.addAll(magazines);
    }
    return allLibraryItems;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO persons (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE persons SET name = :name WHERE id = :id";
        con.createQuery(sql)
          .addParameter("name", this.name)
          .addParameter("id", this.id)
          .executeUpdate();
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
    try(Connection con = DB.sql2o.open()) {
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

  // private long twoWeeksFromNow() {
  //   Calendar calendar = Calendar.getInstance();
  //   Timestamp now = new Timestamp(new Date().getTime());
  //   calendar.setTime(now);
  //   calendar.add(Calendar.DAY_OF_WEEK, 14);
  //   return calendar.getTime().getTime();
  // }
  //
  // public void checkOut(Integer personId) {
  //   this.personId = personId;
  //   this.checkedOut = new Timestamp(new Date().getTime());
  //   this.timesCheckedOut = 0;
  //   this.dueDate = new Timestamp(twoWeeksFromNow());
  // }
  //
  // public boolean extendCheckOut() {
  //   if(timesCheckedOut < this.MAX_CHECKOUTS) {
  //     this.timesCheckedOut++;
  //     this.dueDate.setTime(twoWeeksFromNow());
  //     return true;
  //   } else {
  //     throw new UnsupportedOperationException("You cannot check out a book more than twice!");
  //   }
  // }

  @Override
  public boolean equals(Object otherPerson) {
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
