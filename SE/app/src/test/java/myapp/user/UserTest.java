package myapp.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testUser(){
        System.setIn(new ByteArrayInputStream("a".getBytes()));
        System.setIn(new ByteArrayInputStream("a".getBytes()));
        User user = new User();
        user.login();
        assertTrue(outContent.toString().contains("Invalid username or password"));
    }

}
