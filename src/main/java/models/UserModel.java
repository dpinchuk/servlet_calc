package models;

import java.util.Objects;

public class UserModel {

    private String login;
    private String password;

    public UserModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return this.login + " - " +
                this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(login, userModel.login) &&
                Objects.equals(password, userModel.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

}