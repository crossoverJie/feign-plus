package top.crossoverjie.feign.plus.springboot;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/9 22:44
 * @since JDK 11
 */
public class FeignSpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }


    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    public static String getActiveProfile() {
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }


    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static String getAppName(){
        return applicationContext.getEnvironment().getProperty("spring.application.name");
    }


}
