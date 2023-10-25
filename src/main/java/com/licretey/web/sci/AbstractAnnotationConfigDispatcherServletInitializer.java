package com.licretey.web.sci;

import com.licretey.web.context.AnnotationConfigWebApplicationContext;
import com.licretey.web.context.WebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.servlet.Filter;

/**
 * 创建DispatcherServlet
 * 初始化ioc
 */
public abstract class AbstractAnnotationConfigDispatcherServletInitializer extends AbstractDispatcherServletInitializer {

    /**
     * 将扫描到的包注册到父容器中
     * @return
     */
    @Override
    protected AnnotationConfigApplicationContext createRootApplicationContext() {
        final Class<?>[] rootConfigClasses = getRootConfigClasses();
        if(!ObjectUtils.isEmpty(rootConfigClasses)){
            final AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
            rootContext.register(rootConfigClasses);
            return rootContext;
        }
        return null;
    }

    /**
     * 将扫描到的包注册到mybatis自定义容器中
     * @return
     */
    @Override
    protected WebApplicationContext createWebApplicationContext() {
        final Class<?>[] webConfigClasses = getWebConfigClasses();
        if(!ObjectUtils.isEmpty(webConfigClasses)){
            final AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
            webContext.register(webConfigClasses);
            return webContext;
        }
        return null;
    }

    @Override
    protected Filter[] getFilters() {
        return new Filter[0];
    }


    /**
     * 使用方式如下
     *      class TestObj extends AbstractAnnotationConfigDispatcherServletInitializer{
     *         @Override
     *         protected Class<?>[] getRootConfigClasses() {
     *             return new Class[]{Obt.class};
     *         }
     *
     *         @Override
     *         protected Class<?>[] getWebConfigClasses() {
     *             return new Class[]{Obt.class};
     *         }
     *     }
     */
}
