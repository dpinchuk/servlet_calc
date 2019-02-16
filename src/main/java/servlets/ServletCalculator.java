package servlets;

import dao.ActionDAOImpl;
import dao.UserDAOImpl;
import models.UserModel;
import utils.CookiesObserver;
import utils.ParameterFromRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ServletCalculator extends HttpServlet {

    private final Connection connection;

    public ServletCalculator(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Files.copy(Paths.get("src/web-app/html/form_calc.html"), resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        CookiesObserver cookiesObserver = new CookiesObserver(req);
        PrintWriter writer = resp.getWriter();
        if (cookiesObserver.isCookieSets()) {
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            UserModel user = userDAO.getUserByCookie(cookiesObserver.getUserCookie());

            ActionDAOImpl actionDAO = ActionDAOImpl.getInstance();

            ParameterFromRequest pfr = new ParameterFromRequest(req);
            int a = pfr.getInt("a");
            int b = pfr.getInt("b");
            String command = pfr.getStr("op");
            int result = 0;
            String operation = "";
            switch (command) {
                case "add":
                    result = a + b;
                    operation = "+";
                    break;
                case "sub":
                    result = a - b;
                    operation = "-";
                    break;
                case "mul":
                    result = a * b;
                    operation = "*";
                    break;
                case "div":
                    result = a / b;
                    operation = "/";
                    break;
            }
            String action = String.format("%d %s %d = %d", a, operation, b, result);
            resp.getWriter().printf(action);
            try {
                saveOperationToDB(a, operation, b, result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //actionDAO.addAction(new ActionModel(action, user));
        } else {
            writer.println("User is not authorized!");
        }
    }

    private void saveOperationToDB(int a, String operation, int b, int result) throws SQLException {
        String sql = "insert into history (a, op, b, result) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, a);
        preparedStatement.setString(2, operation);
        preparedStatement.setInt(3, b);
        preparedStatement.setInt(4, result);
        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        preparedStatement.setInt(5, userDAO.hashCode());
        preparedStatement.setString(6, String.valueOf(new Date().getTime()));
        preparedStatement.execute();
    }

}
