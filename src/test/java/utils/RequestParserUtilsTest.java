package utils;

import org.junit.jupiter.api.Test;

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



}