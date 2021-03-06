package me.ianhe.config;

import com.google.common.collect.Maps;
import me.ianhe.utils.JsonUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/9/20 11:39
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e, ServletResponse response) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", "error");
        res.put("data", e.getMessage());
        try {
            response.getWriter().print(JsonUtil.toJson(res));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
