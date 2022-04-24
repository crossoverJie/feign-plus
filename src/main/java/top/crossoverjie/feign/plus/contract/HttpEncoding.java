package top.crossoverjie.feign.plus.contract;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2022/2/10 00:22
 * @since JDK 11
 */
public interface HttpEncoding {

    /**
     * The HTTP Content-Length header.
     */
    String CONTENT_LENGTH = "Content-Length";

    /**
     * The HTTP Content-Type header.
     */
    String CONTENT_TYPE = "Content-Type";

    /**
     * The HTTP Accept-Encoding header.
     */
    String ACCEPT_ENCODING_HEADER = "Accept-Encoding";

    /**
     * The HTTP Content-Encoding header.
     */
    String CONTENT_ENCODING_HEADER = "Content-Encoding";

    /**
     * The GZIP encoding.
     */
    String GZIP_ENCODING = "gzip";

    /**
     * The Deflate encoding.
     */
    String DEFLATE_ENCODING = "deflate";

}
