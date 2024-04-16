package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utils.HttpRequestBody;
import utils.HttpRequestHeaders;
import utils.HttpRequestMethod;
import utils.HttpRequestPath;
import utils.IOUtils;

public class HttpRequest {
    private final HttpRequestMethod httpRequestMethod;
    private final HttpRequestPath httpRequestPath;
    private final HttpRequestHeaders httpRequestHeaders;
    private final HttpRequestBody httpRequestBody;

    private HttpRequest(HttpRequestMethod httpRequestMethod, HttpRequestPath httpRequestPath, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody) {
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestPath = httpRequestPath;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestBody = httpRequestBody;
    }

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (line == null) {return null;}

        String[] methodPathHtml = line.split(" ");

        HttpRequestMethod httpRequestMethod = HttpRequestMethod.findHttpRequestMethod(methodPathHtml[0]);

        HttpRequestPath httpRequestPath = HttpRequestPath.findHttpRequestPath(methodPathHtml[1]);

        List<String> headers = new ArrayList<>();
        line = bufferedReader.readLine();
        while (!"".equals(line)) {
            headers.add(line);
            line = bufferedReader.readLine();
        }
        HttpRequestHeaders httpRequestHeaders = HttpRequestHeaders.findHttpRequestQueryString(headers);

        HttpRequestBody httpRequestBody = HttpRequestBody.findHttpRequestBody(IOUtils.readData(bufferedReader, httpRequestHeaders.findContentLength()));

        return new HttpRequest(httpRequestMethod, httpRequestPath, httpRequestHeaders, httpRequestBody);
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public HttpRequestPath getHttpRequestPath() {
        return httpRequestPath;
    }
}
