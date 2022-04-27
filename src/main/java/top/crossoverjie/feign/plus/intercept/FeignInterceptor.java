package top.crossoverjie.feign.plus.intercept;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import top.crossoverjie.feign.plus.contract.HttpEncoding;
import top.crossoverjie.feign.plus.log.FeignLogInterceptor;
import top.crossoverjie.feign.plus.springboot.FeignSpringContextHolder;

import java.nio.charset.StandardCharsets;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 01:04
 * @since JDK 11
 */
public class FeignInterceptor implements RequestInterceptor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpEncoding.CONTENT_TYPE, "application/json");
        String body = StrUtil.str(template.body(), StandardCharsets.UTF_8);
        FeignLogInterceptor logInterceptor = FeignSpringContextHolder.getBean(FeignLogInterceptor.class);
        String interfaceName = template.methodMetadata().targetType().getName();
        String targetMethod = template.methodMetadata().configKey();
        String target = StrUtil.format("{}.{}", interfaceName, targetMethod);
        logInterceptor.request(target, template.request().url(), body);
    }


}