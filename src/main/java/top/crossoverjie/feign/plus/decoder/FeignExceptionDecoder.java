package top.crossoverjie.feign.plus.decoder;

import cn.hutool.core.util.StrUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import top.crossoverjie.feign.plus.log.FeignLogInterceptor;
import top.crossoverjie.feign.plus.springboot.FeignSpringContextHolder;

import static feign.FeignException.errorStatus;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/21 21:48
 * @since JDK 11
 */
public class FeignExceptionDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        feign.FeignException exception = errorStatus(methodKey, response);
        FeignLogInterceptor logInterceptor = FeignSpringContextHolder.getBean(FeignLogInterceptor.class);
        String targetMethod = response.request().requestTemplate().methodMetadata().configKey();
        String interfaceName = response.request().requestTemplate().methodMetadata().targetType().getName();
        String target = StrUtil.format("{}.{}",interfaceName, targetMethod);
        logInterceptor.exception(target, response.request().url(), exception);
        return exception;
    }
}
