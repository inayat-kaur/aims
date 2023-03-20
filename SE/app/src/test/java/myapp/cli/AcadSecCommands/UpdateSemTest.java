package myapp.cli.AcadSecCommands;

import org.junit.jupiter.api.Test;

import myapp.db.DatabaseConnection;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UpdateSemTest {
    
    private static ByteArrayOutputStream outContent;
    private static Connection conn;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        conn = DatabaseConnection.connectToServer();
    }
    @Test
    public void testUpdateSem() throws Exception{
        UpdateSem us = new UpdateSem();
        us.username = "acad@email";
        us.startDate = java.sql.Date.valueOf("2020-01-01");
        us.endDate = java.sql.Date.valueOf("2020-06-01");
        us.call();
        assertTrue(outContent.toString().contains("Semester updated"));
        PreparedStatement st = conn.prepareStatement("delete from current_event where event = 'ongoing'");
        st.executeUpdate();
        st = conn.prepareStatement("update current_event set event = 'ongoing' where event = 'completed' and year =2019");
        st.executeUpdate();
    }

}
