package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import utils.HttpRequestMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpRequestMethodTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST"})
    void 정확한_입력_테스트(String method) {
        HttpRequestMethod httpRequestMethod = HttpRequestMethod.findHttpRequestMethod(method);
        assertThat(method).isEqualTo(httpRequestMethod.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"PUT", "DELETE"})
    void 잘못된_입력_테스트(String method) {
        assertThatThrownBy(() -> HttpRequestMethod.findHttpRequestMethod(method))
            .isInstanceOf(IllegalArgumentException.class);
    }
}