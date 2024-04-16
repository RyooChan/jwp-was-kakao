package utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHeaders {
    private static final String HEADER_PARSER_TOKEN = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Map<String, String> headers;

    private HttpRequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeaders findHttpRequestQueryString(List<String> headerInput) {
        Map<String, String> headers = headerInput.stream()
            .map(header -> header.split(HEADER_PARSER_TOKEN))
            .collect(Collectors.toMap(keyValue -> keyValue[KEY_INDEX], KeyValue -> KeyValue[VALUE_INDEX]));

        return new HttpRequestHeaders(headers);
    }

    public int findContentLength() {
        return Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
