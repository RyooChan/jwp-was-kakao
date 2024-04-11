package utils;

import java.util.List;

public class RequestParserUtils {
    public static HttpRequestMethod checkMethodFromLine(String input) {
        return HttpRequestMethod.findHttpRequestMethod(input);
    }

    public static HttpRequestPath checkPathFromLine(String input) {
        return HttpRequestPath.findHttpRequestPath(input);
    }

    public static HttpRequestQueryString checkQueryStringFromLine(String input) {
        return HttpRequestQueryString.findHttpRequestQueryString(input);
    }

    public static HttpRequestHeader checkHeader(List<String> headers) {
        return HttpRequestHeader.findHttpRequestQueryString(headers);
    }

    public static HttpRequestBody checkBody(String body) {
        return HttpRequestBody.findHttpRequestBody(body);
    }
}
