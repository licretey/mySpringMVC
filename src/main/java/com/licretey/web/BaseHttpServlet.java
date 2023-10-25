package com.licretey.web;

import com.licretey.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseHttpServlet extends HttpServlet {
    protected WebApplicationContext webApplicationContext;
    public BaseHttpServlet(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    // web ioc容器初始化与配置
    @Override
    public void init() throws ServletException {
        // 父子容器 todo
    }
}
