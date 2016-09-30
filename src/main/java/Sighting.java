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
}
