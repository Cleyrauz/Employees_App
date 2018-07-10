package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

        final VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

//    This doesn't have a main method because is not the main controller

    public ManagersController(){
        this.setUpEndPoints();
    }

    private void setUpEndPoints() {

        get("/managers", (req, res) ->{
            HashMap<String, Object> model = new HashMap<>();
            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/managers", (req, res) -> {
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int budget = Integer.parseInt(req.queryParams("budget"));
          Manager newManager = new Manager(firstName, lastName, salary, department, budget);
          DBHelper.save(newManager);
          res.redirect("/managers");
          return null;
        }, velocityTemplateEngine);

        get("/managers/:id", (req, res) -> {
            int managerId = Integer.parseInt(req.params(":id"));

            Manager manager = DBHelper.find(managerId, Manager.class);
            List<Department> departments = DBHelper.getAll(Department.class);
            HashMap<String, Object> model = new HashMap<>();

            model.put("manager", manager);
            model.put("departments", departments);
            model.put("template", "templates/managers/edit.vtl");

            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        post("/managers/:id/delete", (req, res) ->{
            int id = Integer.parseInt(req.params(":id"));

            Manager manager = DBHelper.find(id, Manager.class);
            DBHelper.delete(manager);
            res.redirect("/managers");
            return null;
        }, velocityTemplateEngine);

        post("/managers/:id/edit", (req, res) ->{
            int id = Integer.parseInt(req.params(":id"));
            Manager manager = DBHelper.find(id, Manager.class);

        int departmentId = Integer.parseInt(req.queryParams("department"));
        Department department = DBHelper.find(departmentId, Department.class);
        String firstName = req.queryParams("firstName");
        String lastName = req.queryParams("lastName");
        String salaryString = req.queryParams("salary");
        int salary = Integer.parseInt(salaryString);
        String budgetString = req.queryParams("budget");
        double budget = Double.parseDouble(budgetString);

        manager.setDepartment(department);
        manager.setFirstName(firstName);
        manager.setLastName(lastName);
        manager.setSalary(salary);
        manager.setBudget(budget);

        DBHelper.save(manager);
        res.redirect("/managers");
        return null;
        }, velocityTemplateEngine);
    }

}

