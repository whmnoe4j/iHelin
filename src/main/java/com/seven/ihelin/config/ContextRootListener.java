package com.seven.ihelin.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextRootListener implements ServletContextListener {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void contextDestroyed(final ServletContextEvent event) {
        LOGGER.debug("context is destroyed");
    }

    public void contextInitialized(ServletContextEvent event) {
        LOGGER.info("context is initialized");
        ServletContext context = event.getServletContext();
        CommonConfig.init(context.getRealPath("/"), context.getContextPath());
    }

}