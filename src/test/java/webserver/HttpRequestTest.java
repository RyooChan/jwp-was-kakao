package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

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
    void get_index_without_querystring() throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String expected = new String(FileIoUtils.loadFileFromClasspath("./templates/index.html"));

        ResponseEntity<String> actual = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(actual.getBody()).isEqualTo(expected);
    }
}
