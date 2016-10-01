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
        try {
          EndangeredAnimal animal = new EndangeredAnimal(name, health, age);
          animal.save();
        } catch (UnsupportedOperationException exception) {
          response.redirect("/error");
        }
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
      try {
      Ranger ranger = new Ranger(name, rangerNumber, email);
      ranger.save();
    } catch (UnsupportedOperationException exception) {
      response.redirect("/error");
    }
      model.put("navbar", nav);
      response.redirect("/rangers");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("navbar", nav);
      model.put("healthy", EndangeredAnimal.HEALTH_HEALTHY);
      model.put("minorIllness", EndangeredAnimal.HEALTH_MINOR_ILL);
      model.put("sick", EndangeredAnimal.HEALTH_SICK);
      model.put("baby", EndangeredAnimal.AGE_BABY);
      model.put("youth", EndangeredAnimal.AGE_YOUTH);
      model.put("old", EndangeredAnimal.AGE_OLD);
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

    get("/animals/:animalId", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int animalId = Integer.parseInt(request.params(":animalId"));
      EndangeredAnimal animal = EndangeredAnimal.find(animalId);
      model.put("navbar", nav);
      model.put("template", "templates/animal.vtl");
      model.put("animal", animal);
      model.put("sightings", animal.getSightings());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/:sightingId", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int sightingId = Integer.parseInt(request.params(":sightingId"));
      Sighting sighting = Sighting.find(sightingId);
      model.put("navbar", nav);
      model.put("template", "templates/sighting.vtl");
      model.put("sighting", sighting);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/rangers/:rangerId", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int rangerId = Integer.parseInt(request.params(":rangerId"));
      Ranger ranger = Ranger.find(rangerId);
      model.put("navbar", nav);
      model.put("template", "templates/ranger.vtl");
      model.put("ranger", ranger);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/rangers/:rangerId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int rangerId = Integer.parseInt(request.params(":rangerId"));
      Ranger ranger = Ranger.find(rangerId);
      model.put("ranger", ranger);
      model.put("navbar", nav);
      model.put("template", "templates/ranger-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/:sightingId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int sightingId = Integer.parseInt(request.params(":sightingId"));
      Sighting sighting = Sighting.find(sightingId);
      model.put("sighting", sighting);
      model.put("rangers", Ranger.all());
      model.put("animals", Animal.all());
      model.put("navbar", nav);
      model.put("template", "templates/sighting-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/:animalId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int animalId = Integer.parseInt(request.params(":animalId"));
      Animal animal = Animal.find(animalId);
      model.put("animal", animal);
      model.put("navbar", nav);
      model.put("template", "templates/animal-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/:animalId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int animalId = Integer.parseInt(request.params(":animalId"));
      EndangeredAnimal animal = EndangeredAnimal.find(animalId);

      String name = request.queryParams("name");
      boolean endangered = Boolean.valueOf(request.queryParams("endangered"));
      String health = request.queryParams("health");
      String age = request.queryParams("age");
      animal.setName(name);
      animal.setHealth(health);
      animal.setAge(age);
      animal.update();
      response.redirect("/animals/" + animalId);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/:sightingId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int sightingId = Integer.parseInt(request.params(":sightingId"));
      Sighting sighting = Sighting.find(sightingId);
      String location = request.queryParams("location");
      int rangerId = Integer.parseInt(request.queryParams("rangerId"));
      int animalId = Integer.parseInt(request.queryParams("animalId"));
      sighting.setLocation(location);
      sighting.setRangerId(rangerId);
      // Animal animal = Animal.find(animalId);
      // animal.addSighting(sighting);
      sighting.update();
      response.redirect("/sightings/" + sightingId);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/rangers/:rangerId/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int rangerId = Integer.parseInt(request.params(":rangerId"));
      Ranger ranger = Ranger.find(rangerId);
      String name = request.queryParams("name");
      String rangerNumber = request.queryParams("rangerNumber");
      String email = request.queryParams("email");
      ranger.setName(name);
      ranger.setRangerNumber(rangerNumber);
      ranger.setEmail(email);
      ranger.update();
      response.redirect("/rangers/" + rangerId);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/:animalId/delete", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int animalId = Integer.parseInt(request.params(":animalId"));
      EndangeredAnimal animal = EndangeredAnimal.find(animalId);
      model.put("navbar", nav);
      model.put("template", "templates/animal.vtl");
      model.put("delete", "templates/animal-delete.vtl");
      model.put("animal", animal);
      model.put("sightings", animal.getSightings());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/:sightingId/delete", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int sightingId = Integer.parseInt(request.params(":sightingId"));
      Sighting sighting = Sighting.find(sightingId);
      model.put("navbar", nav);
      model.put("template", "templates/sighting.vtl");
      model.put("delete", "templates/sighting-delete.vtl");
      model.put("sighting", sighting);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/rangers/:rangerId/delete", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int rangerId = Integer.parseInt(request.params(":rangerId"));
      Ranger ranger = Ranger.find(rangerId);
      model.put("navbar", nav);
      model.put("template", "templates/ranger.vtl");
      model.put("delete", "templates/ranger-delete.vtl");
      model.put("ranger", ranger);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/:animalId/delete", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int animalId = Integer.parseInt(request.params(":animalId"));
      EndangeredAnimal animal = EndangeredAnimal.find(animalId);
      animal.delete();
      model.put("animals", Animal.all());
      response.redirect("/animals");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/:sightingId/delete", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int sightingId = Integer.parseInt(request.params(":sightingId"));
      Sighting sighting = Sighting.find(sightingId);
      sighting.delete();
      model.put("sightings", Sighting.all());
      response.redirect("/sightings");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/rangers/:rangerId/delete", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      int rangerId = Integer.parseInt(request.params(":rangerId"));
      Ranger ranger = Ranger.find(rangerId);
      ranger.delete();
      model.put("rangers", Ranger.all());
      response.redirect("/rangers");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/error", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/error.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
