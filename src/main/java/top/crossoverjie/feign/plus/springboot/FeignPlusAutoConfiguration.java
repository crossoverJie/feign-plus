package top.crossoverjie.feign.plus.springboot;

import feign.Client;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    private FeignPlusConfigurationProperties feignPlusConfigurationProperties ;

    public FeignPlusAutoConfiguration(FeignPlusConfigurationProperties feignPlusConfigurationProperties) {
        this.feignPlusConfigurationProperties = feignPlusConfigurationProperties;
    }

    @Bean
    public ConnectionPool connectionPool(){
        return new ConnectionPool(feignPlusConfigurationProperties.getMaxIdleConnections(),
                feignPlusConfigurationProperties.getKeepAliveDuration(), TimeUnit.MINUTES) ;
    }


    @Bean(value = "client")
    public Client okHttpClient(ConnectionPool connectionPool){
        OkHttpClient delegate = new OkHttpClient().newBuilder()
                .connectionPool(connectionPool)
                .connectTimeout(feignPlusConfigurationProperties.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(feignPlusConfigurationProperties.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(feignPlusConfigurationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .build();
        return new feign.okhttp.OkHttpClient(delegate) ;
    }

}
