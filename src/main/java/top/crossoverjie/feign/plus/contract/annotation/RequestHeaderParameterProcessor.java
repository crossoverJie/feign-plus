package top.crossoverjie.feign.plus.contract.annotation;

import feign.MethodMetadata;
import org.springframework.web.bind.annotation.RequestHeader;
import top.crossoverjie.feign.plus.contract.AnnotatedParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 00:19
 * @since JDK 11
 */
public class RequestHeaderParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<RequestHeader> ANNOTATION = RequestHeader.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation, Method method) {
        int parameterIndex = context.getParameterIndex();
        Class<?> parameterType = method.getParameterTypes()[parameterIndex];
        MethodMetadata data = context.getMethodMetadata();

        if (Map.class.isAssignableFrom(parameterType)) {
            checkState(data.headerMapIndex() == null,
                    "Header map can only be present once.");
            data.headerMapIndex(parameterIndex);

            return true;
        }

        String name = ANNOTATION.cast(annotation).value();
        checkState(emptyToNull(name) != null,
                "RequestHeader.value() was empty on parameter %s", parameterIndex);
        context.setParameterName(name);

        Collection<String> header = context.setTemplateParameter(name,
                data.template().headers().get(name));
        data.template().header(name, header);
        return true;
    }

}
