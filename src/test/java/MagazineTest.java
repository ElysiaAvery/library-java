import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class MagazineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    assertEquals(true, testMagazine instanceof Magazine);
  }

  @Test
  public void Magazine_instantiatesWithTitle_String() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    assertEquals("Title", testMagazine.getTitle());
  }

  @Test
  public void Magazine_instantiatesWithIssueNumber_String() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    assertEquals(1, testMagazine.getIssueNumber());
  }

  @Test
  public void Magazine_instantiatesWithGenre_String() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    assertEquals("Genre", testMagazine.getGenre());
  }

  @Test
  public void setTitle_setsTheTitle_String() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    testMagazine.setTitle("newTitle");
    assertEquals("newTitle", testMagazine.getTitle());
  }

  @Test
  public void setArtist_setsTheIssueNumber_2() {
    Magazine testMagazine = new Magazine("Artist", "Genre", 1);
    testMagazine.setIssueNumber(2);
    assertEquals(2, testMagazine.getIssueNumber());
  }

  @Test
  public void setGenre_setsTheGenre_String() {
    Magazine testMagazine = new Magazine("Artist", "Genre", 1);
    testMagazine.setGenre("newGenre");
    assertEquals("newGenre", testMagazine.getGenre());
  }

  @Test
  public void save_setsTheId_int() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    testMagazine.save();
    assertEquals(true, testMagazine.getId() > 0);
  }

  @Test
  public void save_savesTitle_Title() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    testMagazine.save();
    Magazine savedMagazine = Magazine.find(testMagazine.getId());
    assertEquals("Title", savedMagazine.getTitle());
  }

  @Test
  public void update_changesTheTitle_newTitle() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    testMagazine.save();
    testMagazine.setTitle("newTitle");
    testMagazine.update();
    Magazine savedMagazine = Magazine.find(testMagazine.getId());
    assertEquals("newTitle", savedMagazine.getTitle());
  }

  @Test
  public void delete_removesTheMagazine_null() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    testMagazine.save();
    testMagazine.delete();
    Magazine savedMagazine = Magazine.find(testMagazine.getId());
    assertEquals(null, savedMagazine);
  }

  @Test
  public void search_findsMagazineByTitle_foundMagazine() {
    Magazine testMagazine = new Magazine("Title", "Genre", 1);
    testMagazine.save();
    List<Magazine> foundMagazines = Magazine.search("title");
    assertEquals(true, foundMagazines.contains(testMagazine));
  }

  @Test
  public void all_returnsAllInstancesOfMagazine_true() {
    Magazine firstMagazine = new Magazine("Title", "Genre", 1);
    firstMagazine.save();
    Magazine secondMagazine = new Magazine("Title2", "Genre2", 2);
    secondMagazine.save();
    assertEquals(true, Magazine.all().get(0).equals(firstMagazine));
    assertEquals(true, Magazine.all().get(1).equals(secondMagazine));
  }

  @Test
  public void find_returnsMagazineWithSameId_secondMagazine() {
    Magazine firstMagazine = new Magazine("Title", "Genre", 1);
    firstMagazine.save();
    Magazine secondMagazine = new Magazine("Title2", "Genre2", 2);
    secondMagazine.save();
    assertEquals(Magazine.find(secondMagazine.getId()), secondMagazine);
  }

  @Test
  public void issueNumber_recordsIssueNumberInDatabase() {
    Magazine firstMagazine = new Magazine("Title", "Genre", 1);
    firstMagazine.save();
    firstMagazine.setIssueNumber(2);
    firstMagazine.update();
    assertEquals(2, firstMagazine.getIssueNumber());
  }


}
