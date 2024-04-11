package utils;

public class HttpRequestPath {
    private final String path;

    private HttpRequestPath(String path) {
        this.path = path;
    }

    public static HttpRequestPath findHttpRequestPath(String pathString) {
        String[] split = pathString.split("\\?");
        return new HttpRequestPath(split[0]);
    }

    public String getPath() {
        return path;
    }
}
