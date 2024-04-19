package model;

import java.util.ArrayList;
import java.util.List;

public class Users {
    List<User> users = new ArrayList<>();

    public Users(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
