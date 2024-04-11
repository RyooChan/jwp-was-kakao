package utils;

public class RequestParserUtils {
    public static HttpRequestMethod checkMethodFromLine(String input) {
        String[] split = input.split(" ");
        return HttpRequestMethod.findHttpRequestMethod(split[0]);
    }

    public static String checkPathFromLine(String input) {
        String[] split = input.split(" ");
        String[] pathAndParameter = split[1].split("\\?");
        return pathAndParameter[0];
    }
}
