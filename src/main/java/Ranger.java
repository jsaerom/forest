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

  public void setName(String _name) {
    this.name = _name;
  }

  public void setRangerNumber(String _rangerNumber) {
    this.rangerNumber = _rangerNumber;
  }

  public void setEmail(String email) {
    this.email = _email;
  }
}
