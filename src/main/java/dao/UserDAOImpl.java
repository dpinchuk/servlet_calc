package dao;

import models.UserModel;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAOImpl implements UserDAO {

    private static UserDAOImpl instance = new UserDAOImpl();

    private List<UserModel> userList;

    private UserDAOImpl() {
        this.userList = new ArrayList<>();
    }

    public static UserDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addUser(UserModel user) {
        return this.userList.add(user);
    }

    @Override
    public boolean removeUser(UserModel user) {
        return this.userList.remove(user);
    }

    @Override
    public UserModel getUserByLogin(String login) {
        return this.userList
                .stream()
                .filter(e -> login.equals(e.getLogin()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserModel getUserByLoginAndPassword(String login, String password) {
        return this.userList
                .stream()
                .filter(e -> login.equals(e.getLogin()) && password.equals(e.getPassword()))
                .findFirst()
                .orElse(null);
    }

    public UserModel getUserByCookie(Cookie cookie) {
        return this.userList
                .stream()
                .filter(e -> cookie.getValue().equals(String.valueOf(e.hashCode())))
                .findFirst()
                .orElse(null);
    }

}