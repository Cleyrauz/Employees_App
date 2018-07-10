package controllers;

import db.DBHelper;
import db.Seeds;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;

import java.util.HashMap;
import java.util.List;

public class EmployeeController {

    public static void main(String[] args) {

        Seeds.seedData();

        ManagersController managersController = new ManagersController();
        EngineersController engineersController = new EngineersController();
        DepartmentController departmentController = new DepartmentController();

        final VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/employees", (req, res) ->{
            HashMap<String, Object> model = new HashMap<>();
            List<Employee> employees = DBHelper.getAll(Employee.class);
            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

    }
}
