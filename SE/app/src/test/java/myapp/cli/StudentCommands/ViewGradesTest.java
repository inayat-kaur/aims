package myapp.cli.StudentCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ViewGradesTest {
    
    private static ByteArrayOutputStream outContent;
    
    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testViewGrades() throws Exception{
        ViewGrades vg = new ViewGrades();
        vg.username = "s1cs@email";
        vg.call();
        assertTrue(outContent.toString().contains("cs101 10"));
    }

}
