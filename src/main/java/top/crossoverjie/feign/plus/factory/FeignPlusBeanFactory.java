package top.crossoverjie.feign.plus.factory;

import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import top.crossoverjie.feign.plus.springboot.FeignPlusConfigurationProperties;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/25 02:20
 * @since JDK 11
 */
public class FeignPlusBeanFactory<T> implements FactoryBean<T>, ApplicationContextAware {


    private ApplicationContext applicationContext;

    private Class<T> proxyInterface ;

    private String url ;


    @Override
    public T getObject() throws Exception {
        okhttp3.OkHttpClient delegate = applicationContext.getBean("delegateClient", okhttp3.OkHttpClient.class) ;
        FeignPlusConfigurationProperties conf = applicationContext.getBean(FeignPlusConfigurationProperties.class) ;

        T target = Feign.builder()
                .client(new OkHttpClient(delegate))
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .retryer(new Retryer.Default(100, SECONDS.toMillis(1), 0))
                .options(new Request.Options(conf.getConnectTimeout(),conf.getReadTimeout(), true))
                .target(proxyInterface, url);

        return target;
    }

    @Override
    public Class<?> getObjectType() {
        return proxyInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext ;
    }

    public Class<T> getProxyInterface() {
        return proxyInterface;
    }

    public void setProxyInterface(Class<T> proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
