package me.ianhe.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        if (isIgnoredUri(uri)) {
            return true;
        }
        Object obj = null;
        if (session.getAttribute("adminUser") != null) {
            obj = session.getAttribute("adminUser");
        }
        if (obj == null) {
            String path = request.getContextPath();
            String from = request.getServletPath();
            String queryStr = request.getQueryString();
            if (from.endsWith("/login")) {
                return true;
            }
            if (!StringUtils.isEmpty(queryStr)) {
                from = from + "?" + URLEncoder.encode(queryStr, "UTF-8");
            }
            if (from.endsWith("/login"))
                from = "";
            response.sendRedirect(path + "/admin/login?from=" + from);
            return false;
        }
        return true;
    }

    protected boolean isIgnoredUri(String uri) {
        return uri.matches(".+(?i)(login|logout|400|403|404|500)+");
    }

    protected boolean isIndexUri(String uri) {
        return uri.matches(".+(?i)(index)+");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
