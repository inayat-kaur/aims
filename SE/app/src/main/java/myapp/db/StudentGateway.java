package myapp.db;

import java.sql.*;
import java.util.ArrayList;
//All interactions of Student to the database occur via this class
public class StudentGateway {
    private Connection conn;
    private String username;

    public StudentGateway(String username) {
        this.conn = DatabaseConnection.connectToServer();
        this.username = username;
    }

    public String getName() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select name from students where student_id = ?");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getString("name");
        }
        return null;
    }

    public void updatePhone(String number) throws SQLException {
        PreparedStatement st = conn.prepareStatement("update students set phone = ? where student_id = ?");
        st.setString(1, number);
        st.setString(2, username);
        st.executeUpdate();
        System.out.println("Phone number updated successfully");
    }

    public void updateAddress(String address) throws SQLException {
        PreparedStatement st = conn.prepareStatement("update students set address = ? where student_id = ?");
        st.setString(1, address);
        st.setString(2, username);
        st.executeUpdate();
        System.out.println("Address updated successfully");
    }

    public int getCurrentYear() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select year from current_event where event = 'ongoing'");
        ResultSet res = st.executeQuery();
        res.next();
            return res.getInt("year");
    }

    public int getCurrentSem() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select sem from current_event where event = 'ongoing'");
        ResultSet res = st.executeQuery();
        res.next();
            return res.getInt("sem");
    }

    public boolean checkEvent(String event) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select * from current_event where event = ? and sem = ? and year = ?");
        st.setString(1, event);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public float getCGPA() throws SQLException {
        ResultSet res = getGrades();
        float sum = 0;
        float count = 0;
        float credit, grade;
        while (res.next()) {
            credit = Float.parseFloat(res.getString("credits"));
            grade = Integer.parseInt(res.getString("grade"));
            sum += credit * grade;
            count += credit;
        }
        return (float) sum / count;
    }

    public int getStudentSem() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select year_of_entry from students where student_id = ?");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        res.next();
            return (getCurrentYear() - res.getInt("year_of_entry")) * 2 + getCurrentSem() - 1;
    }

    public boolean checkPrereq(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM courses WHERE course_id = ?");
        st.setString(1, course_id);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            Array preReqArray = res.getArray("pre_req");
            if (preReqArray == null) {
                return true;
            }
            String[][] preReqList = (String[][]) preReqArray.getArray();
            for (String[] prereqList : preReqList) {
                boolean flag = true;
                for (String prereq : prereqList) {
                    if (!prereq.isEmpty()) {
                        flag = checkIfPassedCourse(prereq);
                        if (!flag) {
                            break;
                        }
                    }
                }
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }
    

    public float getCreditsForCourse(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select C from courses where course_id = ?");
        st.setString(1, course_id);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getFloat("C");
        }
        return 0;
    }

    public float getCreditsRegisteredSemX(int year, int sem) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select courses.C from student_course,courses where student_course.course_id = courses.course_id and student_id = ? and sem = ? and year = ?");
        st.setString(1, username);
        st.setInt(2, sem);
        st.setInt(3, year);
        ResultSet res = st.executeQuery();
        float sum = 0;
        while (res.next()) {
            sum += res.getFloat("C");
        }
        return sum;
    }

    public float getCreditsEarnedSemX(int year, int sem) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select course_id,credits from grades where student_id = ? and sem = ? and year = ?");
        st.setString(1, username);
        st.setInt(2, sem);
        st.setInt(3, year);
        ResultSet res = st.executeQuery();
        float sum = 0;
        while (res.next()) {
            if (checkIfPassedCourse(res.getString("course_id")))
                sum += res.getFloat("credits");
        }
        return sum;
    }

    public boolean checkIfPassedCourse(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select * from grades where student_id = ? and course_id = ?");
        st.setString(1, username);
        st.setString(2, course_id);
        ResultSet res = st.executeQuery();
        while (res.next()) {
            if (res.getInt("grade") >= 4) {
                return true;
            }
        }
        return false;
    }

    public float getMinCGPAForCourse(String course_id) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select cg_constraint from offering where course_id = ? and sem = ? and year = ?");
        st.setString(1, course_id);
        st.setInt(2, getCurrentSem());
        st.setInt(3, getCurrentYear());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return res.getFloat("cg_constraint");
        }
        return 0;
    }

    public ResultSet getGrades() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select distinct student_id,course_id,credits,grade from grades where student_id=? and course_id not in (select course_id from grades where student_id=? and grade<grades.grade)");
        st.setString(1, username);
        st.setString(2, username);
        ResultSet res = st.executeQuery();
        return res;
    }

    public void deregisterCourse(String course_id) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("delete from student_course where student_id = ? and course_id = ?");
        st.setString(1, username);
        st.setString(2, course_id);
        st.executeUpdate();
        System.out.println("Course deregistered");
    }

    public boolean checkCourseRegistrationThisSem(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select * from student_course where student_id = ? and course_id = ? and year = ? and sem = ?");
        st.setString(1, username);
        st.setString(2, course_id);
        st.setInt(3, getCurrentYear());
        st.setInt(4, getCurrentSem());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public ResultSet getRegisteredCourses() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select * from student_course where student_id = ?");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        return res;
    }

    public ResultSet getOfferedCoursesThisSem() throws SQLException {
        PreparedStatement st = conn.prepareStatement("select * from offering where year = ? and sem = ?");
        st.setInt(1, getCurrentYear());
        st.setInt(2, getCurrentSem());
        ResultSet res = st.executeQuery();
        return res;
    }

    public boolean checkCourseOfferThisSem(String course_id) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select * from offering where course_id = ? and year = ? and sem = ?");
        st.setString(1, course_id);
        st.setInt(2, getCurrentYear());
        st.setInt(3, getCurrentSem());
        ResultSet res = st.executeQuery();
        if (res.next()) {
            return true;
        }
        return false;
    }

    public ResultSet getCoursesRegisteredForSemx(int year, int sem) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select * from student_course where student_id = ? and sem = ? and year=?");
        st.setString(1, username);
        st.setInt(2, sem);
        st.setInt(3, year);
        ResultSet res = st.executeQuery();
        return res;
    }

    public boolean checkIfCourseCompleted(String course_id) throws SQLException {
        PreparedStatement st = conn
                .prepareStatement("select * from student_course where student_id = ? and course_id = ?");
        st.setString(1, username);
        st.setString(2, course_id);
        ResultSet res = st.executeQuery();
        if (res.next())
            if (res.getString("status").equals("C")) {
                return true;
            }
        return false;
    }

    public void registerCourse(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("insert into student_course values(?, ?, ?, ?, ?)");
        st.setString(1, username);
        st.setString(2, course_id);
        st.setInt(3, getCurrentSem());
        st.setInt(4, getCurrentYear());
        st.setString(5, "R");
        st.executeUpdate();
        System.out.println("Course registered");
    }

    public boolean checkIfProgramCore(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select PC from degree_criteria,students where students.student_id=? and students.dept= degree_criteria.dept and students.year_of_entry = degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            Array pcArray = res.getArray("PC");
            String[] courses = (String[]) pcArray.getArray();
            for (String course : courses) {
                if (course_id.equals(course))
                    return true;
            }
        }
        return false;
    }

    public boolean checkIfBTP(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select btp from degree_criteria,students where students.student_id=? and students.dept= degree_criteria.dept and students.year_of_entry = degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (res.next()) {
            Array pcArray = res.getArray("btp");
            String[] courses = (String[]) pcArray.getArray();
            for (String course : courses) {
                if (course_id.equals(course))
                    return true;
            }
        }
        return false;
    }

    public boolean checkIfBTPCompleted() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select BTP from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        res.next();
        Array array = res.getArray("BTP");
        String[] courses = (String[]) array.getArray();
        for (String course : courses) {
            if (!checkIfPassedCourse(course)) {
                return false;
            }
        }
        return true;
    }

    public String[] getIncompleteProgramCore() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select PC from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        Array array = null;
        if (res.next())
            array = res.getArray(1);
        else {
            return null;
        }
        String[] courses = (String[]) array.getArray();
        ArrayList<String> remaining = new ArrayList<String>();
        for (String course : courses) {
            if (!checkIfPassedCourse(course)) {
                remaining.add(course);
            }
        }
        return remaining.toArray(new String[remaining.size()]);
    }

    public float getRemainingHS() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select HSmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (!res.next())
            return 0;
        float credits = res.getFloat(1);
        st = conn.prepareStatement(
                "select grades.course_id,credits from grades,courses where student_id=? and grades.course_id=courses.course_id and courses.dept='hs'");
        st.setString(1, username);
        res = st.executeQuery();
        while (res.next()) {
            if (!checkIfProgramCore(res.getString("course_id")))
                if (checkIfPassedCourse(res.getString("course_id")))
                    credits -= res.getFloat("credits");
        }
        return credits;
    }

    public float getRemainingSC() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select SCmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (!res.next())
            return 0;
        float credits = res.getFloat(1);
        st = conn.prepareStatement(
                "select grades.course_id,credits from grades,courses where student_id=? and grades.course_id=courses.course_id and (courses.dept='ph' or courses.dept='ma' or courses.dept='bio' or courses.dept='cy')");
        st.setString(1, username);
        res = st.executeQuery();
        while (res.next()) {
            if (!checkIfProgramCore(res.getString("course_id")))
                if (checkIfPassedCourse(res.getString("course_id")))
                    credits -= res.getFloat("credits");
        }
        return credits;
    }

    public float getRemainingPE() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select PEmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (!res.next())
            return 0;
        float credits = res.getFloat(1);
        st = conn.prepareStatement(
                "select grades.course_id,credits from grades,courses,students where grades.student_id=students.student_id and grades.student_id=? and grades.course_id=courses.course_id and courses.dept=students.dept");
        st.setString(1, username);
        res = st.executeQuery();
        while (res.next()) {
            if (!checkIfProgramCore(res.getString("course_id")))
                if (checkIfPassedCourse(res.getString("course_id")))
                    credits -= res.getFloat("credits");
        }
        return credits;
    }

    public String getDepartment(String course_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("select dept from courses where course_id = ?");
        st.setString(1, course_id);
        ResultSet res = st.executeQuery();
        st = conn.prepareStatement("select * from students where student_id = ?");
        st.setString(1, username);
        ResultSet res1 = st.executeQuery();
        res1.next();
        if (res.next()) {
            String dept = res.getString("dept");
            if (dept.equals("hs"))
                return "hs";
            else if (dept.equals("ma") || dept.equals("bio") || dept.equals("ph") || dept.equals("cy"))
                return "sc";
            else if (dept.equals(res1.getString("dept")))
                return "pc";
            else
                return dept;
        } else
            return "NaN";
    }

    public float getHSmin() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select HSmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        res.next();
        return res.getFloat("HSmin");
    }

    public float getPEmin() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select PEmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        res.next();
        return res.getFloat("PEmin");
    }

    public float getSCmin() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select SCmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        res.next();
        return res.getFloat("SCmin");
    }

    public float getRemainingOE() throws SQLException {
        PreparedStatement st = conn.prepareStatement(
                "select OEmin from degree_criteria,students where students.student_id=? and degree_criteria.dept=students.dept and students.year_of_entry=degree_criteria.year");
        st.setString(1, username);
        ResultSet res = st.executeQuery();
        if (!res.next())
            return 0;
        float OE = res.getFloat(1);
        st = conn.prepareStatement("select * from grades where student_id = ? order by year,sem,credits");
        st.setString(1, username);
        res = st.executeQuery();
        float HS = getHSmin();
        float PE = getPEmin();
        float SC = getSCmin();
        while (res.next()) {
            String course= res.getString("course_id");
            if (checkIfPassedCourse(res.getString("course_id")))
                if (!checkIfProgramCore(res.getString("course_id")))
                    if (!checkIfBTP(res.getString("course_id"))) {
                        if (getDepartment(res.getString("course_id")).equals("hs")) {
                            if (HS > 0)
                                HS -= res.getFloat("credits");
                            else
                                OE -= res.getFloat("credits");
                        } else if (getDepartment(res.getString("course_id")).equals("sc")) {
                            if (SC > 0)
                                SC -= res.getFloat("credits");
                            else
                                OE -= res.getFloat("credits");
                        } else if (getDepartment(res.getString("course_id")).equals("pc")) {
                            if (PE > 0)
                                PE -= res.getFloat("credits");
                            else
                                OE -= res.getFloat("credits");
                        } else {
                            OE -= res.getFloat("credits");
                        }
                    }
        }
        return OE;
    }

}
