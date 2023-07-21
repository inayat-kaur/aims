package myapp.cli.AcadSecCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AddRemoveEventTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddRemoveEvent() throws Exception{
        AddEvent addEvent = new AddEvent();
        addEvent.username = "acad@email";
        addEvent.event = "event1";
        addEvent.startDate = "2021-01-01";
        addEvent.endDate = "2021-01-02";
        addEvent.call();
        assertTrue(outContent.toString().contains("Event added"));
        addEvent.event = "ongoing";
        addEvent.call();
        assertTrue(outContent.toString().contains("Event already exists"));
        RemoveEvent removeEvent = new RemoveEvent();
        removeEvent.username = "acad@email";
        removeEvent.event = "event1";
        removeEvent.call();
        assertTrue(outContent.toString().contains("Event removed"));
        removeEvent.event = "event2";
        removeEvent.call();
        assertTrue(outContent.toString().contains("Event does not exist"));
    }

}
