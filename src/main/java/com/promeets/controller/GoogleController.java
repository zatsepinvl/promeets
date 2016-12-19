package com.promeets.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.services.plus.model.Person;
import com.promeets.model.service.google.GoogleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vladimir on 15.03.2016.
 */
@Controller
public class GoogleController {

    private static final String SIGN_UP_PATH = "/signup";
    private static final String GOOGLE_SIGN_UP_REDIRECT_PATH = "/g/oauth2";
    private static final String GOOGLE_OAUTH2_CALLBACK_PATH = "/g/oauth2/code";
    private static final String ACCESS_TOKEN = "g_access_token";
    private static final Logger log = Logger.getLogger(GoogleController.class);

    private static final String PROVIDER = "provider";
    private static final String GOOGLE = "google";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String IMG = "img";
    private static final String ORIGINAL_IMG = "oimg";

    @Autowired
    private GoogleService googleService;

    @RequestMapping(value = GOOGLE_SIGN_UP_REDIRECT_PATH, method = RequestMethod.GET)
    public RedirectView makeCodeRequest() {
        return new RedirectView(googleService.getOauth2AuthenticationUrl());
    }

    @RequestMapping(value = GOOGLE_OAUTH2_CALLBACK_PATH, method = RequestMethod.GET)
    public RedirectView handleCodeResponse(HttpServletResponse response,
                                           @RequestParam(value = "code", required = false) String code,
                                           @RequestParam(value = "error", required = false) String error)
            throws IOException {
        if (error == null) {
            GoogleTokenResponse googleTokenResponse = googleService.exchangeCodeToToken(code);

            Cookie accessTokenCookie = new Cookie(ACCESS_TOKEN, googleTokenResponse.getAccessToken());
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(Math.toIntExact(googleTokenResponse.getExpiresInSeconds()));
            response.addCookie(accessTokenCookie);

            Person person = googleService.getPerson(googleTokenResponse);
            RedirectView redirectView = new RedirectView(SIGN_UP_PATH);
            redirectView.addStaticAttribute(PROVIDER, GOOGLE);
            redirectView.addStaticAttribute(FIRST_NAME, person.getName().getGivenName());
            redirectView.addStaticAttribute(LAST_NAME, person.getName().getFamilyName());
            redirectView.addStaticAttribute(EMAIL, person.getEmails().get(0).getValue());
            String imageUrl = person.getImage().getUrl();
            redirectView.addStaticAttribute(IMG, imageUrl);
            redirectView.addStaticAttribute(ORIGINAL_IMG, imageUrl.substring(0, imageUrl.indexOf('?')));
            return redirectView;
        } else {
            log.error(error);
        }
        return new RedirectView("/");
    }

}
