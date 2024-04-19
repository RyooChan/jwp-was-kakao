package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import login.HttpCookie;
import utils.IOUtils;

import static webserver.HttpRequestMethod.GET;
import static webserver.HttpRequestMethod.POST;

public class HttpRequest {
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    private final HttpRequestMethod httpRequestMethod;
    private final HttpRequestFirstLine httpRequestFirstLine;
    private final HttpRequestHeaders httpRequestHeaders;
    private final HttpRequestBody httpRequestBody;
    private final HttpCookie httpCookie;

    private HttpRequest(HttpRequestMethod httpRequestMethod, HttpRequestFirstLine httpRequestFirstLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody, HttpCookie httpCookie) {
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestFirstLine = httpRequestFirstLine;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestBody = httpRequestBody;
        this.httpCookie = httpCookie;
    }

    public static HttpRequest of(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (line == null) {
            return null;
        }

        String[] methodPathHtml = line.split(" ");

        HttpRequestMethod httpRequestMethod = HttpRequestMethod.findHttpRequestMethod(methodPathHtml[0]);

        HttpRequestFirstLine httpRequestFirstLine = HttpRequestFirstLine.findHttpRequestPath(methodPathHtml[1]);

        List<String> headers = new ArrayList<>();
        line = bufferedReader.readLine();
        while (!"".equals(line)) {
            headers.add(line);
            line = bufferedReader.readLine();
        }
        HttpRequestHeaders httpRequestHeaders = HttpRequestHeaders.ofFromHeaders(headers);

        HttpRequestBody httpRequestBody = HttpRequestBody.ofFromBody(IOUtils.readData(bufferedReader, httpRequestHeaders.findContentLength()));

        Map<String, String> parameter = getParameter(httpRequestMethod, httpRequestBody, httpRequestHeaders);
        HttpCookie httpCookie = new HttpCookie(parameter.get("Cookie"));

        return new HttpRequest(httpRequestMethod, httpRequestFirstLine, httpRequestHeaders, httpRequestBody, httpCookie);
    }

    private static Map<String, String> getParameter(HttpRequestMethod httpRequestMethod, HttpRequestBody httpRequestBody, HttpRequestHeaders httpRequestHeaders) {
        Map<String, String> parameter = new HashMap<>();

        if (httpRequestMethod == POST) {
            parameter = httpRequestBody.getBody();
        }

        if (httpRequestMethod == GET) {
            parameter = httpRequestHeaders.getHeaders();
        }
        return parameter;
    }

    public HttpRequestBody getHttpRequestBody() {
        return httpRequestBody;
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public HttpRequestFirstLine getHttpRequestPath() {
        return httpRequestFirstLine;
    }

    public HttpCookie getHttpCookie() {
        return httpCookie;
    }

    public boolean isStatic() {
        return ContentType.isStatic(this.httpRequestFirstLine.findExtension());
    }

    public boolean isTemplates() {
        return ContentType.isTemplates(this.httpRequestFirstLine.findExtension());
    }
}
