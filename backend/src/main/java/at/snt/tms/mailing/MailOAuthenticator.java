package at.snt.tms.mailing;

import at.snt.tms.model.OAuthResponseDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.component.mail.MailAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.PasswordAuthentication;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

/**
 * Class {@code MailOAuthenticator.java}
 * <p>
 * Mail OAuth class based on other internal project.
 *
 * @author Dominik Fluch
 */
@Component
public class MailOAuthenticator extends MailAuthenticator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailOAuthenticator.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${tms.mail.oauth2.user}")
    private String username;
    @Value("${tms.mail.oauth2.password}")
    private String password;
    @Value("${tms.mail.oauth2.tenant}")
    private String tenant;
    @Value("${tms.mail.oauth2.clientId}")
    private String clientId;
    @Value("${tms.mail.oauth2.scope}")
    private String scope;

    private String token;
    private long tokenExpired;

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        if (this.token == null || (tokenExpired - System.currentTimeMillis()) <= 120){
            LOGGER.info("Updating OAuth2 token...");
            this.updateBearer();
        } else {
            LOGGER.info("Old OAuth2 token still valid, keeping it");
        }

        return new PasswordAuthentication(username, token);
    }

    private void updateBearer() {
        try {
            final HttpClient http = HttpClient.newHttpClient();

            final StringJoiner params = new StringJoiner("&");

            params.add("client_id=" + clientId)
                .add("scope=" + scope)
                .add("username=" + username)
                .add("password=" + password)
                .add("grant_type=password");

            final byte[] encodedParams = params.toString().getBytes(StandardCharsets.UTF_8);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://login.microsoftonline.com/" + tenant + "/oauth2/v2.0/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Content-Encoding", "utf-8")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(encodedParams))
                    .build();

            final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.value()) {
                final OAuthResponseDetails details = OBJECT_MAPPER.readValue(response.body(), OAuthResponseDetails.class);

                this.token = details.getAccessToken();
                this.tokenExpired = System.currentTimeMillis() + details.getExpiresIn();

                LOGGER.info("new OAuth2 token set");
            } else {
                throw new IllegalStateException("Request failed (at HttpService) with response code " + response.statusCode());
            }
        }catch (IOException | InterruptedException | IllegalStateException e) {
            LOGGER.error("Error retrieving token for OAuth2", e);
        }
    }

}
