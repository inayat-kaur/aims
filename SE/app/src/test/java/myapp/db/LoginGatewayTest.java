package myapp.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

public class LoginGatewayTest {

    static Connection conn = null;

    @BeforeAll
    public static void getConn() {
        conn = DatabaseConnection.connectToServer();
    }

    @Test
    public void testSuccessfulLogin() throws SQLException{
        LoginGateway gateway = new LoginGateway();
        boolean result = gateway.login("dean@email", "dean");
        assertTrue(result);
        assertEquals(0, gateway.getRole());
        result = gateway.login("acad@email", "acad");
        assertTrue(result);
        assertEquals(1, gateway.getRole());
        result = gateway.login("cs1@email", "cs1");
        assertTrue(result);
        assertEquals(2, gateway.getRole());
        result = gateway.login("s1cs@email", "s1cs");
        assertTrue(result);
        assertEquals(3, gateway.getRole());
    }

    @Test
    public void testUnsuccessfulLogin() throws SQLException{
        LoginGateway gateway = new LoginGateway();
        boolean result = gateway.login("s1cs@email", "s2");
        assertFalse(result);
        assertEquals(-1, gateway.getRole());
        result = gateway.login("s1@mail", "s1");
        assertFalse(result);
        assertEquals(-1, gateway.getRole());
    }

    @Test
    public void testLogout() throws SQLException{
        LoginGateway gateway = new LoginGateway();
        gateway.login("s7ee@email", "s7ee");
        gateway.logout();
        assertEquals(-1, gateway.getRole());
    }

}
