package com.deta.achi.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 手动获取spring容器内的成员
 * @author DJWang
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    /**
     * 捕获IoC容器上下文对象
     */
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext=applicationContext;
    }

    /**
     * 调用spring上下文对象，获取容器内的对象
     */
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
