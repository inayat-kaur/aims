package myapp.cli.StudentCommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentCommandTest {

    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    public void testStudentCommand() throws Exception{
        StudentCommand ac = new StudentCommand();
        ac.username = null;
        ac.studentCommand = 1;
        ac.call();
        assertTrue(outContent.toString().contains("Error registering course"));
        ac.studentCommand = 2;
        ac.call();
        assertTrue(outContent.toString().contains("Error deregistering course"));
        ac.studentCommand = 3;
        ac.call();
        assertTrue(outContent.toString().contains("Error viewing registered courses"));
        ac.studentCommand = 4;
        ac.call();
        assertTrue(outContent.toString().contains("Error viewing grades"));
        ac.studentCommand = 5;
        ac.call();
        assertTrue(outContent.toString().contains("Error getting CGPA"));
        ac.studentCommand = 6;
        ac.call();
        assertTrue(outContent.toString().contains("Error getting graduation status"));
        ac.studentCommand = 7;
        ac.call();
        assertTrue(outContent.toString().contains("Error updating phone no."));
        ac.studentCommand = 8;
        ac.call();
        assertTrue(outContent.toString().contains("Error updating address"));
        ac.studentCommand = 9;
        assertEquals(0,ac.call());
        ac.studentCommand = 10;
        ac.call();
        assertTrue(outContent.toString().contains("Invalid studentCommand"));
    }

}

