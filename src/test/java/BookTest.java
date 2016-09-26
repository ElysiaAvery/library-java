import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    Book testBook = new Book("Title", "Author", "Genre");
    assertEquals(true, testBook instanceof Book);
  }

  @Test
  public void Book_instantiatesWithTitle_String() {
    Book testBook = new Book("Title", "Author", "Genre");
    assertEquals("Title", testBook.getTitle());
  }

  @Test
  public void Book_instantiatesWithAuthor_String() {
    Book testBook = new Book("Title", "Author", "Genre");
    assertEquals("Author", testBook.getAuthor());
  }

  @Test
  public void Book_instantiatesWithGenre_String() {
    Book testBook = new Book("Title", "Author", "Genre");
    assertEquals("Genre", testBook.getGenre());
  }

  @Test
  public void setTitle_setsTheTitle_String() {
    Book testBook = new Book("Title", "Author", "Genre");
    testBook.setTitle("newTitle");
    assertEquals("newTitle", testBook.getTitle());
  }

  @Test
  public void setAuthor_setsTheAuthor_String() {
    Book testBook = new Book("Author", "Author", "Genre");
    testBook.setAuthor("newAuthor");
    assertEquals("newAuthor", testBook.getAuthor());
  }

  @Test
  public void setGenre_setsTheGenre_String() {
    Book testBook = new Book("Genre", "Author", "Genre");
    testBook.setGenre("newGenre");
    assertEquals("newGenre", testBook.getGenre());
  }

  @Test
  public void save_setsTheId_int() {
    Book testBook = new Book("Title", "Author", "Genre");
    testBook.save();
    assertEquals(true, testBook.getId() > 0);
  }

  @Test
  public void save_savesTitle_Title() {
    Book testBook = new Book("Title", "Author", "Genre");
    testBook.save();
    Book savedBook = Book.find(testBook.getId());
    assertEquals("Title", savedBook.getTitle());
  }

  @Test
  public void update_changesTheTitle_newTitle() {
    Book testBook = new Book("Title", "Author", "Genre");
    testBook.save();
    testBook.setTitle("newTitle");
    testBook.update();
    Book savedBook = Book.find(testBook.getId());
    assertEquals("newTitle", savedBook.getTitle());
  }

  @Test
  public void delete_removesTheBook_null() {
    Book testBook = new Book("Title", "Author", "Genre");
    testBook.save();
    testBook.delete();
    Book savedBook = Book.find(testBook.getId());
    assertEquals(null, savedBook);
  }

  @Test
  public void search_findsBookByTitle_foundBook() {
    Book testBook = new Book("Title", "Author", "Genre");
    testBook.save();
    List<Book> foundBooks = Book.search("title");
    assertEquals(true, foundBooks.contains(testBook));
  }

  @Test
  public void all_returnsAllInstancesOfBook_true() {
    Book firstBook = new Book("Title", "Author", "Genre");
    firstBook.save();
    Book secondBook = new Book("Title2", "Author2", "Genre2");
    secondBook.save();
    assertEquals(true, Book.all().get(0).equals(firstBook));
    assertEquals(true, Book.all().get(1).equals(secondBook));
  }

  @Test
  public void find_returnsBookWithSameId_secondBook() {
    Book firstBook = new Book("Title", "Author", "Genre");
    firstBook.save();
    Book secondBook = new Book("Title2", "Author2", "Genre2");
    secondBook.save();
    assertEquals(Book.find(secondBook.getId()), secondBook);
  }


}
