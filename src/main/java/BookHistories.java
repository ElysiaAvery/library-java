import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class BookHistories {
  private int id;
  private Timestamp dateCheckedOut;
  private Timestamp dueDate;
  private Timestamp dateReturned;
  private int timesCheckedOut;
  private Integer personId;
  private Integer bookId;
  public static final int MAX_CHECKOUTS = 2;
  public static final int MAX_BOOKS = 5;

  public int getId() {
      return this.id;
  }

  public Integer getPersonId() {
    return this.personId;
  }

  public Integer getBookId() {
    return this.bookId;
  }

  public Timestamp getDateCheckedOut() {
    return this.dateCheckedOut;
  }

  public Timestamp getDueDate() {
    return this.dueDate;
  }

  public Timestamp getDateReturned() {
    return this.dateReturned;
  }

  public int getTimesCheckedOut() {
    return this.timesCheckedOut;
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
      String sql = "INSERT INTO bookhistories (dateCheckedOut, dueDate, dateReturned, personId, bookId) VALUES (:dateCheckedOut, :dueDate, :dateReturned, :personId, :bookId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("dateCheckedOut", dateCheckedOut)
        .addParameter("dueDate", dueDate)
        .addParameter("dateReturned", dateReturned)
        .addParameter("personId", personId)
        .addParameter("bookId", bookId)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bookhistories SET dateReturned = :dateReturned WHERE id = :id";
        con.createQuery(sql)
          .addParameter("dateReturned", this.dateReturned)
          .addParameter("id", this.id)
          .executeUpdate();
    }
  }

  public static BookHistories find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bookhistories WHERE id = :id";
      BookHistories bookHistory = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(BookHistories.class);
      return bookHistory;
    }
  }

  public static List<BookHistories> all() {
    String sql = "SELECT * FROM bookhistories";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(BookHistories.class);
    }
  }

  @Override
  public boolean equals(Object otherBookHistories) {
    if (!(otherBookHistories instanceof BookHistories)) {
      return false;
    } else {
      BookHistories newBookHistories = (BookHistories) otherBookHistories;
      return this.getPersonId() == newBookHistories.getPersonId() &&
             this.getId() == newBookHistories.getId() &&
             this.getBookId() == newBookHistories.getBookId();
    }
  }

  public boolean isCheckedOut() {
    if(this.dateReturned == null) {
      return true;
    } else {
      return false;
    }
  }

  public void checkIn() {
    this.dateReturned = new Timestamp(new Date().getTime());
  }

  private long twoWeeksFromNow() {
    Calendar calendar = Calendar.getInstance();
    Timestamp now = new Timestamp(new Date().getTime());
    calendar.setTime(now);
    calendar.add(Calendar.DAY_OF_WEEK, 14);
    return calendar.getTime().getTime();
  }

  public void checkOut(Integer personId, int bookId) {
    this.personId = personId;
    this.bookId = bookId;
    this.dateCheckedOut = new Timestamp(new Date().getTime());
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
}
