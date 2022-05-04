package top.crossoverjie.feign.plus.contract;

import feign.QueryMap;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface SpringQueryMap {

    /**
     * @see QueryMap#encoded()
     * @return alias for {@link #encoded()}.
     */
    @AliasFor("encoded")
    boolean value() default false;

    /**
     * @see QueryMap#encoded()
     * @return Specifies whether parameter names and values are already encoded.
     */
    @AliasFor("value")
    boolean encoded() default false;

}