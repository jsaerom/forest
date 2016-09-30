import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_true() {
    Animal testAnimal = new Animal("squirrel");
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getName_grabsNameFromAnimal_String() {
    Animal testAnimal = new Animal("squirrel");
    assertEquals("squirrel", testAnimal.getName());
  }
}
