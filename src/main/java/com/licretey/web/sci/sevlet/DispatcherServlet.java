package com.licretey.web.sci.sevlet;

import com.licretey.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class DispatcherServlet extends BaseHttpServlet {
    public DispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    // 组件初始化
    @Override
    protected void onRefresh() {

    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("请求分发、组件初始化");

    }
}
