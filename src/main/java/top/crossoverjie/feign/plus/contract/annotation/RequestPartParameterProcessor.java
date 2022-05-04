package top.crossoverjie.feign.plus.contract.annotation;

import feign.MethodMetadata;
import org.springframework.web.bind.annotation.RequestPart;
import top.crossoverjie.feign.plus.contract.AnnotatedParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 00:21
 * @since JDK 11
 */
public class RequestPartParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<RequestPart> ANNOTATION = RequestPart.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context,
                                   Annotation annotation, Method method) {
        int parameterIndex = context.getParameterIndex();
        MethodMetadata data = context.getMethodMetadata();

        String name = ANNOTATION.cast(annotation).value();
        checkState(emptyToNull(name) != null,
                "RequestPart.value() was empty on parameter %s", parameterIndex);
        context.setParameterName(name);

        data.formParams().add(name);
        Collection<String> names = context.setTemplateParameter(name,
                data.indexToName().get(parameterIndex));
        data.indexToName().put(parameterIndex, names);
        return true;
    }

}