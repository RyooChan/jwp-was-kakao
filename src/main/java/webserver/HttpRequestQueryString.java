package webserver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestQueryString {
    private final Map<String, String> queryStrings;

    public HttpRequestQueryString(Map<String, String> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public Map<String, String> getQueryStrings() {
        return queryStrings;
    }
}
