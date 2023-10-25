package com.licretey.web.context;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements ConfigurableWebApplication {
    // protected 让子类可以获取
    protected ServletContext servletContext;
    protected ServletConfig servletConfig;

    @Override
    public void setServletContext(ServletContext servletContext) {

    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {

    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 添加后置处理器
     *
     * ServletContextAware的子类中若同样包含 ServletContext，并具备对应的set方法时，会多次调用ServletContextAware及其子类的set方法
     * 为了只调用1次set方法，需要此处设置忽略
     * @param beanFactory
     */
    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // 添加servlet后置处理器
        beanFactory.addBeanPostProcessor(new ServletBeanPostProcess(this.servletContext,this.servletConfig));

        // 忽略
        beanFactory.ignoreDependencyInterface(ServletContextAware.class);
        beanFactory.ignoreDependencyInterface(ServletConfigAware.class);
    }
}
