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

  @Test
  public void equals_returnsTrueIfNameIsSame_true() {
    Animal firstAnimal = new Animal("squirrel");
    Animal secondAnimal = new Animal("squirrel");
    assertTrue(firstAnimal.equals(secondAnimal));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Animal() {
    Animal testAnimal = new Animal("squirrel");
    testAnimal.save();
    assertTrue(Animal.all().get(0).equals(testAnimal));
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_true() {
    Animal firstAnimal = new Animal("squirrel");
    firstAnimal.save();
    Animal secondAnimal = new Animal("squirrel");
    secondAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(firstAnimal));
    assertEquals(true, Animal.all().get(1).equals(secondAnimal));
  }

  @Test
  public void update_updatesName_true() {
    Animal testAnimal = new Animal("squirrel");
    testAnimal.save();
    testAnimal.setName("lion");
    testAnimal.update();
    assertEquals("lion", testAnimal.getName());
  }

  @Test
  public void find_FindsAnimalInstanceRelatedToId_true() {
    Animal firstAnimal = new Animal("squirrel");
    firstAnimal.save();
    Animal secondAnimal = new Animal("squirrel");
    secondAnimal.save();
    assertEquals(secondAnimal, Animal.find(secondAnimal.getId()));
  }
}
