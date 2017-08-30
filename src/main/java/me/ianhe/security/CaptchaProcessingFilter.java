package me.ianhe.security;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author iHelin
 * @since 2017/8/30 14:34
 */
public class CaptchaProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private String usernameParam = "username";
    private String passwordParam = "password";
    private String validCodeParam = "captcha";

    public CaptchaProcessingFilter() {
        super(new AntPathRequestMatcher("/admin/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter(usernameParam);
        String password = request.getParameter(passwordParam);
        String validCode = request.getParameter(validCodeParam);
        valid(validCode, request.getSession());
        MyUserAuthenticationToken token = new MyUserAuthenticationToken(username, password, validCode);
        return getAuthenticationManager().authenticate(token);
    }

    public void valid(String validCode, HttpSession session) {
        if (StringUtils.isBlank(validCode)) {
            throw new BadCredentialsException("验证码为空!");
        }
        String sessionCaptcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!validCode.equalsIgnoreCase(sessionCaptcha)) {
            throw new BadCredentialsException("验证码错误!");
        }
    }

}
