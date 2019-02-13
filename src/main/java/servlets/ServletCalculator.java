package servlets;

import dao.ActionDAOImpl;
import dao.UserDAOImpl;
import models.ActionModel;
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

public class ServletCalculator extends HttpServlet {

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
            actionDAO.addAction(new ActionModel(action, user));
        } else {
            writer.println("User is not authorized!");
        }
    }

}
