package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import utils.FileIoUtils;

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            HttpRequest httpRequest = HttpRequest.of(bufferedReader);

            DataOutputStream dos = new DataOutputStream(out);
            HttpResponse httpResponse = HttpResponse.responseHeader(httpRequest);
            responseHeader(dos, httpResponse);
            responseBody(dos, httpResponse.getBody());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpResponse.getHttpStatus() + " OK \n");
        httpResponse.getHeaders()
            .forEach((key, value) -> {
                try {
                    dos.writeBytes(key + ": " + value + "\r\n");
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            });
        dos.writeBytes("\r\n");
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
