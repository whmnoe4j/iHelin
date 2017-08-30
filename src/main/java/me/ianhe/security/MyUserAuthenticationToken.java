package me.ianhe.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author iHelin
 * @since 2017/8/30 14:33
 */
public class MyUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String validCode;

    public MyUserAuthenticationToken(String principal, String credentials, String validCode) {
        super(principal, credentials);
        this.validCode = validCode;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

}
