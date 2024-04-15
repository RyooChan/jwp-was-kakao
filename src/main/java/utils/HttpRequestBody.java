package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestBody {
    private final Map<String, String> body;

    private HttpRequestBody(Map<String, String> body) {
        this.body = body;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public static HttpRequestBody findHttpRequestBody(String body) {
        if (body.isEmpty()) {
            return new HttpRequestBody(null);
        }

        String[] keyAndValues = body.split("&");

        Map<String, String> bodies = Arrays.stream(keyAndValues)
            .map(header -> header.split("="))
            .collect(Collectors.toMap(keyValue -> keyValue[0], KeyValue -> KeyValue[1]));

        return new HttpRequestBody(bodies);
    }
}
