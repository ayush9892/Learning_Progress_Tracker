package tracker;

import tracker.CourseData.CourseDataDAO;
import tracker.CourseData.CourseDataImp;
import tracker.CoursesDetails.CourseDAO;
import tracker.CoursesDetails.CourseImp;
import tracker.UsersDetails.UsersDAO;
import tracker.UsersDetails.UsersImp;

public class Main {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/learningtracker";
    public static final String USER_NM = "root";
    public static final String PASS = "***";
    public static void main(String[] args) {
        ValidateCred validateCred = new ValidateCred();
        UsersDAO users = new UsersImp();
        CourseDAO courses = new CourseImp();
        CourseDataDAO courseData = new CourseDataImp();
        LearningMenu learningMenu = new LearningMenu(users, validateCred, courses, courseData);

        learningMenu.cleanDatabase();
        learningMenu.Menu();
    }
}
