package myapp.cli.StudentCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetGraduationStatusTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testGetCGPA() throws Exception{
        GetGraduationStatus gc = new GetGraduationStatus();
        gc.username = "s1cs@email";
        gc.call();
        assertTrue(outContent.toString().contains("BTP not completed"));
        assertTrue(outContent.toString().contains("Program core courses not completed"));
    }

}
