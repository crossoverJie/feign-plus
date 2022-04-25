package top.crossoverjie.feign.plus.decoder;

import cn.hutool.core.util.StrUtil;
import feign.Response;
import feign.gson.GsonDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.crossoverjie.feign.plus.log.FeignLogInterceptor;
import top.crossoverjie.feign.plus.springboot.FeignSpringContextHolder;

import java.lang.reflect.Type;


/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/7 23:46
 * @since JDK 11
 */
@Slf4j
public class FeignPlusDecoder extends GsonDecoder {
    @SneakyThrows
    @Override
    public Object decode(Response response, Type type) {
        Object decode = super.decode(response, type);
        // build target info.
        String interfaceName = response.request().requestTemplate().methodMetadata().targetType().getName();
        String targetMethod = response.request().requestTemplate().methodMetadata().configKey();
        String target = StrUtil.format("{}.{}", interfaceName, targetMethod);
        FeignLogInterceptor logInterceptor = FeignSpringContextHolder.getBean(FeignLogInterceptor.class);
        logInterceptor.response(target, response.request().url(), decode);
        return decode;
    }
}
