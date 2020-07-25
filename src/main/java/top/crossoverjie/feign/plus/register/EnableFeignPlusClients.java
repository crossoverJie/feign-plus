package top.crossoverjie.feign.plus.register;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/25 02:10
 * @since JDK 11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FeignPlusClientsRegistrar.class)
public @interface EnableFeignPlusClients {

    String[] value() default {};

    /**
     * Base packages to scan for annotated components.
     * @return
     */
    String[] basePackages() default {};
}
