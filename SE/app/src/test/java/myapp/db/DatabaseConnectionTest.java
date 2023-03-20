package myapp.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {
    
    @Test
    public void testConnection() {
        DatabaseConnection.connectToServer();
        assertNotNull(DatabaseConnection.connectToServer());
    }
    
}
