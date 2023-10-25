package com.licretey.web.context;

import org.springframework.beans.factory.Aware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public interface ServletConfigAware extends Aware {
    void setServletConfig(ServletConfig servletConfig);
}
