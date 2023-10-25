package com.licretey.web.sci;

import com.licretey.web.context.WebApplicationContext;
import com.licretey.web.sci.sevlet.DispatcherServlet;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * 创建DispatcherServlet
 * 初始化ioc
 */
public abstract class AbstractDispatcherServletInitializer implements WebApplicationInitializer {
    public static final String DEFAULT_SERVLET_NAME = "dispatcher";
    public static final String DEFAULT_FILTERS_NAME = "filters";
    public static final int FILE_SIZE = 1024 * 1024;
    public static final int THREAD_SIZE = 5;

    @Override
    public void onStartUp(ServletContext servletContext) {
        // 创建父容器
        final AnnotationConfigApplicationContext rootApplicationContext = createRootApplicationContext();
        // 将父容器放入servletContext
        servletContext.setAttribute(WebApplicationContext.ROOT_NAME, rootApplicationContext);
        // 刷新父容器: 源码中通过servlet事件进行refresh
        rootApplicationContext.refresh();
        // 创建子容器
        final WebApplicationContext webApplicationContext = createWebApplicationContext();

        // 创建DispatcherServlet分发器
        final DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        final ServletRegistration.Dynamic dynamic = servletContext.addServlet(DEFAULT_SERVLET_NAME, dispatcherServlet);

        // 配置信息
        final Filter[] filters = getFilters();
        if (filters != null) {
            for (Filter filter : filters) {
                servletContext.addFilter(DEFAULT_FILTERS_NAME, filter);
            }
        }
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping(getMappings());
        // 从配置文件中拿
        // 创建文件： location 文件位置 file上传大小 线程数
        final MultipartConfigElement configElement = new MultipartConfigElement(null, 5 * FILE_SIZE, 5 * FILE_SIZE, THREAD_SIZE);
        dynamic.setMultipartConfig(configElement); // 配置文件信息

    }

    // 过滤器
    protected abstract Filter[] getFilters();

    // 映射器
    protected String[] getMappings() {
        return new String[]{"/"};
    }


    // 创建父容器
    protected abstract AnnotationConfigApplicationContext createRootApplicationContext();

    // 创建子容器
    protected abstract WebApplicationContext createWebApplicationContext();


    // 获取包扫描配置类
    protected abstract Class<?>[] getRootConfigClasses();

    protected abstract Class<?>[] getWebConfigClasses();

}
