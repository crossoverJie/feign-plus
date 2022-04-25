package top.crossoverjie.feign.plus.log;

import feign.FeignException;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/11 22:16
 * @since JDK 11
 */
public interface FeignLogInterceptor {
    /**
     * before request
     *
     * @param target method name
     * @param url    请求 URL: http://127.0.0.1:8080/v1/demo/1
     * @param body   body {"name":"abc"}
     */
    void request(String target, String url, String body);

    /**
     * on exception
     *
     * @param target         method name
     * @param url            URL: http://127.0.0.1:8080/v1/demo/1
     * @param feignException exception
     */
    void exception(String target, String url, FeignException feignException);

    /**
     * 成功响应时
     *  @param target   method name
     * @param url      请求 URL: http://127.0.0.1:8080/v1/demo/1
     * @param response 返回信息
     */
    void response(String target, String url, Object response);
}
