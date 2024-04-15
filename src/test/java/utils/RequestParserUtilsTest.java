package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParserUtilsTest {

    @Test
    void 한_줄을_입력받아_method_를_구한다() {
        String input = "GET";
        HttpRequestMethod httpRequestMethod = RequestParserUtils.checkMethodFromLine(input);

        assertThat(httpRequestMethod).isEqualTo(HttpRequestMethod.GET);
    }

    @Test
    void 한_줄을_입력받아_path_를_구한다() {
        String input = "/index.html";
        HttpRequestPath httpRequestPath = RequestParserUtils.checkPathFromLine(input);

        assertThat(httpRequestPath.getPath()).isEqualTo("/index.html");
    }

    @Test
    void 한_줄을_입력받아_path_를_구한다_2() {
        String input = "/user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com";
        HttpRequestPath httpRequestPath = RequestParserUtils.checkPathFromLine(input);

        assertThat(httpRequestPath.getPath()).isEqualTo("/user/create");
    }

    @Test
    void 한_줄을_입력받아_query_string_을_구한다() {
        String input = "/user/create?userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com";
        HttpRequestQueryString httpRequestQueryString = RequestParserUtils.checkQueryStringFromLine(input);

        assertThat(httpRequestQueryString.getQueryStrings().get("userId")).isEqualTo("cu");
    }

    @Test
    void 여러_줄을_입력받아_header_map_을_구한다() {
        List<String> input = new ArrayList<>();
        input.add("Host: localhost:8080");
        input.add("Accept: text/css,*/*;q=0.1");
        input.add("Connection: keep-alive");

        HttpRequestHeader httpRequestHeader = RequestParserUtils.checkHeader(input);
        assertThat(httpRequestHeader.getHeaders().get("Host")).isEqualTo("localhost:8080");
    }

    @Test
    void 버퍼드리더를_통해_body_를_구한다() throws IOException {
        String input = "userId=cu&password=password&name=%EC%9D%B4%EB%8F%99%EA%B7%9C&email=brainbackdoor%40gmail.com";
        int length = input.length();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

        HttpRequestBody httpRequestBody = RequestParserUtils.checkBody(IOUtils.readData(bufferedReader, length));
        assertThat(httpRequestBody.getBody().get("userId")).isEqualTo("cu");
    }


}