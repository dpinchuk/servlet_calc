package servlets;

import dao.UserDAOImpl;
import models.UserModel;
import utils.CookiesObserver;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServletRegistration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Files.copy(Paths.get("src/web-app/html/registration_page.html"), resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CookiesObserver cookiesObserver = new CookiesObserver(req);
        PrintWriter writer = resp.getWriter();
        if (!cookiesObserver.isCookieSets()) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            if (!login.equals("") && !password.equals("")) {
                if (userDAO.getUserByLogin(login) == null) {
                    userDAO.addUser(new UserModel(login, password));
                } else {
                    writer.println("User is also exist!");
                }
            } else {
                writer.println("Error user's data!");
            }
        } else {
            writer.println("User is also authorized!");
        }
    }

}