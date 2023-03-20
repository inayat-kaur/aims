package myapp.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;

public class FacultyTest {

    static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testFaculty() throws SQLException{
        System.setIn(new ByteArrayInputStream("7".getBytes()));
        Faculty fac = new Faculty("cs1@email","cs1");
        assertTrue(outContent.toString().contains("Welcome cs1"));
    }

}

