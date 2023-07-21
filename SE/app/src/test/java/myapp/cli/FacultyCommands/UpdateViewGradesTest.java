package myapp.cli.FacultyCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import myapp.db.DatabaseConnection;

public class UpdateViewGradesTest {
    
    private static ByteArrayOutputStream outContent;
    private static Connection conn;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        conn  = DatabaseConnection.connectToServer();
    }

    @Test
    public void testViewGrades() throws Exception{
        UpdateGrades ug = new UpdateGrades();
        ug.username = "cs1@email";
        ug.course_id = "cs301";
        ug.fileloc = "Add your path\\SE\\app\\src\\test\\resources\\testUpdateGrades.csv";
        ug.call();
        assertTrue(outContent.toString().contains("Course not offered"));
        ug.course_id = "cp101";
        ug.username ="ce1@email";
        ug.call();
        assertTrue(outContent.toString().contains("Cannot update grades now"));
        PreparedStatement st = conn.prepareStatement("insert into current_event values('grading',1,2019,'2019-01-01','2019-01-10')");
        st.executeUpdate();
        ug.call();
        assertTrue(outContent.toString().contains("Grades updated successfully"));
        st = conn.prepareStatement("delete from current_event where event = 'grading' and year=2019");
        st.executeUpdate();
        ViewGrades vg = new ViewGrades();
        vg.username = "ce1@email";
        vg.course_id = "cp101";
        vg.sem = 1;
        vg.year=2019;
        vg.call();
        assertTrue(outContent.toString().contains("s5ce@email 5"));
        st =conn.prepareStatement("delete from grades where year = 2019");
        st.executeUpdate();
        st = conn.prepareStatement("update student_course set status='R' where year=2019 and course_id = 'cp101'");
        st.executeUpdate();
    }
}
