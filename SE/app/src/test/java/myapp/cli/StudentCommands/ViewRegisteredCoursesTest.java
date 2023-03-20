package myapp.cli.StudentCommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ViewRegisteredCoursesTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testViewRegisteredCourses() throws Exception{
        ViewRegisteredCourses vrc = new ViewRegisteredCourses();
        vrc.username = "s1cs@email";
        vrc.call();
        assertTrue(outContent.toString().contains("cs101"));
        assertFalse(outContent.toString().contains("me101"));
    }

}
