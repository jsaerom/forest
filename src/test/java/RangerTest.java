import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RangerTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void ranger_instantiatesCorrectly_true() {
    Ranger testRanger = new Ranger("John", "1234", "john@ranger.com");
    assertEquals(true, testRanger instanceof Ranger);
  }

  @Test
  public void getName_grabsNameFromRanger_String() {
    Ranger testRanger = new Ranger("John", "1234", "john@ranger.com");
    assertEquals("John", testRanger.getName());
  }

  @Test
  public void getRangerNumber_grabsRangerNumFromRanger_String() {
    Ranger testRanger = new Ranger("John", "1234", "john@ranger.com");
    assertEquals("1234", testRanger.getRangerNumber());
  }

  @Test
  public void getEmail_grabsEmailFromRanger_String() {
    Ranger testRanger = new Ranger("John", "1234", "john@ranger.com");
    assertEquals("john@ranger.com", testRanger.getEmail());
  }
}
