package model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import db.DataBase;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }


    public static User createUserByParameter(Map<String, String> parameters) throws UnsupportedEncodingException {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = URLEncoder.encode(parameters.get("name"), "UTF-8");
        String email = parameters.get("email");

        return new User(userId, password, name, email);
    }
}
