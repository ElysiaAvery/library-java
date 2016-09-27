import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class CDTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    CD testCD = new CD("Title", "Artist", "Genre");
    assertEquals(true, testCD instanceof CD);
  }

  @Test
  public void CD_instantiatesWithTitle_String() {
    CD testCD = new CD("Title", "Artist", "Genre");
    assertEquals("Title", testCD.getTitle());
  }

  @Test
  public void CD_instantiatesWithArtist_String() {
    CD testCD = new CD("Title", "Artist", "Genre");
    assertEquals("Artist", testCD.getArtist());
  }

  @Test
  public void CD_instantiatesWithGenre_String() {
    CD testCD = new CD("Title", "Artist", "Genre");
    assertEquals("Genre", testCD.getGenre());
  }

  @Test
  public void setTitle_setsTheTitle_String() {
    CD testCD = new CD("Title", "Artist", "Genre");
    testCD.setTitle("newTitle");
    assertEquals("newTitle", testCD.getTitle());
  }

  @Test
  public void setArtist_setsTheArtist_String() {
    CD testCD = new CD("Artist", "Artist", "Genre");
    testCD.setArtist("newArtist");
    assertEquals("newArtist", testCD.getArtist());
  }

  @Test
  public void setGenre_setsTheGenre_String() {
    CD testCD = new CD("Genre", "Artist", "Genre");
    testCD.setGenre("newGenre");
    assertEquals("newGenre", testCD.getGenre());
  }

  @Test
  public void save_setsTheId_int() {
    CD testCD = new CD("Title", "Artist", "Genre");
    testCD.save();
    assertEquals(true, testCD.getId() > 0);
  }

  @Test
  public void save_savesTitle_Title() {
    CD testCD = new CD("Title", "Artist", "Genre");
    testCD.save();
    CD savedCD = CD.find(testCD.getId());
    assertEquals("Title", savedCD.getTitle());
  }

  @Test
  public void update_changesTheTitle_newTitle() {
    CD testCD = new CD("Title", "Artist", "Genre");
    testCD.save();
    testCD.setTitle("newTitle");
    testCD.update();
    CD savedCD = CD.find(testCD.getId());
    assertEquals("newTitle", savedCD.getTitle());
  }

  @Test
  public void delete_removesTheCD_null() {
    CD testCD = new CD("Title", "Artist", "Genre");
    testCD.save();
    testCD.delete();
    CD savedCD = CD.find(testCD.getId());
    assertEquals(null, savedCD);
  }

  @Test
  public void search_findsCDByTitle_foundCD() {
    CD testCD = new CD("Title", "Artist", "Genre");
    testCD.save();
    List<CD> foundCDs = CD.search("title");
    assertEquals(true, foundCDs.contains(testCD));
  }

  @Test
  public void all_returnsAllInstancesOfCD_true() {
    CD firstCD = new CD("Title", "Artist", "Genre");
    firstCD.save();
    CD secondCD = new CD("Title2", "Artist2", "Genre2");
    secondCD.save();
    assertEquals(true, CD.all().get(0).equals(firstCD));
    assertEquals(true, CD.all().get(1).equals(secondCD));
  }

  @Test
  public void find_returnsCDWithSameId_secondCD() {
    CD firstCD = new CD("Title", "Artist", "Genre");
    firstCD.save();
    CD secondCD = new CD("Title2", "Artist2", "Genre2");
    secondCD.save();
    assertEquals(CD.find(secondCD.getId()), secondCD);
  }

  @Test
  public void artist_recordsArtistInDatabase() {
    CD firstCD = new CD("Title", "Artist", "Genre");
    firstCD.save();
    firstCD.setArtist("new artist");
    firstCD.update();
    assertEquals("new artist", firstCD.getArtist());
  }


}
