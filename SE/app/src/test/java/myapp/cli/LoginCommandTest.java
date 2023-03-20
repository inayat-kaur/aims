package myapp.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginCommandTest {

    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    } 
    
    @Test
    public void testLoginCommand() throws Exception{
        LoginCommand lg = new LoginCommand();
        lg.username = "a";
        lg.password = "a";
        int res = lg.call();
        assertTrue( res == 0);
        assertTrue(outContent.toString().contains("Invalid username or password"));
        lg.username = "acad@email";
        lg.password = "acad";
        System.setIn(new ByteArrayInputStream("9".getBytes()));
        res = lg.call();
        assertTrue( res == 0);
        lg.username = "cs1@email";
        lg.password = "cs1";
        System.setIn(new ByteArrayInputStream("7".getBytes()));
        res = lg.call();
        assertTrue( res == 0);
        lg.username = "s1cs@email";
        lg.password = "s1cs";
        System.setIn(new ByteArrayInputStream("9".getBytes()));
        res = lg.call();
        assertTrue( res == 0);
    }
}
