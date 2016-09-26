import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class BookHistoriesTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void BookHistories_instantiatesCorrectly_true() {
    BookHistories testHistory = new BookHistories();
    assertEquals(true, testHistory instanceof BookHistories);
  }

  @Test
  public void all_returnsAllInstancesOfBookHistories_true() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    testBookHistories.save();
    BookHistories secondBookHistories = new BookHistories();
    secondBookHistories.checkOut(1, 1);
    secondBookHistories.save();
    assertTrue(BookHistories.all().get(0).equals(testBookHistories));
    assertTrue(BookHistories.all().get(1).equals(secondBookHistories));
  }

  @Test
  public void getId_bookHistoriesInstantiateWithAnId() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.save();
    assertTrue(testBookHistories.getId() > 0);
  }

  @Test
  public void find_returnsBookHistoriesWithSameId_secondBookHistories() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.save();
    BookHistories secondBookHistories = new BookHistories();
    secondBookHistories.save();
    assertEquals(BookHistories.find(secondBookHistories.getId()), secondBookHistories);
  }

  @Test
  public void equals_returnsTrueIfBookHistoriesAreTheSame() {
    BookHistories testBookHistories = new BookHistories();
    BookHistories myBookHistories = new BookHistories();
    assertTrue(testBookHistories.equals(myBookHistories));
  }

  @Test
  public void save_assignsIdToObject() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.save();
    BookHistories savedBookHistories = BookHistories.all().get(0);
    assertEquals(testBookHistories.getId(), savedBookHistories.getId());
  }

  @Test
  public void save_savesBookHistoriesIdIntoDB_true() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.save();
    BookHistories savedBookHistories = BookHistories.find(testBookHistories.getId());
    assertEquals(savedBookHistories.getId(), testBookHistories.getId());
  }

  // @Test
  // public void update_updatesBookHistoriesDateReturned_true() {
  //   BookHistories testBookHistories = new BookHistories();
  //   testBookHistories.save();
  //   testBookHistories.update();
  //   assertEquals(true, BookHistories.find(testBookHistories.getId()));
  // }

  @Test
  public void checkOut_setsTheBookHistoriesId_Integer() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    Integer expected = 1;
    assertEquals(expected, (Integer) testBookHistories.getPersonId());
  }

  @Test
  public void checkOut_setsCheckoutDate_now() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    Timestamp expected = new Timestamp(new Date().getTime());
    assertEquals(expected.getDate(), testBookHistories.getDateCheckedOut().getDate());
  }

  @Test
  public void checkOut_setsDueDate_twoWeeks() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    Calendar calendar = Calendar.getInstance();
    Timestamp now = new Timestamp(new Date().getTime());
    calendar.setTime(now);
    calendar.add(Calendar.DAY_OF_WEEK, 14);
    Timestamp expected = new Timestamp(calendar.getTime().getTime());
    assertEquals(expected.getDate(), testBookHistories.getDueDate().getDate());
  }

  @Test
  public void checkOut_isCheckedOut_true() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    assertEquals(true, testBookHistories.isCheckedOut());
  }

  @Test
  public void checkIn_isCheckedOut_false() {
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    testBookHistories.checkIn();
    assertEquals(false, testBookHistories.isCheckedOut());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void extendCheckOut_throwsExceptionIfTimesCheckedOutIsAtMaxValue(){
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    for(int i = 0; i <= (BookHistories.MAX_CHECKOUTS); i++){
      testBookHistories.extendCheckOut();
    }
  }

  @Test
  public void book_checkOutsCannotGoBeyondMaxValue(){
    BookHistories testBookHistories = new BookHistories();
    testBookHistories.checkOut(1, 1);
    for(int i = 0; i <= (BookHistories.MAX_CHECKOUTS); i++){
      try {
        testBookHistories.extendCheckOut();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testBookHistories.getTimesCheckedOut() <= BookHistories.MAX_CHECKOUTS);
  }
}
