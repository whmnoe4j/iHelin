package me.ianhe.db.entity;

import org.springframework.security.core.GrantedAuthority;

public class Authorities implements GrantedAuthority {

    public Authorities(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    private String username;

    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}