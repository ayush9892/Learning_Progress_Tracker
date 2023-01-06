package tracker.CourseData;

import java.sql.*;
import java.util.ArrayList;

import static tracker.Main.*;

public class CourseDataImp implements CourseDataDAO {
    @Override
    public boolean isCourseAva(String crs) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement("SELECT course FROM coursedata WHERE course = ?")) {
            ps.setString(1, crs);
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

    @Override
    public boolean setEnrolledUsers() {
        boolean isAnyEnrolled = false;
        try (Connection con = connect()) {
            int java_enr = queryResult(con, "java");
            int dsa_enr = queryResult(con, "dsa");
            int dbs_enr =  queryResult(con, "databases");
            int spr_enr = queryResult(con, "spring");
            int tot_java_pnt = totalCrsPnt(con, "java");
            int tot_dsa_pnt = totalCrsPnt(con, "dsa");
            int tot_dbs_pnt = totalCrsPnt(con, "databases");
            int tot_spr_pnt = totalCrsPnt(con, "spring");
            if (java_enr != 0 || dsa_enr != 0 || dbs_enr != 0 || spr_enr != 0) {
                isAnyEnrolled = true;
                try(PreparedStatement pSt = con.prepareStatement("UPDATE coursedata SET enrolledno = ?, grdperassg = ?, totalCrsPnt = ? WHERE course = ?")) {
                    pSt.setInt(1, java_enr);
                    pSt.setFloat(2, tot_java_pnt / (float)java_enr);
                    pSt.setInt(3, tot_java_pnt);
                    pSt.setString(4, "java");
                    pSt.executeUpdate();
                    pSt.setInt(1, dsa_enr);
                    pSt.setFloat(2, tot_dsa_pnt / (float)dsa_enr);
                    pSt.setInt(3, tot_dsa_pnt);
                    pSt.setString(4, "dsa");
                    pSt.executeUpdate();
                    pSt.setInt(1,dbs_enr);
                    pSt.setFloat(2, tot_dbs_pnt / (float)dbs_enr);
                    pSt.setInt(3, tot_dbs_pnt);
                    pSt.setString(4, "databases");
                    pSt.executeUpdate();
                    pSt.setInt(1, spr_enr);
                    pSt.setFloat(2, tot_spr_pnt / (float)spr_enr);
                    pSt.setInt(3, tot_spr_pnt);
                    pSt.setString(4, "spring");
                    pSt.executeUpdate();
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return isAnyEnrolled;
    }

    @Override
    public ArrayList<String> filter(String req, String col) {
        try(Connection con = connect();
            Statement st = con.createStatement();
            ResultSet rs_req = st.executeQuery("SELECT "+ req + "(" + col + ") FROM coursedata")) {
            rs_req.next();
            try(PreparedStatement ps = con.prepareStatement("SELECT course FROM coursedata WHERE " + col + " = ?")) {
                if (col.equals("grdperassg")) {
                    ps.setFloat(1, rs_req.getFloat(1));
                } else {
                    ps.setInt(1, rs_req.getInt(1));
                }
                try (ResultSet rs_crs = ps.executeQuery()) {
                    ArrayList<String> curs = new ArrayList<>();
                    while (rs_crs.next()) {
                        curs.add(rs_crs.getString("course"));
                    }
                    return curs;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int queryResult(Connection con, String course) throws SQLException {
        int enrolledUsers = 0;
        try (PreparedStatement ps = con.prepareStatement("SELECT userid FROM courses WHERE course = ? AND point > 0")) {
            ps.setString(1, course);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    enrolledUsers++;
                }
            }
        }
        return enrolledUsers;
    }

    private int totalCrsPnt(Connection con, String crs) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT SUM(point) FROM courses WHERE course = ?")) {
            ps.setString(1, crs);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }


    private Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, USER_NM, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
