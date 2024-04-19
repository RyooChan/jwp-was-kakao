package webserver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.github.jknack.handlebars.Handlebars;

import db.DataBase;
import login.HttpCookie;
import login.Session;
import login.SessionManager;
import model.User;
import utils.FileIoUtils;

public class HttpResponse {

    private HttpStatus httpStatus;
    private byte[] body;
    private final HttpCookie httpCookie;
    private final Map<String, String> headers;

    private HttpResponse(HttpStatus httpStatus, byte[] body, Map<String, String> headers) {
        this.httpStatus = httpStatus;
        this.body = body;
        this.httpCookie = new HttpCookie();
        this.headers = headers;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }


    private static User createUserByMethod(HttpRequest httpRequest) {

        if (httpRequest.getHttpRequestMethod().equals(HttpRequestMethod.GET)) {
            return User.createUserByParameter(httpRequest.getHttpRequestPath().getHttpRequestQueryString().getQueryStrings());
        }

        return User.createUserByParameter(httpRequest.getHttpRequestBody().getBody());
    }

    private static HttpResponse createHeaderWithRedirectUrl(HttpRequest httpRequest) throws IOException, URISyntaxException {
        User user = createUserByMethod(httpRequest);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));
        headers.put("location", "/index.html");

        return new HttpResponse(HttpStatus.FOUND, body, headers);
    }

    private static HttpResponse responseIndex200Header(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getHttpRequestPath().getPath());

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));

        return new HttpResponse(HttpStatus.OK, body, headers);
    }

    private static HttpResponse responseUserListHeader(HttpRequest httpRequest) throws IOException, URISyntaxException {
        User user;
        byte[] body;
        Map<String, String> headers = new HashMap<>();

        Session session = SessionManager.getSession(httpRequest.getHttpCookie().getCookie("JSESSIONID"));

        if (session == null) {;
            body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
            headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=utf-8");
            headers.put("location", "/user/login.html");
            return new HttpResponse(HttpStatus.FOUND, body, headers);
        }

        Collection<User> users = DataBase.findAll();
        Handlebars handlebars = new Handlebars();
        body = handlebars.compile(httpRequest.getHttpRequestPath().getPath())
            .apply(users)
            .getBytes();
        return new HttpResponse(HttpStatus.OK, body, headers);
    }

    private static HttpResponse responseStatic200Header(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getHttpRequestPath().getPath());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/" + httpRequest.getHttpRequestPath().findExtension() + ";charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));

        return new HttpResponse(HttpStatus.OK, body, headers);
    }

    private static HttpResponse responseLoginUserHeader(HttpRequest httpRequest) throws IOException, URISyntaxException {
        User user;
        byte[] body;
        Map<String, String> headers = new HashMap<>();

        try {
            user = User.login(httpRequest);
            body = FileIoUtils.loadFileFromClasspath("./templates/index.html");;

            Session session = SessionManager.createSession();
            session.setAttribute("user", user);

            headers = new HashMap<>();
            headers.put("Content-Type", "application/json;charset=utf-8");
            headers.put("Content-Length", String.valueOf(body.length));
            headers.put("Set-Cookie", session.getId() + ";Path=/");
            headers.put("location", "/index.html");

            HttpResponse httpResponse = new HttpResponse(HttpStatus.FOUND, body, headers);
            httpResponse.addCookie("JSESSIONID", session.getId());

            return httpResponse;

        } catch (IllegalArgumentException e) {
            body = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");

            headers = new HashMap<>();
            headers.put("Content-Type", "text/html;charset=utf-8");
            headers.put("Content-Length", String.valueOf(body.length));

            return new HttpResponse(HttpStatus.OK, body, headers);
        }
    }

    public static HttpResponse responseHeader(HttpRequest httpRequest) throws IOException, URISyntaxException {

        if (httpRequest.isTemplates()) {
            return responseIndex200Header(httpRequest);
        }

        if (httpRequest.isStatic()) {
            return responseStatic200Header(httpRequest);
        }

        if (httpRequest.getHttpRequestPath().getPath().equals("/user/create")) {
            return createHeaderWithRedirectUrl(httpRequest);
        }

        if (httpRequest.getHttpRequestPath().getPath().equals("/user/login")) {
            return responseLoginUserHeader(httpRequest);
        }

        if (httpRequest.getHttpRequestPath().getPath().equals("/user/list")) {
            return responseUserListHeader(httpRequest);
        }

        return responseIndex200Header(httpRequest);
    }


    public void addCookie(String name, String value) {
        httpCookie.addCookie(name, value);
    }

    public List<String> getCookies() {
        return httpCookie.getCookies();
    }

}
