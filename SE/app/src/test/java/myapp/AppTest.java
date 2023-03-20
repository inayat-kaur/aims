package myapp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {

    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMain() throws InterruptedException{
        // Thread thread = new Thread(() -> App.main(new String[] {}));
        // thread.start();
        // Thread.sleep(3000);
        // thread.interrupt();
        // assertTrue(outContent.toString().contains("Welcome to AIMS! Kindly Login"));
    }

}
