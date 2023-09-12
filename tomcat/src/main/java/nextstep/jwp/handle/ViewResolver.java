package nextstep.jwp.handle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import org.apache.coyote.common.ContentType;
import org.apache.coyote.common.HttpStatus;
import org.apache.coyote.response.HttpResponse;

public class ViewResolver {

    private static final String STATIC_DIRECTORY = "static";
    private static final String SLASH = File.separator;
    private static final String NOT_FOUND = "404.html";

    private ViewResolver() {
    }

    public static void renderPage(final HttpResponse response, final HttpStatus httpStatus, final String page)
            throws IOException {
        try {
            response.setStatus(httpStatus);
            response.setContentType(getContentType(page));
            response.setContent(getBody(page));
        } catch (NullPointerException e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(ContentType.TEXT_HTML.getType());
            response.setContent(getBody(NOT_FOUND));
        }
    }

    private static String getContentType(final String page) {
        final String extension = page.substring(page.lastIndexOf(".") + 1);
        String contentType = ContentType.getTypeFrom(extension);
        return contentType == null ? ContentType.APPLICATION_OCTET_STREAM.getType() : contentType;
    }

    private static String getBody(final String page) throws IOException {
        final URL resource = ClassLoader.getSystemClassLoader().getResource(STATIC_DIRECTORY + SLASH + page);
        final File file = new File(resource.getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
