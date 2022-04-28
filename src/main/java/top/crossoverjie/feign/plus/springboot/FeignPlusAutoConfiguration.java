package top.crossoverjie.feign.plus.springboot;

import feign.Client;
import io.micrometer.core.instrument.MeterRegistry;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.crossoverjie.feign.plus.decoder.FeignErrorDecoder;
import top.crossoverjie.feign.plus.log.DefaultLogInterceptor;
import top.crossoverjie.feign.plus.log.FeignLogInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/25 01:16
 * @since JDK 11
 */
@Configuration
@EnableConfigurationProperties(FeignPlusConfigurationProperties.class)
public class FeignPlusAutoConfiguration {

    private FeignPlusConfigurationProperties feignPlusConfigurationProperties;

    public FeignPlusAutoConfiguration(FeignPlusConfigurationProperties feignPlusConfigurationProperties) {
        this.feignPlusConfigurationProperties = feignPlusConfigurationProperties;
    }

    @Bean
    public ConnectionPool connectionPool() {
        return new ConnectionPool(feignPlusConfigurationProperties.getMaxIdleConnections(),
                feignPlusConfigurationProperties.getKeepAliveDuration(), TimeUnit.MINUTES);
    }


    @Bean(value = "client")
    public Client okHttpClient(ConnectionPool connectionPool) {
        OkHttpClient delegate = new OkHttpClient().newBuilder()
                // skip ssl
                .hostnameVerifier((hostname, session) -> true)
                .connectionPool(connectionPool)
                .connectTimeout(feignPlusConfigurationProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(feignPlusConfigurationProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(feignPlusConfigurationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .build();
        return new feign.okhttp.OkHttpClient(delegate);
    }


    @Bean
    @ConditionalOnMissingBean(FeignSpringContextHolder.class)
    public FeignSpringContextHolder feignSpringContextHolder() {
        return new FeignSpringContextHolder();
    }

    @Bean()
    @ConditionalOnMissingBean(FeignLogInterceptor.class)
    public FeignLogInterceptor feignLogInterceptor() {
        return new DefaultLogInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean(MeterRegistryCustomizer.class)
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer(
            @Value("${spring.application.name}") String appName) {
        return registry -> registry.config().commonTags("app", appName);
    }

    @Bean
    @ConditionalOnMissingBean(FeignErrorDecoder.class)
    public FeignErrorDecoder feignErrorDecoder() {
        return (methodKey, response, e) -> e;
    }
}
