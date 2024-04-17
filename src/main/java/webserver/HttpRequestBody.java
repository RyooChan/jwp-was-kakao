package webserver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.HttpRequest.KEY_INDEX;
import static webserver.HttpRequest.VALUE_INDEX;

public class HttpRequestBody {
    private static final String BODY_PARSER_TOKEN = "&";
    private static final String KEY_VALUE_PARSER_TOKEN = "=";

    private final Map<String, String> body;

    private HttpRequestBody(Map<String, String> body) {
        this.body = body;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public static HttpRequestBody ofFromBody(String body) {
        if (body.isEmpty()) {
            return new HttpRequestBody(null);
        }

        String[] keyAndValues = body.split(BODY_PARSER_TOKEN);

        Map<String, String> bodies = Arrays.stream(keyAndValues)
            .map(header -> header.split(KEY_VALUE_PARSER_TOKEN))
            .collect(Collectors.toMap(keyValue -> keyValue[KEY_INDEX], KeyValue -> URLDecoder.decode(KeyValue[VALUE_INDEX], StandardCharsets.UTF_8)));

        return new HttpRequestBody(bodies);
    }
}
