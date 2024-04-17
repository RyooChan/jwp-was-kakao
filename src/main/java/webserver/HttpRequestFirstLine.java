package webserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static webserver.HttpRequest.KEY_INDEX;
import static webserver.HttpRequest.VALUE_INDEX;

public class HttpRequestFirstLine {
    private static final String PATH_PARSER_TOKEN_WITH_ESCAPE = "\\?";
    private static final String PATH_PARSER_TOKEN = "?";
    private static final String QUERY_STRING_PARSER_TOKEN = "&";
    private static final String KEY_VALUE_PARSER_TOKEN = "=";

    private final String path;
    private final HttpRequestQueryString httpRequestQueryString;

    public HttpRequestFirstLine(String path, HttpRequestQueryString httpRequestQueryString) {
        this.path = path;
        this.httpRequestQueryString = httpRequestQueryString;
    }

    public static HttpRequestFirstLine findHttpRequestPath(String pathString) {
        String[] pathComponents = pathString.split(PATH_PARSER_TOKEN_WITH_ESCAPE);

        if (pathString.contains(PATH_PARSER_TOKEN)) {
            String[] querySplit = pathComponents[1].split(QUERY_STRING_PARSER_TOKEN);
            Map<String, String> httpRequestQueryStrings = Arrays.stream(querySplit)
                .map(parameter -> parameter.split(KEY_VALUE_PARSER_TOKEN))
                .collect(Collectors.toMap(
                    keyValue -> keyValue[KEY_INDEX], keyValue -> keyValue[VALUE_INDEX]
                ));
            return new HttpRequestFirstLine(pathComponents[0], new HttpRequestQueryString(httpRequestQueryStrings));
        }

        return new HttpRequestFirstLine(pathComponents[0], new HttpRequestQueryString(new HashMap<>()));
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

    public String findExtension() {
        String[] split = this.path.split("\\.");
        return split[split.length - 1];
    }
}
