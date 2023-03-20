package myapp.cli.AcadSecCommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AcadSecCommandTest {

    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }
    
    @Test
    public void testAcadSecCommand() throws Exception{
        AcadSecCommand ac = new AcadSecCommand();
        ac.username = null;
        ac.acadSecCommand = 1;
        ac.call();
        assertTrue(outContent.toString().contains("Error in adding event"));
        ac.acadSecCommand = 2;
        ac.call();
        assertTrue(outContent.toString().contains("Error in removing event"));
        ac.acadSecCommand = 3;
        ac.call();
        assertTrue(outContent.toString().contains("Error in updating semester"));
        ac.acadSecCommand = 4;
        ac.call();
        assertTrue(outContent.toString().contains("Error in adding course"));
        ac.acadSecCommand = 5;
        ac.call();
        assertTrue(outContent.toString().contains("Error in removing course"));
        ac.acadSecCommand = 6;
        ac.call();
        assertTrue(outContent.toString().contains("Error in viewing grades"));
        ac.acadSecCommand = 7;
        ac.call();
        assertTrue(outContent.toString().contains("Error in generating transcript"));
        ac.acadSecCommand = 8;
        ac.call();
        assertTrue(outContent.toString().contains("Error in adding degree criteria"));
        ac.acadSecCommand = 9;
        assertEquals(0,ac.call());
        ac.acadSecCommand = 10;
        ac.call();
        assertTrue(outContent.toString().contains("Invalid Academic Section Command"));
    }

}
