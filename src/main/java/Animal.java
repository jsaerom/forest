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

}
