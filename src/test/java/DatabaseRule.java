import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
    String deleteLibraryItemsQuery = "DELETE FROM libraryItems *;";
      String deletePersonsQuery = "DELETE FROM persons *;";
      String deleteBookHistoriesQuery = "DELETE FROM bookhistories *;";
      con.createQuery(deleteLibraryItemsQuery).executeUpdate();
      con.createQuery(deletePersonsQuery).executeUpdate();
      con.createQuery(deleteBookHistoriesQuery).executeUpdate();
    }
  }

}
