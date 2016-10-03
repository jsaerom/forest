import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Sighting {
  private String location;
  private Timestamp date;
  private int id;
  private int rangerId;

  public Sighting(String _location, int _rangerId) {
    this.location = _location;
    this.date = new Timestamp(new Date().getTime());
    this.rangerId = _rangerId;
  }

  public String getLocation() {
    return this.location;
  }

  public String getDate() {
    String S = new SimpleDateFormat("MMMM dd, yyyy").format(date);
    return S;
  }

  public int getRangerId() {
    return this.rangerId;
  }

  public List<EndangeredAnimal> getAnimals() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT sighting_id FROM animals_sightings WHERE sighting_id = :sighting_id;";
      List<Integer> animalIds = con.createQuery(joinQuery)
        .addParameter("sighting_id", this.getId())
        .executeAndFetch(Integer.class);

      List<EndangeredAnimal> animals = new ArrayList<EndangeredAnimal>();

      for (Integer animalId : animalIds) {
        String animalQuery = "SELECT * FROM animals WHERE id = :animalId;";
        EndangeredAnimal animal = con.createQuery(animalQuery)
          .addParameter("animalId", animalId)
          .executeAndFetchFirst(EndangeredAnimal.class);
        animals.add(animal);
      }
      return animals;
    }
  }

  public int getId() {
    return this.id;
  }

  public void setLocation(String _location) {
    this.location = _location;
  }

  public void setRangerId(int _rangerId) {
    this.rangerId = _rangerId;
  }

  @Override
  public boolean equals(Object otherSighting) {
    if(!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getLocation().equals(newSighting.getLocation()) &&
             this.getDate().equals(newSighting.getDate()) &&
             this.getRangerId() == newSighting.getRangerId() &&
             this.getId() == newSighting.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (location, date, rangerid) VALUES (:location, now(), :rangerid);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .addParameter("rangerid", this.rangerId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Sighting> all() {
    String sql = "SELECT * FROM sightings;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Sighting.class);
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE sightings SET (location, rangerid) = (:location, :rangerid) WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("location", this.location)
      .addParameter("id", this.id)
      .addParameter("rangerid", this.rangerId)
      .executeUpdate();
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id = :id;";
      Sighting animal = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
      return animal;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM sightings WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM animals_sightings WHERE sighting_id = :sightingid;";
      con.createQuery(joinDeleteQuery)
        .addParameter("sightingid", this.getId())
        .executeUpdate();
    }
  }
}
