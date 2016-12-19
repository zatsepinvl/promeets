package com.promeets.model.service.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.plus.*;
import com.google.api.services.plus.model.Person;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Vladimir on 16.03.2016.
 */
@Component
public class GoogleService {
    private static final Logger log = Logger.getLogger(GoogleService.class);

    //@Value(value = "#{appProperties.googleOAuth2Info}")
    private static final String clientSecretFilePath = "client_secret.json";

    private static final String APPLICATION_NAME = "Promeets";

    private static final String SCOPES =
            PlusScopes.USERINFO_EMAIL
                    + "%20" + PlusScopes.USERINFO_PROFILE
                    + "%20" + CalendarScopes.CALENDAR;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

    private OAuthInfo oAuthInfo;

    public GoogleService() {
        loadOAuthInfo();
    }

    public GoogleTokenResponse exchangeCodeToToken(String code) throws IOException {
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();
        return new GoogleAuthorizationCodeTokenRequest(
                httpTransport,
                jsonFactory,
                oAuthInfo.getClient_id(),
                oAuthInfo.getClient_secret(),
                code,
                oAuthInfo.getRedirect_uris()[0]
        ).execute();
    }

    public String getOauth2AuthenticationUrl() {
        return oAuthInfo.getAuth_uri()
                + "?"
                + "scope=" + SCOPES
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
        Plus plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        Calendar.CalendarList calendarList = calendar.calendarList();
        Events events = calendar.events().list("primary").setTimeMin(new DateTime(System.nanoTime())).execute();
        List<Event> eventList = events.getItems();
        return plus.people().get("me").execute();
    }
}


