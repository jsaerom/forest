import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class EndangeredAnimal extends Animal {
  private String health;
  private String age;

  public static final String HEALTH_HEALTHY = "Healthy";
  public static final String HEALTH_MINOR_ILL = "Healthy with minor issues";
  public static final String HEALTH_SICK = "Sick";
  public static final String AGE_BABY = "Baby";
  public static final String AGE_YOUTH = "Youth";
  public static final String AGE_OLD = "Old";

  public EndangeredAnimal(String _name, String _health, String _age) {
    super(_name);
    this.health = _health;
    this.age = _age;
  }

  public String getHealth() {
    return this.health;
  }

  public String getAge() {
    return this.age;
  }

  public void setHealth(String _health) {
    this.health = _health;
  }

  public void setAge(String _age) {
    this.age = _age;
  }

  @Override
  public boolean equals(Object otherEndangeredAnimal) {
    if (!(otherEndangeredAnimal instanceof EndangeredAnimal)) {
      return false;
    } else {
      EndangeredAnimal newEndangeredAnimal = (EndangeredAnimal) otherEndangeredAnimal;
      return this.getName().equals(newEndangeredAnimal.getName()) &&
             this.getHealth().equals(newEndangeredAnimal.getHealth()) &&
             this.getAge().equals(newEndangeredAnimal.getAge()) &&
             this.getId() == newEndangeredAnimal.getId();
    }
  }
}
