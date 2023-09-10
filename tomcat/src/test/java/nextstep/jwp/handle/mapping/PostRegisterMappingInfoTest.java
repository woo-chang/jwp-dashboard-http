package nextstep.jwp.handle.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import org.apache.coyote.request.HttpRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostRegisterMappingInfoTest {

    @Nested
    class 요청_매핑_여부_확인 {

        @Test
        void 요청_매핑이_가능하면_true_반환한다() throws Exception {
            final String httpRequestMessage = String.join("\r\n",
                    "POST /register HTTP/1.1",
                    "Host: localhost:8080",
                    "Connection: keep-alive",
                    "Accept: */*;q=0.1, text/html;q=0.8, application/json;q=0.5"
            );
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final HttpRequest httpRequest = HttpRequest.parse(bufferedReader);

            final PostRegisterMappingInfo postRegisterMappingInfo = new PostRegisterMappingInfo();
            final boolean result = postRegisterMappingInfo.support(httpRequest);

            assertThat(result).isTrue();
        }

        @Test
        void 요청_매핑이_가능하지_않다면_false_반환한다() throws Exception {
            final String httpRequestMessage = String.join("\r\n",
                    "GET /login HTTP/1.1",
                    "Host: localhost:8080",
                    "Connection: keep-alive",
                    "Accept: */*;q=0.1, text/html;q=0.8, application/json;q=0.5"
            );
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final HttpRequest httpRequest = HttpRequest.parse(bufferedReader);

            final PostRegisterMappingInfo postRegisterMappingInfo = new PostRegisterMappingInfo();
            final boolean result = postRegisterMappingInfo.support(httpRequest);

            assertThat(result).isFalse();
        }
    }
}
