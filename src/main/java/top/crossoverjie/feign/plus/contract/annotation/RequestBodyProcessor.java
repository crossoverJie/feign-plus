package top.crossoverjie.feign.plus.contract.annotation;

import org.springframework.web.bind.annotation.RequestBody;
import top.crossoverjie.feign.plus.contract.AnnotatedParameterProcessor;
import top.crossoverjie.feign.plus.contract.HttpEncoding;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 00:21
 * @since JDK 11
 */
public class RequestBodyProcessor implements AnnotatedParameterProcessor {

    private static final Class<RequestBody> ANNOTATION = RequestBody.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation, Method method) {
        context.getMethodMetadata().template().header(HttpEncoding.CONTENT_TYPE, "application/json");
        return true;
    }

}