package utils;

public class HttpRequestBody {
    private final String body;

    private HttpRequestBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    // TODO : 아예 처음부터 IOUtils 를 여기서 사용할까, 혹은 생성자를 바로 노출할까 했는데 일단 통일성을 위해 static method 사용
    public static HttpRequestBody findHttpRequestBody(String body) {
        return new HttpRequestBody(body);
    }
}
