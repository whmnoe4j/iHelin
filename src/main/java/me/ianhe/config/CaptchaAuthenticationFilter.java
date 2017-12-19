package me.ianhe.config;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author iHelin
 * @since 2017/8/30 14:34
 */
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;


    public CaptchaAuthenticationFilter() {
        super(new AntPathRequestMatcher("/admin/login", "POST"));
    }


    /**
     * 验证码获取及校验
     *
     * @author iHelin
     * @since 2017/8/30 20:49
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            throw new AuthenticationServiceException("不支持的验证方法: " + request.getMethod());
        }

        String username = request.getParameter(usernameParameter);
        String password = request.getParameter(passwordParameter);
        String captcha = request.getParameter(captchaParameter);

        if (StringUtils.isBlank(username)) {
            throw new BadCredentialsException("用户名为空!");
        }
        if (StringUtils.isBlank(password)) {
            throw new BadCredentialsException("密码为空!");
        }
        if (StringUtils.isBlank(captcha)) {
            throw new BadCredentialsException("验证码为空!");
        }

        String sessionCaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(sessionCaptcha)) {
            throw new BadCredentialsException("验证码错误!");
        }
        AbstractAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(token);
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String getCaptchaParameter() {
        return captchaParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        this.captchaParameter = captchaParameter;
    }
}
