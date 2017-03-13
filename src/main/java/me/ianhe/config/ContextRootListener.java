package me.ianhe.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextRootListener implements ServletContextListener {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void contextDestroyed(final ServletContextEvent event) {
        LOGGER.debug("context is destroyed");
    }

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        CommonConfig.init(context.getRealPath("/"), context.getContextPath());
        LOGGER.info("context is initialized,root path: {}",context.getRealPath("/"));
    }

}