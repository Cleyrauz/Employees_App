import db.DBHelper;
import models.Department;
import models.Employee;
import models.Engineer;
import models.Manager;

public class Runner {

    public static void main(String[] args) {
        DBHelper.deleteAll(Engineer.class);
        DBHelper.deleteAll(Employee.class);
        DBHelper.deleteAll(Manager.class);
        DBHelper.deleteAll(Department.class);

    }
}
