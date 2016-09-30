import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class EndangeredAnimal extends Animal {
  private String health;
  private String age;
  private boolean endangered;

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
    this.endangered = true;
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

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, health, age, endangered) VALUES (:name, :health, :age, true);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("health", this.health)
        .addParameter("age", this.age)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<EndangeredAnimal> allEndangeredAnimals() {
    String sql = "SELECT * FROM animals WHERE endangered = true;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(EndangeredAnimal.class);
    }
  }

  @Override
  public void update() {
    try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE animals SET (name, health, age) = (:name, :health, :age) WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("name", this.name)
      .addParameter("health", this.health)
      .addParameter("age", this.age)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
