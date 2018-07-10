package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {

    final VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public EngineersController(){
        this.setUpEndPoints();
    }

    private void setUpEndPoints() {

        get("/engineers", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("template", "templates/engineers/index.vtl");
            model.put("engineers", engineers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("engineers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/engineers", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            Engineer newEngineer = new Engineer(firstName, lastName, salary, department);
            DBHelper.save(newEngineer);
            res.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);

        get("/engineers/:id", (req, res) -> {
            int engineerId = Integer.parseInt(req.params(":id"));

            Engineer engineer = DBHelper.find(engineerId, Engineer.class);
            List<Department> departments = DBHelper.getAll(Department.class);
            HashMap<String, Object> model = new HashMap<>();

            model.put("engineer", engineer);
            model.put("departments", departments);
            model.put("template", "templates/engineers/edit.vtl");

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/engineers/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));

            Engineer engineer = DBHelper.find(id, Engineer.class);
            DBHelper.delete(engineer);
            res.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);

        post("/engineers/:id/edit", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(id, Engineer.class);

            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            String salaryString = req.queryParams("salary");
            int salary = Integer.parseInt(salaryString);

            engineer.setDepartment(department);
            engineer.setFirstName(firstName);
            engineer.setLastName(lastName);
            engineer.setSalary(salary);

            DBHelper.save(engineer);
            res.redirect("/engineers");
            return null;
        }, velocityTemplateEngine);
    }
}
