package dao;

import models.ActionModel;
import models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionDAOImpl implements ActionDAO {

    private static ActionDAOImpl instance = new ActionDAOImpl();

    private List<ActionModel> userActionList;

    private ActionDAOImpl() {
        this.userActionList = new ArrayList<>();
    }

    public static ActionDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addAction(ActionModel action) {
        return this.userActionList.add(action);
    }

    @Override
    public List<ActionModel> getUserActions(UserModel user) {
        return this.userActionList
                .stream()
                .filter(e -> user.equals(e.getUser()))
                .collect(Collectors.toList());
    }

}