package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.IOUtils;

public class HttpRequest {
    public static final int KEY_INDEX = 0;
    public static final int VALUE_INDEX = 1;

    private final HttpRequestMethod httpRequestMethod;
    private final HttpRequestFirstLine httpRequestFirstLine;
    private final HttpRequestHeaders httpRequestHeaders;
    private final HttpRequestBody httpRequestBody;

    private HttpRequest(HttpRequestMethod httpRequestMethod, HttpRequestFirstLine httpRequestFirstLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody) {
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestFirstLine = httpRequestFirstLine;
        this.httpRequestHeaders = httpRequestHeaders;
        this.httpRequestBody = httpRequestBody;
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

        return new HttpRequest(httpRequestMethod, httpRequestFirstLine, httpRequestHeaders, httpRequestBody);
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

    public boolean isCreateUser() {
        return httpRequestFirstLine.getPath().equals("/user/create");
    }

    public boolean isStatic() {
        return ContentType.isStatic(this.httpRequestFirstLine.findExtension());
    }

    public boolean isTemplates() {
        return ContentType.isTemplates(this.httpRequestFirstLine.findExtension());
    }

//    public boolean isPathEquals(String path) {
//        return this.httpRequestFirstLine.isPathEquals(path);
//    }
}
