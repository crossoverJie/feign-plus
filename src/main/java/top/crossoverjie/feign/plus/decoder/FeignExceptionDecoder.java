package top.crossoverjie.feign.plus.decoder;

import cn.hutool.core.util.StrUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.feign.plus.context.FeignContextHolder;
import top.crossoverjie.feign.plus.log.FeignLogInterceptor;
import top.crossoverjie.feign.plus.metrics.Metrics;
import top.crossoverjie.feign.plus.springboot.FeignSpringContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static feign.FeignException.errorStatus;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/21 21:48
 * @since JDK 11
 */
@Slf4j
public class FeignExceptionDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String rawResponse = parseResponse(response);

        feign.FeignException exception = errorStatus(methodKey, response);
        FeignLogInterceptor logInterceptor = FeignSpringContextHolder.getBean(FeignLogInterceptor.class);
        String targetMethod = response.request().requestTemplate().methodMetadata().configKey();
        String interfaceName = response.request().requestTemplate().methodMetadata().targetType().getName();
        String target = StrUtil.format("{}.{}", interfaceName, targetMethod);
        logInterceptor.exception(target, response.request().url(), exception);

        Long start = FeignContextHolder.getLocalTime();
        long end = System.currentTimeMillis();
        Metrics.time(Duration.ofMillis(end - start), "feign_call", "target", target, "status", "fail");
        Metrics.count("feign_call_exception", "target", target, "status", "fail");

        FeignErrorDecoder feignErrorDecoder = FeignSpringContextHolder.getBean(FeignErrorDecoder.class);
        return feignErrorDecoder.decode(methodKey, rawResponse, exception);
    }

    private String parseResponse(Response response) {
        String rawResponse = "";
        try {
            Reader reader = response.body().asReader(StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            rawResponse = stringBuilder.toString();
        } catch (IOException e) {
            log.error("parse response error", e);
        }
        return rawResponse;
    }
}
