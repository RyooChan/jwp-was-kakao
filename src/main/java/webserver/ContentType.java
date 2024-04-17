package webserver;

import java.util.Arrays;
import java.util.Objects;

public enum ContentType {
    CSS("css", "static"),
    EOT("eot", "static"),
    SVG("svg", "static"),
    TTF("ttf", "static"),
    WOFF("woff", "static"),
    WOFF2("woff2", "static"),
    PNG("png", "static"),
    JS("js", "static"),
    HTML("html", "templates"),
    ICO("ico", "templates"),
    ;

    ContentType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    private final String extension;
    private final String contentType;

    public static boolean isStatic(String extension) {
        for (ContentType type : ContentType.values()) {
            if (type.extension.equals(extension)) {
                return type.contentType.equals("static");
            }
        }
        return false;
    }

    public static boolean isTemplates(String extension) {
        for (ContentType type : ContentType.values()) {
            if (type.extension.equals(extension)) {
                return type.contentType.equals("templates");
            }
        }
        return false;
    }
}
