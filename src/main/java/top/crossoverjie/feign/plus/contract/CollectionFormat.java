package top.crossoverjie.feign.plus.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CollectionFormat {

    /**
     * Allows setting the {@link feign.CollectionFormat} to be used while processing the
     * annotated method.
     * @return the {@link feign.CollectionFormat} to be used
     */
    feign.CollectionFormat value();

}