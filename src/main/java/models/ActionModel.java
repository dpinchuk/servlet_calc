package models;

import java.util.Objects;

public class ActionModel {

    private String userAction;
    private UserModel user;

    public ActionModel(String userAction, UserModel user) {
        this.userAction = userAction;
        this.user = user;
    }

    public String getUserAction() {
        return userAction;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return userAction + " -> " +
                user.getLogin();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionModel that = (ActionModel) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

}
