package tracker.UsersDetails;

import java.util.ArrayList;

public interface UsersDAO {
    boolean checkEmail(String email);
    int addStudent(String fstName, String lstName, String email, int totStud);
    ArrayList<UsersDTO> studentList();
}
