package myapp.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class AcadSecGatewayTest {
    
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
        String username = "acad@email";
        AcadSecGateway gateway = new AcadSecGateway(username);
        assertNotNull(gateway);
    }

    @Test
    public void testGetCurrentYear()  throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        assertEquals(2019, gateway.getCurrentYear());
    }

    @Test
    public void testGetCurrentSem() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        assertEquals(1, gateway.getCurrentSem());
    }

    @Test
    public void testCheckEvent() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        assertEquals(true, gateway.checkEvent("ongoing"));
        assertEquals(false, gateway.checkEvent("gradingg"));
    }

    @Test
    public void testAddRemoveEvent() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        gateway.addEvent("test", Date.valueOf("2019-01-01"), Date.valueOf("2019-01-01"));
        assertTrue(outContent.toString().contains("Event added"));
        gateway.removeEvent("test");
        assertTrue(outContent.toString().contains("Event removed"));
    }

    @Test
    public void testUpdateSem() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        Date startDate = Date.valueOf("2019-07-01");
        Date endDate = Date.valueOf("2019-12-31");
        gateway.updateSem(startDate, endDate);
        PreparedStatement st = conn.prepareStatement("delete from current_event where event = 'ongoing'");
        st.executeUpdate();
        st = conn.prepareStatement("update current_event set event = 'ongoing' where event = 'completed' and sem = 1 and year = 2019");
        st.executeUpdate();
    }

    @Test
    public void testGetStudentSem() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        assertEquals(2, gateway.getStudentSem("s1cs@email"));
        assertEquals(0, gateway.getStudentSem("test2@email"));
    }

    @Test
    public void testGetGrades() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        ResultSet res = gateway.getGrades("s1cs@email");
        assertTrue(res.next());
        res = gateway.getGrades("test1@email");
        assertEquals(false, res.next());
    }

    @Test
    public void testGetSemGrades() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        ResultSet res = gateway.getSemGrades("s1cs@email", 1);
        assertTrue(res.next());
        res = gateway.getSemGrades("s1cs@email", 2);
        assertEquals(false, res.next());
    }

    @Test
    public void testCheckAuthority() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        assertEquals(false, gateway.checkAuthority());
        AcadSecGateway gateway2 = new AcadSecGateway("dean@email");
        assertEquals(true, gateway2.checkAuthority());
    }

    @Test
    public void testCheckCourse() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        assertEquals(true, gateway.checkCourse("cs101"));
        assertEquals(false, gateway.checkCourse("test"));
    }

    @Test
    public void testAddRemoveCourse() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        gateway.addCourse("ge101","ge","3-4-5-6","NaN");
        assertTrue(outContent.toString().contains("Course added"));
        gateway.addCourse("cp103","ge","3-4-5-6","cp101|cp102");
        assertTrue(outContent.toString().contains("Course added"));
        gateway.removeCourse("ge101");
        assertTrue(outContent.toString().contains("Course removed"));
        gateway.removeCourse("cp103");
        assertTrue(outContent.toString().contains("Course removed"));
    }

    @Test
    public void testAddDegreeCriteria() throws SQLException{
        AcadSecGateway gateway = new AcadSecGateway("acad@email");
        String[] PC = {"cy101","cy102"};
        String[] btp = {"cp101","cp102"};
        float PEmin = 9;
        float SCmin = 9;
        float HSmin = 9;
        float OEmin = 9;
        gateway.addDegreeCriteria("hs","mtech",PC,PEmin,HSmin,SCmin,OEmin,btp);
        assertTrue(outContent.toString().contains("Degree criteria added"));
        PreparedStatement stmt = conn.prepareStatement("delete from degree_criteria where dept = 'hs'");
        stmt.executeUpdate();
    }

}
