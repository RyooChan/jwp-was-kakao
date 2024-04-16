package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import utils.FileIoUtils;
import utils.HttpRequestMethod;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = HttpRequest.of(bufferedReader);

            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = getBodyFromRequest(httpRequest);

            response200Header(dos, body.length, httpRequest.getHttpRequestPath().getPath());
            responseBody(dos, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private static byte[] getBodyFromRequest(HttpRequest httpRequest) throws IOException, URISyntaxException {

        if (httpRequest.getHttpRequestPath().isEndsWith(".html")) {
            return FileIoUtils.loadFileFromClasspath("./templates" + httpRequest.getHttpRequestPath().getPath());
        }

        if (httpRequest.getHttpRequestPath().isEndsWith(".css")) {
            return FileIoUtils.loadFileFromClasspath("./static" + httpRequest.getHttpRequestPath().getPath());
        }

        if (httpRequest.getHttpRequestPath().isPathEquals("/user/create")) {
            User user = createUserByMethod(httpRequest);
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        }

        return "Hello World".getBytes();
    }

    private static User createUserByMethod(HttpRequest httpRequest) throws UnsupportedEncodingException {

        if (httpRequest.getHttpRequestMethod().equals(HttpRequestMethod.GET)) {
            return User.createUserByParameter(httpRequest.getHttpRequestPath().getHttpRequestQueryString().getQueryStrings());
        }

        return User.createUserByParameter(httpRequest.getHttpRequestBody().getBody());
    }

    private void createHeaderWithRedirectUrl(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \n");
            dos.writeBytes("Content-Type: application/json;charset=utf-8\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("location: /index.html" + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseIndex200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void responseStatic200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String path) {
        if (path.endsWith(".html")) {
            responseIndex200Header(dos, lengthOfBodyContent);
            return;
        }

        if (path.contains(".")) {
            String[] split = path.split("\\.");
            responseStatic200Header(dos, lengthOfBodyContent, split[split.length-1]);
            return;
        }

        if (path.equals("/user/create")) {
            createHeaderWithRedirectUrl(dos, lengthOfBodyContent);
            return;
        }

        responseIndex200Header(dos, lengthOfBodyContent);
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
