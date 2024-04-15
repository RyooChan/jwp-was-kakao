package model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import db.DataBase;
import webserver.HttpRequest;

public class CreateUserPost implements CreateUser {
    @Override
    public User create(HttpRequest httpRequest) throws UnsupportedEncodingException {
        Map<String, String> queryStrings = httpRequest.getHttpRequestBody().getBody();
        String userId = queryStrings.get("userId");
        String password = queryStrings.get("password");
        String name = URLEncoder.encode(queryStrings.get("name"), "UTF-8");
        String email = queryStrings.get("email");

        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
        return user;
    }
}
