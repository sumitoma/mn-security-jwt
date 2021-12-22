package mn.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;


@MicronautTest
public class ServiceTest {

    @Inject
    @Client("/jwtauthservice")
    HttpClient httpClient;

    @Test
    void testUnauthorizedAccess(){

        Assertions.assertThrows(HttpClientResponseException.class, ()->{
            httpClient.toBlocking().exchange(HttpRequest.GET("/app"));
        });
    }

    @Test
    void testLoginAccess() {
        HttpResponse<BearerAccessRefreshToken> loginResponse = httpClient.toBlocking().exchange(HttpRequest.POST("/login", new UsernamePasswordCredentials(
                "sumit", "tomar"
        )).accept(MediaType.APPLICATION_JSON_TYPE), BearerAccessRefreshToken.class);

        Assertions.assertNotNull(loginResponse);
        Assertions.assertEquals(HttpStatus.OK, loginResponse.getStatus());

        BearerAccessRefreshToken bearerAccessRefreshToken = loginResponse.body();

        Assertions.assertNotNull(bearerAccessRefreshToken);
        Assertions.assertEquals("sumit", bearerAccessRefreshToken.getUsername());
        JWT token = null;
        try {
            token = JWTParser.parse(bearerAccessRefreshToken.getAccessToken());
        } catch (ParseException e) {
            Assertions.fail("Failed to parse JWT Token");
        }

        Assertions.assertTrue(token instanceof SignedJWT);



    }
}
