package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserUtilsTest {

    @Test
    void 한_줄을_입력받아_method_를_구한다() {
        String input = "GET /index.html HTTP/1.1";
        HttpRequestMethod httpRequestMethod = RequestParserUtils.checkMethodFromLine(input);

        assertThat(httpRequestMethod).isEqualTo(HttpRequestMethod.GET);
    }

    @Test
    void 한_줄을_입력받아_path_를_구한다() {
        String input = "GET /index.html HTTP/1.1";
        String path = RequestParserUtils.checkPathFromLine(input);

        assertThat(path).isEqualTo("/index.html");
    }

    @Test
    void 한_줄을_입력받아_path_를_구한다_2() {
        String input = "GET /user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com HTTP/1.1";
        String path = RequestParserUtils.checkPathFromLine(input);

        assertThat(path).isEqualTo("/user/create");
    }

    @Test
    void 한_줄을_입력받아_query_string_을_구한다() {
        String input = "GET /user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com HTTP/1.1";
        Map<String, String> stringStringMap = RequestParserUtils.checkQueryStringFromLine(input);

        assertThat(stringStringMap.get("userId")).isEqualTo("cu");
    }

    @Test
    void 여러_줄을_입력받아_header_map_을_구한다() throws IOException {
        String input = "Host: localhost:8080\n" +
                        "Accept: text/css,*/*;q=0.1\n" +
                        "Connection: keep-alive";
        BufferedReader reader = new BufferedReader(new StringReader(input));

        Map<String, String> stringStringMap = RequestParserUtils.checkHeader(reader);
        assertThat(stringStringMap.get("Host")).isEqualTo("localhost:8080");
    }


}