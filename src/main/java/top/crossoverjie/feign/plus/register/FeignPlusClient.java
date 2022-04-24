package top.crossoverjie.feign.plus.register;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author crossoverJie
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeignPlusClient {

    String name() default "";

    /**
     *
     * @return Target url
     */
    String url() default "";

    /**
     * @return port
     */
    String port() default "80";
}
