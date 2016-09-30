import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {
  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteAnimalsQuery = "DELETE FROM animals *;";
      String deleteSightingsQuery = "DELETE FROM sightings *;";
      String deleteRangersQuery = "DELETE FROM rangers *;";
      String deleteRangerSightingsQuery = "DELETE FROM rangers_sightings *;";
      String deleteAnimalSightingsQuery = "DELETE FROM animals_sightings *;";
      con.createQuery(deleteAnimalsQuery).executeUpdate();
      con.createQuery(deleteSightingsQuery).executeUpdate();
      con.createQuery(deleteRangersQuery).executeUpdate();
      con.createQuery(deleteRangerSightingsQuery).executeUpdate();
      con.createQuery(deleteAnimalSightingsQuery).executeUpdate();
    }
  }
}
