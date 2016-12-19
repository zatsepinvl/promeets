package com.promeets.model.service.google;

import org.springframework.stereotype.Component;

/**
 * Created by Vladimir on 16.03.2016.
 */
@Component
public class OAuthInfo {

    private String client_id;
    private String project_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_secret;
    private String[] redirect_uris;

    public String getClient_id() {
        return client_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getAuth_uri() {
        return auth_uri;
    }

    public String getToken_uri() {
        return token_uri;
    }

    public String getAuth_provider_x509_cert_url() {
        return auth_provider_x509_cert_url;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String[] getRedirect_uris() {
        return redirect_uris;
    }


}


