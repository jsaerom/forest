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
}
