package utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestHeader {
    private final Map<String, String> headers;

    private HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeader findHttpRequestQueryString(List<String> headerInput) {
        Map<String, String> headers = headerInput.stream()
            .map(header -> header.split(": "))
            .collect(Collectors.toMap(keyValue -> keyValue[0], KeyValue -> KeyValue[1]));

        return new HttpRequestHeader(headers);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
