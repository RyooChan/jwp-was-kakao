package webserver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.HttpRequest.KEY_INDEX;
import static webserver.HttpRequest.VALUE_INDEX;

public class HttpRequestHeaders {
    private static final String HEADER_PARSER_TOKEN = ": ";

    private final Map<String, String> headers;

    private HttpRequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HttpRequestHeaders ofFromHeaders(List<String> headerInput) {
        Map<String, String> headers = headerInput.stream()
            .map(header -> header.split(HEADER_PARSER_TOKEN))
            .collect(Collectors.toMap(keyValue -> keyValue[KEY_INDEX], KeyValue -> URLDecoder.decode(KeyValue[VALUE_INDEX], StandardCharsets.UTF_8)));

        return new HttpRequestHeaders(headers);
    }

    public int findContentLength() {
        return Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
