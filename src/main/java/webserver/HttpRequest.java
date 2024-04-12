package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import utils.HttpRequestMethod;
import utils.HttpRequestPath;
import utils.HttpRequestQueryString;
import utils.RequestParserUtils;

public class HttpRequest {
    private final HttpRequestMethod httpRequestMethod;
    private final HttpRequestPath httpRequestPath;
    private final HttpRequestQueryString httpRequestQueryString;

    private HttpRequest(HttpRequestMethod httpRequestMethod, HttpRequestPath httpRequestPath, HttpRequestQueryString httpRequestQueryString) {
        this.httpRequestMethod = httpRequestMethod;
        this.httpRequestPath = httpRequestPath;
        this.httpRequestQueryString = httpRequestQueryString;
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

    public static HttpRequest of(String firstLine) throws IOException {
        String[] methodPathHtml = firstLine.split(" ");
        Arrays.stream(methodPathHtml).forEach(System.out::println);
        HttpRequestMethod httpRequestMethod = RequestParserUtils.checkMethodFromLine(methodPathHtml[0]);
        HttpRequestPath httpRequestPath = RequestParserUtils.checkPathFromLine(methodPathHtml[1]);
        HttpRequestQueryString httpRequestQueryString = RequestParserUtils.checkQueryStringFromLine(methodPathHtml[1]);
        return new HttpRequest(httpRequestMethod, httpRequestPath, httpRequestQueryString);
    }
}
