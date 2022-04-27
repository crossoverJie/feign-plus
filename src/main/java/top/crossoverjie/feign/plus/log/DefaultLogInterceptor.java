package top.crossoverjie.feign.plus.log;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/12 01:04
 * @since JDK 11
 */
@Slf4j
public class DefaultLogInterceptor implements FeignLogInterceptor {
    @Override
    public void request(String target, String url, String body) {
        log.debug("feign targetMethod={}, request url={}, body={}", target, url, body);
    }

    @Override
    public void exception(String target, String url, FeignException feignException) {
        log.debug("feign targetMethod={}, exception url={}, body={}", target, url, feignException.getMessage());
    }

    @Override
    public void response(String target, String url, Object response) {
        log.debug("feign targetMethod={}, response url={}, body={}", target, url, response.toString());
    }

}
