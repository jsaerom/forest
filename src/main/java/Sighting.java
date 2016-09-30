import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class Sighting {
  public String location;
  public Timestamp date;
  public int id;

  public Sighting(String _location) {
    this.location = _location;
    this.date = new Timestamp(new Date().getTime());
  }

  public String getLocation() {
    return this.location;
  }

  public Timestamp getDate() {
    return this.date;
  }

  public int getId() {
    return this.id;
  }

  public void setLocation(String _location) {
    this.location = _location;
  }

  @Override
  public boolean equals(Object otherSighting) {
    if(!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getLocation().equals(newSighting.getLocation()) &&
             this.getDate().getDay() == newSighting.getDate().getDay() &&
             this.getId() == newSighting.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (location, date) VALUES (:location, now());";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
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
    String sql = "UPDATE sightings SET (location) = (:location) WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("location", this.location)
      .addParameter("id", this.id)
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
    }
  }
}
