package myapp.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

public class FacultyGatewayTest {

    static Connection conn = null;
    static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn() {
        conn = DatabaseConnection.connectToServer();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testConstructor() {
        String username = "cs1@email";
        FacultyGateway gateway = new FacultyGateway(username);
        assertNotNull(gateway);
    }

    @Test
    public void testGetName() throws SQLException{
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        assertEquals("cs1", gateway.getName());
        gateway = new FacultyGateway("f@email");
        assertEquals(null, gateway.getName());
    }

    @Test
    public void testUpdatePhone() throws SQLException {
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        gateway.updatePhone("9999999999");
        assertTrue(outContent.toString().contains("Phone number updated successfully"));
    }

    @Test
    public void testUpdateAddress() throws SQLException {
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        gateway.updateAddress("Street 1, City");
        assertTrue(outContent.toString().contains("Address updated successfully"));
    }

    @Test
    public void testGetCurrentYear() throws SQLException {
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        assertEquals(2019, gateway.getCurrentYear());
    }

    @Test
    public void testGetCurrentSem() throws SQLException {
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        assertEquals(1, gateway.getCurrentSem());
    }

    @Test
    public void testCheckEvent() throws SQLException {
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        assertEquals(true, gateway.checkEvent("ongoing"));
        assertEquals(false, gateway.checkEvent("gradingg"));
    }

    @Test
    public void testGetCourseCredits() throws SQLException{
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        assertTrue(Double.compare(3, gateway.getCourseCredits("cs101")) == 0);
        assertTrue(Double.compare(3, gateway.getCourseCredits("ma101")) == 0);
    }

    @Test
    public void testCheckIfOffered() throws SQLException{
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        assertEquals(true, gateway.checkIfOffered("cs101"));
        assertEquals(false, gateway.checkIfOffered("me102"));
    }

    @Test
    public void testRegisterDeregisterCourse() throws SQLException {
        FacultyGateway gateway = new FacultyGateway("me1@email");
        gateway.registerCourse("me101", 2);
        assertTrue(outContent.toString().contains("Course registered successfully"));
        gateway.deregisterStudents("me101");
        assertTrue(outContent.toString().contains("Students deregistered successfully"));
        gateway.deregisterCourse("me101");
        assertTrue(outContent.toString().contains("Course deregistered successfully"));
    }

    @Test
    public void testGetGrades() throws SQLException{
        FacultyGateway gateway = new FacultyGateway("cs1@email");
        ResultSet rs = gateway.getGrades("cs101",2018,2);
        assertTrue(rs.next());
        rs = gateway.getGrades("cs101",2019,1);
        assertFalse(rs.next());
    }

    @Test
    public void testUpdateGrades() throws SQLException{
        FacultyGateway gateway = new FacultyGateway("ce1@email");
        ArrayList<String> students = new ArrayList<String>();
        students.add("s5ce@email");
        ArrayList<Integer> grades = new ArrayList<Integer>();
        grades.add(6);
        gateway.updateGrades(students,grades,"cp101");
        assertTrue(outContent.toString().contains("Grades updated successfully"));
        PreparedStatement st = conn.prepareStatement("delete from grades where year = 2019 and sem =1");
        st.executeUpdate();
        st = conn.prepareStatement("update student_course set status = 'R' where sem =1 and year = 2019");
        st.executeUpdate();
    }

}
