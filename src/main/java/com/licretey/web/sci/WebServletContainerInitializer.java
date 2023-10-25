package com.licretey.web.sci;

import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Set;

/**
 * 对接接口：真正开始对接SCI（被servlet中的SPI加载，然后同一调用onStartup）
 */
public class WebServletContainerInitializer implements ServletContainerInitializer {

    /**
     *
     * @param webApplications 被@HandlesTypes(className)注解标记的子类集合
     *            （用于执行各自的onStartup，进行servlet的创建, 以及初始化ioc）
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> webApplications, ServletContext servletContext) throws ServletException {
        if(ObjectUtils.isEmpty(webApplications)) return;

        final ArrayList<WebApplicationInitializer> initializers = new ArrayList<>(webApplications.size());
        // 收集非接口、非抽象类、WebApplicationInitializer子类的对象集合
        for (Class<?> webApplication : webApplications) {
            if (!webApplication.isInterface()
            && !Modifier.isAbstract(webApplication.getModifiers())
            && webApplication.isAssignableFrom(WebApplicationInitializer.class)) {
                try {
                    // 利用Spring提供的反射工具类反射创建类
                    WebApplicationInitializer webApplicationInitializer = (WebApplicationInitializer) (ReflectionUtils.accessibleConstructor(webApplication).newInstance());
                    initializers.add(webApplicationInitializer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 调用onStartup
        if(!ObjectUtils.isEmpty(initializers)){
            for (WebApplicationInitializer initializer : initializers) {
                initializer.onStartUp(servletContext);
            }
        }
    }
}
