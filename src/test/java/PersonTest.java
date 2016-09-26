import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PersonTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Person_instantiatesCorrectly_true() {
    Person testPerson = new Person("Name");
    assertEquals(true, testPerson instanceof Person);
  }

  @Test
  public void Person_instantiatesWithName_String() {
    Person testPerson = new Person("Name");
    assertEquals("Name", testPerson.getName());
  }

  @Test
  public void all_returnsAllInstancesOfPerson_true() {
    Person testPerson = new Person("Name");
    testPerson.save();
    Person secondPerson = new Person("Name1");
    secondPerson.save();
    assertTrue(Person.all().get(0).equals(testPerson));
    assertTrue(Person.all().get(1).equals(secondPerson));
  }

  @Test
  public void getId_personsInstantiateWithAnId() {
    Person testPerson = new Person("Name");
    testPerson.save();
    assertTrue(testPerson.getId() > 0);
  }

  @Test
  public void find_returnsPersonWithSameId_secondPerson() {
    Person testPerson = new Person("Name");
    testPerson.save();
    Person secondPerson = new Person("Name1");
    secondPerson.save();
    assertEquals(Person.find(secondPerson.getId()), secondPerson);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Person testPerson = new Person("Name");
    Person myPerson = new Person("Name");
    assertTrue(testPerson.equals(myPerson));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    Person testPerson = new Person("Name");
    testPerson.save();
    assertTrue(Person.all().get(0).equals(testPerson));
  }

  @Test
  public void save_assignsIdToObject() {
    Person testPerson = new Person("Name");
    testPerson.save();
    Person savedPerson = Person.all().get(0);
    assertEquals(testPerson.getId(), savedPerson.getId());
  }

  @Test
  public void save_savesPersonIdIntoDB_true() {
    Person testPerson = new Person("Name");
    testPerson.save();
    Person savedPerson = Person.find(testPerson.getId());
    assertEquals(savedPerson.getId(), testPerson.getId());
  }

  @Test
  public void updatePersonName_updatesPersonName_true() {
    Person testPerson = new Person("Name");
    testPerson.save();
    testPerson.setName("Mo");
    testPerson.update();
    assertEquals("Mo", Person.find(testPerson.getId()).getName());
  }

  // @Test
  // public void getBooks_retrievesAllBooksFromDatabase_BooksList() {
  //   Person testPerson = new Person("Name");
  //   Book firstBook = new Book("Title", "Author", "Genre");
  //   firstBook.save();
  //   Book secondBook = new Book("Title", "Author", "Genre");
  //   secondBook.save();
  //   Book[] books = new Book[] { firstBook, secondBook };
  //   assertTrue(testPerson.getBooks().containsAll(Arrays.asList(books)));
  // }

  @Test
  public void delete_deletesPerson_true() {
    Person testPerson = new Person("Name");
    int testPersonId = testPerson.getId();
    testPerson.delete();
    assertEquals(null, Person.find(testPersonId));
  }

  // @Test
  // public void getAllBooks_retrievesAllBookHistoriesForPatron_true() {
  //   Person testPerson = new Person("Name");
  //   Book newBook = new Book("Title", "Author", "Genre");
  //   newBook.save();
  //   Book thisBook = new Book("Title", "Author", "Genre");
  //   thisBook.save();
  //   Book[] books = new Book[] {newBook, thisBook};
  //   assertTrue(testPerson.getAllBooks().containsAll(Arrays.asList(books)));
  // }

  // @Test
  // public void getOverduePatrons_retrievesAllPatronsWithOverdueBooks_true() {
  //   Person librarian = new Person("Name");
  //   Person testPerson = new Person("Name");
  //   testPerson.save();
  //   Person newPerson = new Person("Name");
  //   newPerson.save();
  //   Person[] persons = new Person[] {testPerson, newPerson};
  //   assertTrue(librarian.getOverduePatrons().containsAll(Arrays.asList(persons)));
  // }

  @Test
  public void searchForPerson_findsPersonByName_foundName() {
    Person testPerson = new Person("Name");
    testPerson.save();
    List<Person> foundNames = Person.searchForPerson("Name");
    assertTrue(foundNames.contains(testPerson));
  }
}
