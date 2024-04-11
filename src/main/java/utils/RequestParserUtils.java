package utils;

public class RequestParserUtils {
    public static HttpRequestMethod checkMethodFromLine(String input) {
        String[] split = input.split(" ");
        return HttpRequestMethod.findHttpRequestMethod(split[0]);
    }
}
