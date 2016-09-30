import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Animal {
  public String name;
  public int id;

  public Animal(String _name) {
    this.name = _name;
  }

  public String getName(){
    return this.name;
  }

  public int getId() {
    return id;
  }

  public void setName(String _name) {
    this.name = _name;
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if (!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName()) &&
             this.getId() == newAnimal.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Animal> all() {
    String sql = "SELECT name, id FROM animals;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Animal.class);
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE animals SET (name) = (:name) WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("name", this.name)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT name, id FROM animals WHERE id = :id;";
      Animal animal = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Animal.class);
      return animal;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
      String joinDeleteQuery = "DELETE FROM animals_sightings WHERE animal_id = :animalid;";
      con.createQuery(joinDeleteQuery)
        .addParameter("animalid", this.getId())
        .executeUpdate();
    }
  }

  public void addSighting(Sighting sighting) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals_sightings (sighting_id, animal_id) VALUES (:sighting_id, :animal_id);";
      con.createQuery(sql)
        .addParameter("sighting_id", sighting.getId())
        .addParameter("animal_id", this.id)
        .executeUpdate();
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT sighting_id FROM animals_sightings WHERE animal_id = :animal_id;";
      List<Integer> sightingIds = con.createQuery(joinQuery)
        .addParameter("animal_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Sighting> sightings = new ArrayList<Sighting>();

      for (Integer sightingId : sightingIds) {
        String sightingQuery = "SELECT * FROM sightings WHERE id = :sightingId;";
        Sighting sighting = con.createQuery(sightingQuery)
          .addParameter("sightingId", sightingId)
          .executeAndFetchFirst(Sighting.class);
        sightings.add(sighting);
      }
      return sightings;
    }
  }

}
