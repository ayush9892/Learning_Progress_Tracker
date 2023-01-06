package tracker.CoursesDetails;


import tracker.UsersDetails.UsersDTO;

import java.util.ArrayList;

public interface CourseDAO {
    CourseDTO getCoursePoints(String id);

    void setCoursePoints(String userId, int java, int dsa, int database, int spring);

    boolean checkUserAvailability(String userId);

    ArrayList<UserIdPointDTO> getUsersScore(String course);

    ArrayList<UsersDTO> getAllCompUsers();

    UsersDTO checkCompUser(String id, String course);

}
