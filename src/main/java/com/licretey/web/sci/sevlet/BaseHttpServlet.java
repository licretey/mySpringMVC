package com.licretey.web.sci.sevlet;

import com.licretey.web.context.AbstractRefreshableWebApplicationContext;
import com.licretey.web.context.WebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

// 对接口
public abstract class BaseHttpServlet extends HttpServlet {
    // 子容器，父容器从ServletContext中获取
    protected WebApplicationContext webApplicationContext;
    public BaseHttpServlet(WebApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    // web ioc容器初始化与配置
    @Override
    public void init() throws ServletException {
        // 父子容器 todo
        ServletContext servletContext = getServletContext();
        ApplicationContext rootContext = (ApplicationContext)servletContext.getAttribute(WebApplicationContext.ROOT_NAME);
        if(!ObjectUtils.isEmpty(webApplicationContext)){
            // 因为刷新需要强壮
            AbstractRefreshableWebApplicationContext wac = (AbstractRefreshableWebApplicationContext) this.webApplicationContext;
            // 设置父容器
            if(wac.getParent() == null){
                wac.setParent(rootContext);
            }
            // 配置上下文
            wac.setServletContext(servletContext);
            wac.setServletConfig(getServletConfig());
            // 子容器刷新
            wac.refresh();
        }
        // 预留外部接口（模板模式）
        onRefresh();
    }

    protected abstract void onRefresh();
}
