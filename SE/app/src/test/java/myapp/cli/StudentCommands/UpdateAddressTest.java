package myapp.cli.StudentCommands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;

public class UpdateAddressTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testUpdateAddress() throws Exception{
        UpdateAddress up = new UpdateAddress();
        up.username = "s1cs@email";
        up.address = "new address";
        up.call();
        assertTrue(outContent.toString().contains("Address updated successfully"));
    }

}
