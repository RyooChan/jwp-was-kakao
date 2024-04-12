package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestQueryString {
    private final Map<String, String> queryStrings;

    private HttpRequestQueryString(Map<String, String> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }

    public static HttpRequestQueryString findHttpRequestQueryString(String pathString) {
        String[] PathAndqueryString = pathString.split("\\?");
        String[] querySplit = PathAndqueryString[1].split("&");

        Map<String, String> httpRequestQueryStrings = Arrays.stream(querySplit)
            .map(parameter -> parameter.split("="))
            .collect(Collectors.toMap(
                keyValue -> keyValue[0], keyValue -> keyValue[1]
            ));

        return new HttpRequestQueryString(httpRequestQueryStrings);
    }
}
