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
//        FeignContextHolder.setLocalTime();
        log.debug("feign targetMethod={}, request url={}, body={}", target, url, body);
    }

    @Override
    public void exception(String target, String url, FeignException feignException) {
        log.debug("feign targetMethod={}, exception url={}, body={}", target, url, feignException.getMessage());
//        Long start = FeignContextHolder.getLocalTime();
        long end = System.currentTimeMillis();
//        FMetrics.recordTime(Duration.ofMillis(end - start), "feign_call", "target", target, "status", "fail");
//        FMetrics.recordOne("feign_call_exception", "target", target, "status", "fail");
    }

    @Override
    public void response(String target, String url, Object response) {
        log.debug("feign targetMethod={}, response url={}, body={}", target, url, response.toString());
//        Long start = FeignContextHolder.getLocalTime();
        long end = System.currentTimeMillis();
//        FMetrics.recordTime(Duration.ofMillis(end - start), "feign_call", "target", target, "status", "success");
    }

}
