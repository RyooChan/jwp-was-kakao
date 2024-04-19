package login;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookie {
    private static final String COOKIE_PARSER_TOKEN = ";";
    private static final String COOKIE_KEY_VALUE_APPEND_TOKEN = "=";

    private final Map<String, String> cookies;

    public HttpCookie() {
        this.cookies = new HashMap<>();
    }

    public HttpCookie(String cookieString) {
        if (cookieString == null || cookieString.isBlank()) {
            this.cookies = Collections.emptyMap();
            return;
        }

        this.cookies = Arrays.stream(cookieString.split(COOKIE_PARSER_TOKEN))
            .map(String::trim)
            .map(cookie -> cookie.split(COOKIE_KEY_VALUE_APPEND_TOKEN, 2))
            .collect(Collectors.toMap(token -> token[0], token -> token[1]));
    }

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public List<String> getCookies() {
        return cookies.entrySet().stream()
            .map(entry -> entry.getKey() + COOKIE_KEY_VALUE_APPEND_TOKEN + entry.getValue())
            .collect(Collectors.toList());
    }
}
