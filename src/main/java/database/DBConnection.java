package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private String path = "jdbc:mysql://localhost:3306/dimadb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String user = "root";
    private String password = "1111";
    private Connection connection;

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(this.path, this.user, this.password);
    }

    public Connection connection() {
        if (connection == null) {
            try {
                connection = connect();
            } catch (SQLException e) {
                throw new IllegalStateException("Error", e);
            }
        }
        return this.connection;
    }

}
