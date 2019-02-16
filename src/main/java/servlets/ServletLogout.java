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

public class ServletLogout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Files.copy(Paths.get("src/web-app/html/logout_page.html"), resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CookiesObserver cookiesObserver = new CookiesObserver(req);
        PrintWriter writer = resp.getWriter();
        if (cookiesObserver.isCookieSets()) {
            Cookie userCookie = cookiesObserver.getUserCookie();
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            UserModel user = userDAO.getUserByCookie(cookiesObserver.getUserCookie());
            userCookie.setMaxAge(0);
            resp.addCookie(userCookie);
            writer.println("User [" + user.getLogin() + "] is logout!");
            resp.sendRedirect("/login");
        } else {
            writer.println("User is not authorized!");
        }
    }

}