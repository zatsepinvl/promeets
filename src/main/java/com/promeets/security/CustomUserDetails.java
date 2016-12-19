package com.promeets.security;

import org.springframework.security.core.GrantedAuthority;
import com.promeets.model.entity.User;

import java.util.Collection;

/**
 * Created by Vladimir on 10.04.2016.
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public CustomUserDetails(User user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (this.user.getUserId() == user.getUserId()) {
            this.user = user;
        }
    }
}
