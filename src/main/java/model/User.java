package model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import db.DataBase;
import login.Session;
import login.SessionManager;
import webserver.HttpRequest;

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


    public static User createUserByParameter(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        String name = parameters.get("name");
        String email = parameters.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        return user;
    }

    public static User login(HttpRequest httpRequest) {
        Map<String, String> parameter = httpRequest.getHttpRequestBody().getBody();

        User user = DataBase.findUserById(parameter.get("userId"));

        if (user == null) {
            throw new IllegalArgumentException("해당하는 유저가 없습니다.");
        }

        if (!Objects.equals(user.password, parameter.get("password"))) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return user;
    }
}
