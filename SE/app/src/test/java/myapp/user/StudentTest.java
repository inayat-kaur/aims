package myapp.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;

public class StudentTest {

    static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testStudent() throws Exception{
        System.setIn(new ByteArrayInputStream("9".getBytes()));
        Student st = new Student("s1cs@email","s1cs");
        assertTrue(outContent.toString().contains("Welcome s1cs"));
    }

}

