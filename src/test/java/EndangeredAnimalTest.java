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

  @Test
  public void equals_returnsTrueIfInstanceIsSame_true() {
    EndangeredAnimal firstAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    EndangeredAnimal secondAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    assertTrue(firstAnimal.equals(secondAnimal));
  }

  @Test
  public void save_insertsObjectIntoDatabase_EndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    testEndangeredAnimal.save();
    assertTrue(EndangeredAnimal.allEndangeredAnimals().get(0).equals(testEndangeredAnimal));
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    secondEndangeredAnimal.save();
    assertEquals(true, EndangeredAnimal.allEndangeredAnimals().get(0).equals(firstEndangeredAnimal));
    assertEquals(true, EndangeredAnimal.allEndangeredAnimals().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void update_updatesInstance_true() {
    EndangeredAnimal testEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    testEndangeredAnimal.save();
    testEndangeredAnimal.setName("lion");
    testEndangeredAnimal.setHealth("Sick");
    testEndangeredAnimal.setAge("Old");
    testEndangeredAnimal.update();
    assertEquals("lion", testEndangeredAnimal.getName());
    assertEquals("Sick", testEndangeredAnimal.getHealth());
    assertEquals("Old", testEndangeredAnimal.getAge());
  }

  @Test
  public void find_FindsAnimalInstanceRelatedToId_true() {
    EndangeredAnimal firstEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = new EndangeredAnimal("squirrel", "Healthy", "Baby");
    secondEndangeredAnimal.save();
    assertEquals(secondEndangeredAnimal, EndangeredAnimal.find(secondEndangeredAnimal.getId()));
  }
}
