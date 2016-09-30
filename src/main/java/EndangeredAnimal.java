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

}
