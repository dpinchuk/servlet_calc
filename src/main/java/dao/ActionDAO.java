package dao;

import models.ActionModel;
import models.UserModel;

import java.util.List;

public interface ActionDAO {

    boolean addAction(ActionModel action);

    List<ActionModel> getUserActions(UserModel user);

}