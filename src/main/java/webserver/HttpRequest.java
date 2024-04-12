package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.HttpRequestHeader;
import utils.HttpRequestMethod;
import utils.HttpRequestPath;
import utils.HttpRequestQueryString;
import utils.RequestParserUtils;

public class HttpRequest {
    private final HttpRequestMethod httpRequestMethod;
    private final HttpRequestPath httpRequestPath;
    private final HttpRequestQueryString httpRequestQueryString;
    private final HttpRequestHeader httpRequestHeader;

    private HttpRequest(HttpRequestMethod httpRequestMethod, HttpRequestPath httpRequestPath, HttpRequestQueryString httpRequestQueryString, HttpRequestHeader httpRequestHeader) {
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestPath = httpRequestPath;
        this.httpRequestQueryString = httpRequestQueryString;
        this.httpRequestHeader = httpRequestHeader;
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public HttpRequestPath getHttpRequestPath() {
        return httpRequestPath;
    }

    public HttpRequestQueryString getHttpRequestQueryString() {
        return httpRequestQueryString;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }

    public static HttpRequest ofFirstLine(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        if (line == null) { return null;}

        String[] methodPathHtml = line.split(" ");

        HttpRequestMethod httpRequestMethod = RequestParserUtils.checkMethodFromLine(methodPathHtml[0]);
        HttpRequestPath httpRequestPath = RequestParserUtils.checkPathFromLine(methodPathHtml[1]);
        HttpRequestQueryString httpRequestQueryString = RequestParserUtils.checkQueryStringFromLine(methodPathHtml[1]);

        List<String> headers = new ArrayList<>();
        line = bufferedReader.readLine();
        while (!"".equals(line)) {
            headers.add(line);
            line = bufferedReader.readLine();
        }
        HttpRequestHeader httpRequestHeader = RequestParserUtils.checkHeader(headers);

        return new HttpRequest(httpRequestMethod, httpRequestPath, httpRequestQueryString, httpRequestHeader);
    }
}
