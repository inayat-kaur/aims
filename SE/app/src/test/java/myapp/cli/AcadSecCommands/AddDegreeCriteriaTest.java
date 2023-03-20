package myapp.cli.AcadSecCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import myapp.db.DatabaseConnection;

public class AddDegreeCriteriaTest {
    
    private static ByteArrayOutputStream outContent;
    private static Connection conn;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        conn = DatabaseConnection.connectToServer();
    }

    @Test
    public void testAddDegreeCriteria() throws Exception{
        AddDegreeCriteria addDegreeCriteria = new AddDegreeCriteria();
        addDegreeCriteria.username = "acad@email";
        addDegreeCriteria.dept = "cs";
        addDegreeCriteria.program = "mtech";
        addDegreeCriteria.programCore = "p1,p2,p3";
        addDegreeCriteria.programElective = 10;
        addDegreeCriteria.humanitiesElective = 10;
        addDegreeCriteria.scienceElective = 10;
        addDegreeCriteria.openElective = 10;
        addDegreeCriteria.btp = "btp1,btp2,btp3";
        addDegreeCriteria.call();
        assertTrue(outContent.toString().contains("Degree criteria added"));
        PreparedStatement st = conn.prepareStatement("delete from degree_criteria where dept = ? and program = ?");
        st.setString(1, "cs");
        st.setString(2, "mtech");
        st.executeUpdate();
    }
}
