package utils;

public enum HttpRequestMethod {
    GET,
    POST
    ;

    public static HttpRequestMethod findHttpRequestMethod(String method) {
        return HttpRequestMethod.valueOf(method);
    }

}
