package dao;

import models.UserModel;

import javax.servlet.http.Cookie;

public interface UserDAO {

    boolean addUser(UserModel user);

    boolean removeUser(UserModel user);

    UserModel getUserByLogin(String login);

    UserModel getUserByLoginAndPassword(String login, String password);

    UserModel getUserByCookie(Cookie cookie);

}