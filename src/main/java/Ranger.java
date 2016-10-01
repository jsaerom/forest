import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Ranger {
  private String name;
  private String rangerNumber;
  private String email;
  private int id;

  public Ranger(String _name, String _rangerNumber, String _email) {
    this.name = _name;
    this.rangerNumber = _rangerNumber;
    this.email = _email;
    if(email.length() == 0 || rangerNumber.length() == 0 || name.length() == 0){
      throw new UnsupportedOperationException("Please fill out your whole information");
    }
  }

  public String getName() {
    return this.name;
  }

  public String getRangerNumber() {
    return this.rangerNumber;
  }

  public String getEmail() {
    return this.email;
  }

  public int getId() {
    return this.id;
  }

  public void setName(String _name) {
    this.name = _name;
  }

  public void setRangerNumber(String _rangerNumber) {
    this.rangerNumber = _rangerNumber;
  }

  public void setEmail(String _email) {
    this.email = _email;
  }

  @Override public boolean equals(Object otherRanger) {
    if (!(otherRanger instanceof Ranger)) {
      return false;
    } else {
      Ranger newRanger = (Ranger) otherRanger;
      return this.getName().equals(newRanger.getName()) &&
             this.getRangerNumber().equals(newRanger.getRangerNumber()) &&
             this.getEmail().equals(newRanger.getEmail()) &&
             this.getId() == newRanger.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO rangers (name, rangernumber, email) VALUES (:name, :rangernumber, :email);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("rangernumber", this.rangerNumber)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Ranger> all() {
    String sql = "SELECT * FROM rangers;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ranger.class);
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()){
    String sql = "UPDATE rangers SET (name, rangernumber, email) = (:name, :rangernumber, :email) WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("name", this.name)
      .addParameter("rangernumber", this.rangerNumber)
      .addParameter("email", this.email)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static Ranger find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM rangers WHERE id = :id;";
      Ranger ranger = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Ranger.class);
      return ranger;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM rangers WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }
}
