package top.crossoverjie.feign.plus.decoder;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/4/28 00:15
 * @since JDK 11
 */
public interface FeignErrorDecoder {
    /** custom exception
     * @param methodKey method name
     * @param response raw response(json)
     * @param e exception
     * @return custom exception
     */
    Exception decode(String methodKey, String response, Exception e);
}
