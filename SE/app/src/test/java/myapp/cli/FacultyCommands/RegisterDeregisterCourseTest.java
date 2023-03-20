package myapp.cli.FacultyCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
        RegisterCourse rc = new RegisterCourse();
        rc.username = "cs1@email";
        rc.course_id = "cs101";
        rc.cg_constraint = 0.5f;
        rc.call();
        assertTrue(outContent.toString().contains("Course already offered"));
        PreparedStatement st = conn.prepareStatement("delete from current_event where event = 'offering' and year = 2019");
        st.executeUpdate();
        rc.course_id = "cs301";
        rc.call();
        assertTrue(outContent.toString().contains("Cannot offer course now"));
        st = conn.prepareStatement("insert into current_event values ('offering',1,2019,'2019-01-01','2019-01-10')");
        st.executeUpdate();
        rc.call();
        assertTrue(outContent.toString().contains("Course registered successfully"));
        DeregisterCourse dc = new DeregisterCourse();
        dc.username = "cs1@email";
        dc.course_id = "cs502";
        dc.call();
        assertTrue(outContent.toString().contains("Course not offered"));
        st = conn.prepareStatement("delete from current_event where event = 'takeback_course' and year = 2019");
        st.executeUpdate();
        dc.course_id = "cs301";
        dc.call();
        assertTrue(outContent.toString().contains("Cannot deregister course now"));
        st = conn.prepareStatement("insert into current_event values ('takeback_course',1,2019,'2019-01-01','2019-01-10')");
        st.executeUpdate();
        dc.call();
        assertTrue(outContent.toString().contains("Course deregistered successfully"));
    }
}
