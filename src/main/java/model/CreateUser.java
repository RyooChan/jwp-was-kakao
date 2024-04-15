package model;

import java.io.UnsupportedEncodingException;

import webserver.HttpRequest;

@FunctionalInterface
public interface CreateUser {
    User create(HttpRequest httpRequest) throws UnsupportedEncodingException;
}
