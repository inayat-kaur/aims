package myapp.db;
 
import java.sql.*;
import java.util.ArrayList;

//All interactions of Faculty to the database occur via this class

public class FacultyGateway {
    private String username;
    private Connection conn;

    public FacultyGateway(String username) {
        this.username = username;
        this.conn = DatabaseConnection.connectToServer();
    }

    public String getName() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select name from faculty where faculty_id = ?");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getString("name");
        }
        return null;
    }

    public void updatePhone(String phone) throws SQLException {
        PreparedStatement st = conn.prepareStatement("update faculty set phone = ? where faculty_id = ?");
        st.setString(1, phone);
        st.setString(2, username);
        st.executeUpdate();
        System.out.println("Phone number updated successfully");
    }

    public void updateAddress(String address) throws SQLException {
        PreparedStatement st = conn.prepareStatement("update faculty set address = ? where faculty_id = ?");
        st.setString(1, address);
        st.setString(2, username);
        st.executeUpdate();
        System.out.println("Address updated successfully");
    }

    public int getCurrentYear() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select year from current_event where event = 'ongoing'");
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getInt("year");
        }
        return 0;
    }

    public int getCurrentSem() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select sem from current_event where event = 'ongoing'");
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getInt("sem");
        }
        return 0;
    }

    public boolean checkEvent(String event) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select * from current_event where event = ? and sem=? and year=?");
        st.setString(1, event);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public float getCourseCredits(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select C from courses where course_id = ?");
        st.setString(1, course_id);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getFloat("C");
        }
        return 0f;
    }

    public boolean checkIfOffered(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select * from offering where course_id = ? and faculty_id = ? and sem = ? and year = ?");
        st.setString(1, course_id);
        st.setString(2, username);
        st.setInt(3, getCurrentSem());
        st.setInt(4, getCurrentYear());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public void registerCourse(String course_id, float cg_constraint) throws SQLException {
        String query = "INSERT INTO offering VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, course_id);
        stmt.setInt(2, getCurrentSem());
        stmt.setInt(3, getCurrentYear());
        stmt.setString(4, username);
        stmt.setFloat(5, cg_constraint);
        stmt.executeUpdate();
        System.out.println("Course registered successfully");
    }

    public void deregisterStudents(String course_id) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("delete from student_course where course_id = ? and sem = ? and year = ?");
        st.setString(1, course_id);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        st.executeUpdate();
        System.out.println("Students deregistered successfully");
    }

    public void deregisterCourse(String course_id) throws SQLException {
        String query = "DELETE FROM offering WHERE course_id = ? AND faculty_id = ? and sem = ? and year = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, course_id);
        stmt.setString(2, username);
        stmt.setInt(3, getCurrentSem());
        stmt.setInt(4, getCurrentYear());
        stmt.executeUpdate();
        System.out.println("Course deregistered successfully");
    }

    public ResultSet getGrades(String course_id, int year, int sem) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select student_id, grade from grades where course_id = ? and year = ? and sem = ?");
        st.setString(1, course_id);
        st.setInt(2, year);
        st.setInt(3, sem);
        ResultSet res = st.executeQuery();
        return res;
    }

    public void updateGrades(ArrayList<String> student_id, ArrayList<Integer> grades, String course_id)
            throws SQLException {
        String query = "insert into grades values (?,?,?,?,?,?)";
        String query2 = "update student_course set status = ? where student_id = ? and course_id = ? and sem = ? and year = ?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(2, course_id);
        st.setFloat(3, getCourseCredits(course_id));
        st.setInt(4, getCurrentSem());
        st.setInt(5, getCurrentYear());
        PreparedStatement st2 = conn.prepareStatement(query2);
        st2.setString(3, course_id);
        st2.setInt(4, getCurrentSem());
        st2.setInt(5, getCurrentYear());
        for (int i = 0; i < student_id.size(); i++) {
            st.setString(1, student_id.get(i));
            st.setInt(6, grades.get(i));
            st.executeUpdate();
            st2.setString(2, student_id.get(i));
            if (grades.get(i) >= 4) {
                st2.setString(1, "C");
            } else {
                st2.setString(1, "F");
            }
            st2.executeUpdate();
        }
        System.out.println("Grades updated successfully");
    }

}
