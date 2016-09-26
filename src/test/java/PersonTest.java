import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

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
}
