package top.crossoverjie.feign.plus.contract;

import org.springframework.http.HttpHeaders;

import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 00:23
 * @since JDK 11
 */
public final class FeignUtils {

    private FeignUtils() {
        throw new IllegalStateException("Can't instantiate a utility class");
    }

    static HttpHeaders getHttpHeaders(Map<String, Collection<String>> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            httpHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return httpHeaders;
    }

    static Map<String, Collection<String>> getHeaders(HttpHeaders httpHeaders) {
        LinkedHashMap<String, Collection<String>> headers = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }

        return headers;
    }

    static Collection<String> addTemplateParameter(Collection<String> possiblyNull,
                                                   String paramName) {
        Collection<String> params = ofNullable(possiblyNull).map(ArrayList::new)
                .orElse(new ArrayList<>());
        params.add(String.format("{%s}", paramName));
        return params;
    }

}