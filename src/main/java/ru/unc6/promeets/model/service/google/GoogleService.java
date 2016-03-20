package ru.unc6.promeets.model.service.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.*;
import com.google.api.services.plus.model.Person;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vladimir on 16.03.2016.
 */
@Component
public class GoogleService {
    private static final Logger log = Logger.getLogger(GoogleService.class);

    //@Value(value = "#{appProperties.googleOAuth2Info}")
    private String clientSecretFilePath = "client_secret.json";

    private final String applicationName = "Promeets";

    private OAuthInfo oAuthInfo;

    public GoogleService() {
        loadOAuthInfo();
    }

    public GoogleTokenResponse exchangeCodeToToken(String code) throws IOException {
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                oAuthInfo.getClient_id(),
                oAuthInfo.getClient_secret(),
                code,
                oAuthInfo.getRedirect_uris()[0]
        ).execute();
        return tokenResponse;
    }

    public String getOauth2AuthenticationUrl() {
        return oAuthInfo.getAuth_uri()
                + "?"
                + "scope=email%20profile"
                + "&redirect_uri=" + oAuthInfo.getRedirect_uris()[0]
                + "&response_type=code"
                + "&client_id=" + oAuthInfo.getClient_id();
    }

    private void loadOAuthInfo() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            ObjectMapper objectMapper = new ObjectMapper();
            oAuthInfo = objectMapper.readValue(new File(classLoader.getResource(clientSecretFilePath).getFile()), OAuthInfo.class);
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Person getPerson(GoogleTokenResponse googleTokenResponse) throws IOException {
        GoogleCredential credential = new GoogleCredential().setAccessToken(googleTokenResponse.getAccessToken());
        Plus plus = new Plus.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(applicationName)
                .build();
        return plus.people().get("me").execute();
    }
}


