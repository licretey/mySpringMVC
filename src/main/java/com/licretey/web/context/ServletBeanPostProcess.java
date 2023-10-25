package com.licretey.web.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class ServletBeanPostProcess implements BeanPostProcessor {
    private ServletContext servletContext;
    private ServletConfig servletConfig;
    public ServletBeanPostProcess(ServletContext servletContext, ServletConfig servletConfig) {
        this.servletContext = servletContext;
        this.servletConfig = servletConfig;
    }

    /**
     * 用户想拿到servletContext、servletConfig，需要自行实现XXXAutowire, 通过改接口获取
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean != null && bean instanceof ServletConfigAware){
            ((ServletConfigAware)bean).setServletConfig(this.servletConfig);
        }
        if(bean != null && bean instanceof ServletContextAware){
            ((ServletContextAware)bean).setServletContext(this.servletContext);
        }
        return bean;
    }
}
