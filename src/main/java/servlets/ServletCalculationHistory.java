package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class ServletCalculationHistory extends HttpServlet {

    private Connection connection;

    public ServletCalculationHistory(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder();
        try {
            String sql = "SELECT * FROM history";
            connection.prepareStatement(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while ((resultSet.next())) {
                int a = resultSet.getInt("a");
                int b = resultSet.getInt("b");
                String op = resultSet.getString("op");
                int res = resultSet.getInt("result");
                Timestamp at = resultSet.getTimestamp("at");
                result
                        .append(a)
                        .append(op)
                        .append(b)
                        .append(" = ")
                        .append(res)
                        .append(" ")
                        .append("at:")
                        .append(at)
                        .append("\n");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Error", e);
        }
        resp.getWriter().println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}