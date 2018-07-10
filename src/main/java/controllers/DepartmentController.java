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

public class DepartmentController {

    final VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

    public DepartmentController(){
        this.setUpEndPoints();
    }

    private void setUpEndPoints() {

        get("/departments", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/departments/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);
    }
}
