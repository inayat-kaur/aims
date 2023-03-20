package myapp.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;

public class AcadSecTest {

    static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAcadSec(){
        System.setIn(new ByteArrayInputStream("9".getBytes()));
        AcadSec acadSec = new AcadSec("acad@email","acad");
        assertTrue(outContent.toString().contains("Welcome acad@email"));
    }

}
