package top.crossoverjie.feign.plus.contract.annotation;

import feign.MethodMetadata;
import top.crossoverjie.feign.plus.contract.AnnotatedParameterProcessor;
import top.crossoverjie.feign.plus.contract.SpringQueryMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 00:19
 * @since JDK 11
 */
public class QueryMapParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<SpringQueryMap> ANNOTATION = SpringQueryMap.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation, Method method) {
        int paramIndex = context.getParameterIndex();
        MethodMetadata metadata = context.getMethodMetadata();
        if (metadata.queryMapIndex() == null) {
            metadata.queryMapIndex(paramIndex);
            metadata.queryMapEncoded(((SpringQueryMap) annotation).encoded());
        }
        return true;
    }

}
