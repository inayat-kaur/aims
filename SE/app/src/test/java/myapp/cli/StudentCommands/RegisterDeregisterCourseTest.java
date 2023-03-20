package myapp.cli.StudentCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import myapp.db.DatabaseConnection;

public class RegisterDeregisterCourseTest {
    
    private static ByteArrayOutputStream outContent;
    private static Connection conn;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        conn = DatabaseConnection.connectToServer();
    }

    @Test
    public void testRegisterDeregisterCourse() throws Exception{
        RegisterCourses rc = new RegisterCourses();
        rc.username = "s1cs@email";
        rc.course_id = "cs301";
        rc.call();
        assertTrue(outContent.toString().contains("Course not offered this semester"));
        rc.course_id = "cs101";
        rc.call();
        assertTrue(outContent.toString().contains("Course already completed"));
        rc.course_id = "cp101";
        rc.call();
        assertTrue(outContent.toString().contains("Course registered"));
        DeregisterCourse dc = new DeregisterCourse();
        dc.username = "s1cs@email";
        dc.course_id = "cs101";
        dc.call();
        assertTrue(outContent.toString().contains("You are not registered for this course in this semester"));
        dc.course_id = "cp101";
        dc.call();
        assertTrue(outContent.toString().contains("Course deregistered"));
    }

}
