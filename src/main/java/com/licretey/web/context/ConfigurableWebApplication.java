package com.licretey.web.context;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public interface ConfigurableWebApplication extends ApplicationContext {
    void setServletContext(ServletContext servletContext);
    void setServletConfig(ServletConfig servletConfig);
    ServletContext getServletContext();
    ServletConfig getServletConfig();
}
