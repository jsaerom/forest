import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    String nav = "templates/nav-bar.vtl";

    get("/", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("navbar", nav);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("navbar", nav);
      model.put("template", "templates/animals.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("sightings", Sighting.all());
      model.put("navbar", nav);
      model.put("template", "templates/sightings.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/rangers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangers", Ranger.all());
      model.put("navbar", nav);
      model.put("template", "templates/rangers.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      boolean endangered = Boolean.valueOf(request.queryParams("endangered"));
      String health = request.queryParams("health");
      String age = request.queryParams("age");
      if (endangered == true) {
        EndangeredAnimal animal = new EndangeredAnimal(name, health, age);
        animal.save();
      } else {
        Animal animal = new Animal(name);
        animal.save();
      }
      response.redirect("/animals");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String location = request.queryParams("location");
      String stringDate = request.queryParams("date");
      // Date date = Date.valueOf(stringDate);
      int rangerId = Integer.parseInt(request.queryParams("rangerId"));
      int animalId = Integer.parseInt(request.queryParams("animalId"));
      Sighting sighting = new Sighting(location, rangerId);
      sighting.save();
      Animal animal = Animal.find(animalId);
      animal.addSighting(sighting);
      model.put("navbar", nav);
      response.redirect("/sightings");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/rangers", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String rangerNumber = request.queryParams("rangerNumber");
      String email = request.queryParams("email");
      Ranger ranger = new Ranger(name, rangerNumber, email);
      ranger.save();
      model.put("navbar", nav);
      response.redirect("/rangers");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("navbar", nav);
      model.put("template", "templates/animal-add.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/rangers/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("navbar", nav);
      model.put("template", "templates/ranger-add.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangers", Ranger.all());
      model.put("animals", Animal.all());
      model.put("navbar", nav);
      model.put("template", "templates/sighting-add.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
