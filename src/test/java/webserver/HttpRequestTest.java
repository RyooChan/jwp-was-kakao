package webserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void get_index_test() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final String expected = new String(FileIoUtils.loadFileFromClasspath("./templates/index.html"));

        assertThat(restTemplate.getForEntity("http://localhost:8080/index.html", String.class).getBody())
            .isEqualTo(expected);
    }
}
