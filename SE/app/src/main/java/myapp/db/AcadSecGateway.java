package myapp.db;

import java.sql.*;

//All interactions of AcadSec to the database occur via this class

public class AcadSecGateway {

    private String username;
    private Connection conn;

    public AcadSecGateway(String username) {
        this.username = username;
        this.conn = DatabaseConnection.connectToServer();
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
                .prepareStatement("select * from current_event where event = ? and sem =? and year = ?");
        st.setString(1, event);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public void addEvent(String event, Date starDate, Date endDate) throws SQLException {
        PreparedStatement st = conn.prepareStatement("insert into current_event values(?, ?, ?, ?, ?)");
        st.setString(1, event);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        st.setDate(4, starDate);
        st.setDate(5, endDate);
        st.executeUpdate();
        System.out.println("Event added");
    }

    public void removeEvent(String event) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("delete from current_event where event = ? and sem = ? and year = ?");
        st.setString(1, event);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        st.executeUpdate();
        System.out.println("Event removed");
    }

    public void updateSem(Date startDate, Date endDate) throws SQLException {
        PreparedStatement st = conn.prepareStatement("update current_event set event = 'completed' where event = 'ongoing'");
        st.executeUpdate();
        int nextYear = getCurrentYear();
        int nextSem = getCurrentSem() + 1;
        if (nextSem > 2) {
            nextSem = 1;
            nextYear = nextYear + 1;
        }
        st = conn.prepareStatement("insert into current_event values(?, ?, ?, ?, ?)");
        st.setString(1, "ongoing");
        st.setInt(2, nextSem);
        st.setInt(3, nextYear);
        st.setDate(4, startDate);
        st.setDate(5, endDate);
        st.executeUpdate();
        System.out.println("Semester updated");
    }

    public int getStudentSem(String student_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select year_of_entry from students where student_id = ?");
        st.setString(1, student_id);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return (getCurrentYear() - res.getInt("year_of_entry")) * 2 + getCurrentSem() - 1;
        }
        return 0;
    }

    public ResultSet getGrades(String student_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select * from grades where student_id = ?");
        st.setString(1, student_id);
        return st.executeQuery();
    }

    public ResultSet getSemGrades(String student_id, int studentSem) throws SQLException {
        int sem = getCurrentSem();
        int year = getCurrentYear();
        int diff = getStudentSem(student_id) - studentSem;
        if (diff % 2 == 1) {
            if(sem==2)year -= diff / 2;
            else year -= 1+diff/2;
            sem = 3 - sem;
        } else {
            year -= diff / 2;
        }
        PreparedStatement st = conn
                .prepareStatement("select * from grades where student_id = ? and sem = ? and year = ?");
        st.setString(1, student_id);
        st.setInt(2, sem);
        st.setInt(3, year);
        return st.executeQuery();
    }

    public boolean checkAuthority() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select * from users where username = ? and role = 0");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public boolean checkCourse(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select * from courses where course_id = ?");
        st.setString(1, course_id);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public void addCourse(String course_id, String dept, String ltpc, String pre) throws SQLException {
        String[] parts = ltpc.split("-");
        int L = Integer.parseInt(parts[0]);
        int T = Integer.parseInt(parts[1]);
        int P = Integer.parseInt(parts[2]);
        float C = Float.parseFloat(parts[3]);
        String[][] pre_req;
        String[][] padded = null;
        if (pre.equals("NaN")) {
            pre_req = null;
        } else {
            String[] parts2 = pre.split("\\|");
            pre_req = new String[parts2.length][];
            int maxLength = 0;
            for (int i = 0; i < parts2.length; i++) {
                pre_req[i] = parts2[i].split(",");
                if (pre_req[i].length > maxLength) {
                    maxLength = pre_req[i].length;
                }
            }
            padded = new String[pre_req.length][maxLength];
            for (int i = 0; i < pre_req.length; i++) {
                for (int j = 0; j < maxLength; j++) {
                    if (j < pre_req[i].length) {
                        padded[i][j] = pre_req[i][j];
                    } else {
                        padded[i][j] = "";
                    }
                }
            }
        }
        PreparedStatement st = conn.prepareStatement("insert into courses values(?, ?, ?, ?, ?, ?,?)");
        st.setString(1, course_id);
        st.setString(2, dept);
        st.setInt(3, L);
        st.setInt(4, T);
        st.setInt(5, P);
        st.setFloat(6, C);
        st.setObject(7, padded);
        st.executeUpdate();
        System.out.println("Course added");
    }

    public void removeCourse(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("delete from courses where course_id = ?");
        st.setString(1, course_id);
        st.executeUpdate();
        System.out.println("Course removed");
    }

    public void addDegreeCriteria(String dept, String program, String[] programCore, float programElective, float humanitiesElective,
            float scienceElective, float openElective, String[] btp)
            throws SQLException {
        int year = getCurrentYear();
        PreparedStatement st = conn.prepareStatement("insert into degree_criteria values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        st.setInt(1, year);
        st.setString(2, dept);
        st.setString(3, program);
        st.setObject(4, programCore);
        st.setFloat(5, programElective);
        st.setFloat(6, humanitiesElective);
        st.setFloat(7, scienceElective);
        st.setFloat(8, openElective);
        st.setObject(9, btp);
        st.executeUpdate();
        System.out.println("Degree criteria added");
    }

}
