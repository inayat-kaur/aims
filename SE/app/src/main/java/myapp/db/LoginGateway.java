package myapp.db;

import java.sql.*;

public class LoginGateway {
    private int role = -1;
    private String username;
    private Connection conn;

    public LoginGateway() {
        conn = DatabaseConnection.connectToServer();
    }

    public boolean login(String username, String password) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            String pass = rs.getString(2);
            if (!pass.equals(password)) {
                return false;
            }
            this.username = username;
            this.role = rs.getInt(3);
            st = conn.prepareStatement("insert into log values(?, ?, 'login')");
            st.setString(1, username);
            st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            st.executeUpdate();
            return true;
        }
        return false;
    }

    public void logout() throws SQLException {
        this.role = -1;
        PreparedStatement st = conn.prepareStatement("insert into log values(?, ?, 'logout')");
        st.setString(1, username);
        st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        st.executeUpdate();
    }

    public int getRole() {
        return this.role;
    }
}
