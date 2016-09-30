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

}
