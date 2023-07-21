package myapp.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class StudentGatewayTest {

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
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertNotNull(gateway);
    }

    @Test
    public void testGetName() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals("s1cs", gateway.getName());
        gateway = new StudentGateway("s@email");
        assertEquals(null, gateway.getName());
    }

    @Test
    public void testUpdatePhone() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        gateway.updatePhone("9999999999");
        assertTrue(outContent.toString().contains("Phone number updated successfully"));
    }

    @Test
    public void testUpdateAddress() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        gateway.updateAddress("1234 Main St");
        assertTrue(outContent.toString().contains("Address updated successfully"));
    }

    @Test
    public void testGetCurrentYear() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(2019, gateway.getCurrentYear());
    }

    @Test
    public void testGetCurrentSem() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(1, gateway.getCurrentSem());
    }

    @Test
    public void testCheckEvent() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(true, gateway.checkEvent("ongoing"));
        assertEquals(false, gateway.checkEvent("gradingg"));
    }

    @Test
    public void testGetCGPA() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        float cgpa = gateway.getCGPA();
        assertEquals(9, cgpa);
    }

    @Test
    public void testGetStudentSem() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        int sem = gateway.getStudentSem();
        assertEquals(2, sem);
    }

    @Test
    public void testCheckPrereq() throws SQLException {
        StudentGateway gateway = new StudentGateway("s2cs@email");
        assertEquals(true, gateway.checkPrereq("cs202"));
        assertEquals(false, gateway.checkPrereq("cs301"));
    }

    @Test
    public void testGetCreditsForCourse() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(3, gateway.getCreditsForCourse("cs101"));
    }

    @Test
    public void testGetRegisteredSemX() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(19.5, gateway.getCreditsRegisteredSemX(2018, 2));
    }

    @Test
    public void testGetCreditsEarnedSemX() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(19.5, gateway.getCreditsEarnedSemX(2018, 2));
    }

    @Test
    public void testCheckIfPassedCourse() throws SQLException {
        StudentGateway gateway = new StudentGateway("s3me@email");
        assertEquals(true, gateway.checkIfPassedCourse("me101"));
        assertEquals(false, gateway.checkIfPassedCourse("ph101"));
    }

    @Test
    public void testGetMinCGPAForCourse() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(0.0, gateway.getMinCGPAForCourse("cs101"));
    }

    @Test
    public void testGetGrades() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        ResultSet res = gateway.getGrades();
        assertTrue(res.next());
    }

    @Test
    public void testRegisterDeregisterCourse() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        gateway.registerCourse("cp101");
        assertTrue(outContent.toString().contains("Course registered"));
        gateway.deregisterCourse("cp101");
        assertTrue(outContent.toString().contains("Course deregistered"));
    }

    @Test
    public void testCheckCourseRegistrationThisSem() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(false, gateway.checkCourseRegistrationThisSem("cp101"));
        gateway.registerCourse("cp101");
        assertEquals(true, gateway.checkCourseRegistrationThisSem("cp101"));
        gateway.deregisterCourse("cp101");
    }

    @Test
    public void testGetRegisteredCourses() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        ResultSet res = gateway.getRegisteredCourses();
        assertTrue(res.next());
    }

    @Test
    public void testGetOfferedCoursesThisSem() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        ResultSet res = gateway.getOfferedCoursesThisSem();
        assertTrue(res.next());
    }

    @Test
    public void testCheckCourseOfferThisSem() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(true, gateway.checkCourseOfferThisSem("cs101"));
        assertEquals(false, gateway.checkCourseOfferThisSem("cs301"));
    }

    @Test
    public void testGetCoursesRegisteredForSemx() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        ResultSet res = gateway.getCoursesRegisteredForSemx(2018, 2);
        assertTrue(res.next());
        res = gateway.getCoursesRegisteredForSemx(2019, 1);
        assertFalse(res.next());
    }

    @Test
    public void testIfCourseCompleted() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(gateway.checkIfCourseCompleted("cs101"), true);
        assertEquals(gateway.checkIfCourseCompleted("cs301"), false);

    }

    @Test
    public void testCheckIfProgramCore() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(true, gateway.checkIfProgramCore("cs101"));
        assertEquals(false, gateway.checkIfProgramCore("cs301"));
    }

    @Test
    public void testCheckIfBTPCompleted() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(false, gateway.checkIfBTPCompleted());
        gateway = new StudentGateway("s8ee@email");
        assertEquals(true, gateway.checkIfBTPCompleted());
    }

    @Test
    public void testGetIncompleteProgramCore() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        String[] res = gateway.getIncompleteProgramCore();
        String[] out = { "cs202" };
        assertTrue(res[0].equals(out[0]));
    }

    @Test
    public void testGetRemainingPE() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(3,gateway.getRemainingPE());

    }

    @Test
    public void testGetRemainingHS() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(3,gateway.getRemainingHS());
    }

    @Test
    public void testGetRemainingSC() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(3,gateway.getRemainingSC());
    }

    @Test
    public void testGetRemainingOE() throws SQLException {
        StudentGateway gateway = new StudentGateway("s1cs@email");
        assertEquals(3,gateway.getRemainingOE());
        gateway = new StudentGateway("s7ee@email");
        assertEquals(0,gateway.getRemainingOE());
        gateway = new StudentGateway("s8ee@email");
        assertEquals(3,gateway.getRemainingOE());
        gateway = new StudentGateway("test@email");
        assertEquals(3,gateway.getRemainingOE());
    }

}
