package myapp.cli.StudentCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;

public class UpdatePhoneTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testUpdatePhone() throws Exception{
        UpdatePhone up = new UpdatePhone();
        up.username = "s1cs@email";
        up.phone = "1234567890";
        up.call();
        assertTrue(outContent.toString().contains("Phone number updated successfully"));
    }
}
