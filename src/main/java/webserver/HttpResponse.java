package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import model.User;
import utils.FileIoUtils;

public class HttpResponse {

    private final HttpStatus httpStatus;
    private byte[] body;
    private final Map<String, String> headers;

    private HttpResponse(HttpStatus httpStatus, byte[] body, Map<String, String> headers) {
        this.httpStatus = httpStatus;
        this.body = body;
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


    private static HttpResponse responseStatic200Header(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getHttpRequestPath().getPath());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/" + httpRequest.getHttpRequestPath().findExtension() + ";charset=utf-8");
        headers.put("Content-Length", String.valueOf(body.length));

        return new HttpResponse(HttpStatus.OK, body, headers);
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

        return responseIndex200Header(httpRequest);
    }
}
