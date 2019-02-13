package servlets;

import dao.UserDAOImpl;
import models.UserModel;
import utils.CookiesObserver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utils.Const.COOKIE_NAME;

public class ServletLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Files.copy(Paths.get("src/web-app/html/login_page.html"), resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CookiesObserver cookiesObserver = new CookiesObserver(req);
        PrintWriter writer = resp.getWriter();
        if (!cookiesObserver.isCookieSets()) {
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            UserModel user = userDAO.getUserByLoginAndPassword(login, password);
            if (user != null) {
                Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(user.hashCode()));
                resp.addCookie(cookie);
                writer.println("User [" + login + "] logged in successfully!");
            } else {
                writer.println("User is not found!");
            }
        } else {
            writer.println("User is also authorized!");
        }
    }

}