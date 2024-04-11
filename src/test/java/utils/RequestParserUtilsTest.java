package utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestParserUtilsTest {

    @Test
    void 한_줄을_입력받아_method_를_구한다() {
        String input = "GET /index.html HTTP/1.1\n";
        HttpRequestMethod httpRequestMethod = RequestParserUtils.checkMethodFromLine(input);

        assertThat(httpRequestMethod).isEqualTo(HttpRequestMethod.GET);
    }


}