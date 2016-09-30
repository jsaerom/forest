import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class EndangeredAnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void endangeredanimal_instantiatesCorrectly_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void getName_grabsNameFromEndangeredAnimal_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    assertEquals("squirrel", testEndangeredAnimal.getName());
  }

  @Test
  public void getHealth_grabsHealthFromEndangeredAnimal_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    assertEquals("Healthy", testEndangeredAnimal.getHealth());
  }

  @Test
  public void getAge_grabsAgeFromEndangeredAnimal_String() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    assertEquals("Baby", testEndangeredAnimal.getAge());
  }
}
