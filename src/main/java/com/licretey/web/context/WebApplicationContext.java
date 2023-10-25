package com.licretey.web.context;

import org.springframework.context.ApplicationContext;

/**
 * 对接接口： 基于spring容器
 * Web IOC
 */
public interface WebApplicationContext extends ApplicationContext {
    public static final String ROOT_NAME = WebApplicationContext.class.getName() + "ROOT";

}
