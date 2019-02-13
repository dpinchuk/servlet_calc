package servlets;

import dao.ActionDAOImpl;
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

public class ServletActions extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Files.copy(Paths.get("src/web-app/html/actions_page.html"), resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CookiesObserver cookiesObserver = new CookiesObserver(req);
        PrintWriter writer = resp.getWriter();
        if (cookiesObserver.isCookieSets()) {
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            UserModel user = userDAO.getUserByCookie(cookiesObserver.getUserCookie());
            ActionDAOImpl actionDAO = ActionDAOImpl.getInstance();
            writer.println("User [" + user.getLogin() + "] actions history:");
            actionDAO.getUserActions(user)
                    .forEach(e -> writer.println(e.getUserAction()));
        } else {
            writer.println("User is not authorized!");
        }
    }

}