package myapp.cli.FacultyCommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FacultyCommandTest {

    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    public void testFacultyCommand() throws Exception{
        FacultyCommand ac = new FacultyCommand();
        ac.username = null;
        ac.facultyCommand = 1;
        ac.call();
        assertTrue(outContent.toString().contains("Error in registering course"));
        ac.facultyCommand = 2;
        ac.call();
        assertTrue(outContent.toString().contains("Error in deregistering course"));
        ac.facultyCommand = 3;
        ac.call();
        assertTrue(outContent.toString().contains("Error in viewing grades"));
        ac.facultyCommand = 4;
        ac.call();
        assertTrue(outContent.toString().contains("Error in updating grades"));
        ac.facultyCommand = 5;
        ac.call();
        assertTrue(outContent.toString().contains("Error in updating phone no."));
        ac.facultyCommand = 6;
        ac.call();
        assertTrue(outContent.toString().contains("Error in updating address"));
        ac.facultyCommand = 7;
        assertEquals(0,ac.call());
        ac.facultyCommand = 8;
        ac.call();
        assertTrue(outContent.toString().contains("Invalid facultyCommand"));
    }

}

