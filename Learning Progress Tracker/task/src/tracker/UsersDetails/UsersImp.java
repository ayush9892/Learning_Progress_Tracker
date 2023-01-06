package tracker.UsersDetails;

import static tracker.Main.DB_URL;
import static tracker.Main.USER_NM;
import static tracker.Main.PASS;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class UsersImp implements UsersDAO {

    @Override
    public boolean checkEmail(String email) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM usercred WHERE email = ?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void linkUserCourse(String userId, String crs) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement("insert into courses(userid, course) values (?, ?)")) {
            ps.setString(1, userId);
            ps.setString(2, crs);
            con.setAutoCommit(true);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addStudent(String fstName, String lstName, String email, int totStud) {
        if (checkEmail(email)) {
            System.out.println("This email is already taken.");
        } else {
            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement("insert into usercred(iduserCred, firstName, lastName, email) values (?, ?, ?, ?)")) {
                ps.setString(1, fstName + email.hashCode());
                ps.setString(2, fstName);
                ps.setString(3, lstName);
                ps.setString(4, email);
                con.setAutoCommit(true);
                ps.executeUpdate();
                linkUserCourse(fstName + email.hashCode(), "java");
                linkUserCourse(fstName + email.hashCode(), "dsa");
                linkUserCourse(fstName + email.hashCode(), "databases");
                linkUserCourse(fstName + email.hashCode(), "spring");
                totStud++;
                System.out.println("The student has been added.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totStud;
    }

    @Override
    public ArrayList<UsersDTO> studentList() {
        try (Connection con = connect();
             Statement s = con.createStatement()) {
                try (ResultSet rs = s.executeQuery("SELECT * FROM usercred")) {
                    ArrayList<UsersDTO> UserList = new ArrayList<>();
                        while (rs.next()) {
                            UsersDTO user = new UsersDTO();
                            user.setUserId(rs.getString("iduserCred"));
                            user.setFirstName(rs.getString("firstName"));
                            user.setLastName(rs.getString("lastName"));
                            user.setEmail(rs.getString("email"));
                            UserList.add(user);
                        }
                    Collections.reverse(UserList);
                        return UserList;
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, USER_NM, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
