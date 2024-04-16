package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpRequestPath {
    private static final String PATH_PARSER_TOKEN_WITH_ESCAPE = "\\?";
    private static final String PATH_PARSER_TOKEN = "?";
    private static final String QUERY_STRING_PARSER_TOKEN = "&";
    private static final String KEY_VALUE_PARSER_TOKEN = "=";

    private final String path;
    private final HttpRequestQueryString httpRequestQueryString;

    public HttpRequestPath(String path, HttpRequestQueryString httpRequestQueryString) {
        this.path = path;
        this.httpRequestQueryString = httpRequestQueryString;
    }

    public static HttpRequestPath findHttpRequestPath(String pathString) {
        Map<String, String> httpRequestQueryStrings
            = new HashMap<>();

        String[] split = pathString.split(PATH_PARSER_TOKEN_WITH_ESCAPE);

        if (pathString.contains(PATH_PARSER_TOKEN)) {
            String[] querySplit = split[1].split(QUERY_STRING_PARSER_TOKEN);

            httpRequestQueryStrings = Arrays.stream(querySplit)
                .map(parameter -> parameter.split(KEY_VALUE_PARSER_TOKEN))
                .collect(Collectors.toMap(
                    keyValue -> keyValue[0], keyValue -> keyValue[1]
                ));
        }

        return new HttpRequestPath(split[0], new HttpRequestQueryString(httpRequestQueryStrings));
    }

    public String getPath() {
        return path;
    }

    public HttpRequestQueryString getHttpRequestQueryString() {
        return httpRequestQueryString;
    }

    public boolean isPathEquals(String path) {
        return Objects.equals(this.path, path);
    }

    public boolean isEndsWith(String path) {
        return this.path.endsWith(path);
    }
}
