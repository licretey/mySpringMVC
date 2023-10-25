package com.licretey.web.sci;

import javax.servlet.ServletContext;

// 对接口
public interface WebApplicationInitializer {
    void onStartUp(ServletContext servletContext);
}
