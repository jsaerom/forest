import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class SightingTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void sighting_instantiatesCorrectly_true() {
    Sighting testSighting = new Sighting("Zone A");
    assertEquals(true, testSighting instanceof Sighting);
  }

  @Test
  public void getLocation_grabsLocationFromSighting_String() {
    Sighting testSighting = new Sighting("Zone A");
    assertEquals("Zone A", testSighting.getLocation());
  }

  @Test
  public void equals_returnsTrueIfObjectIsSame_true() {
    Sighting firstSighting = new Sighting("Zone A");
    Sighting secondSighting = new Sighting("Zone A");
    assertTrue(firstSighting.equals(secondSighting));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Sighting() {
    Sighting testSighting = new Sighting("Zone A");
    testSighting.save();
    assertTrue(Sighting.all().get(0).equals(testSighting));
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    Sighting firstSighting = new Sighting("Zone A");
    firstSighting.save();
    Sighting secondSighting = new Sighting("Zone A");
    secondSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(firstSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondSighting));
  }

  @Test
  public void getDate_returnsDate_true() {
    Sighting testSighting = new Sighting("Zone A");
    Timestamp newTime = new Timestamp(new Date().getTime());
    assertEquals(newTime.getDay(), testSighting.getDate().getDay());
  }

  @Test
  public void update_updatesLocation_true() {
    Sighting testSighting = new Sighting("Zone A");
    testSighting.save();
    testSighting.setLocation("Zone B");
    testSighting.update();
    assertEquals("Zone B", testSighting.getLocation());
  }

  @Test
  public void find_FindsSightingInstanceRelatedToId_true() {
    Sighting firstSighting = new Sighting("Zone A");
    firstSighting.save();
    Sighting secondSighting = new Sighting("Zone B");
    secondSighting.save();
    assertEquals(secondSighting, Sighting.find(secondSighting.getId()));
  }
}
