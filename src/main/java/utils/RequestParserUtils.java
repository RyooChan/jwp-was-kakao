package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> checkQueryStringFromLine(String input) {
        Map<String, String> queryStrings = new HashMap<>();
        String[] split = input.split(" ");
        String[] pathAndParameter = split[1].split("\\?");
        String[] keyValues = pathAndParameter[1].split("&");
        Arrays.stream(keyValues).forEach(
            string -> {
                String[] keyAndValue = string.split("=");
                queryStrings.put(keyAndValue[0], keyAndValue[1]);
            }
        );
        return queryStrings;
    }
}
