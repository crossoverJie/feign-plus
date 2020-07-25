package top.crossoverjie.feign.test;

import feign.Param;
import feign.RequestLine;
import top.crossoverjie.feign.plus.register.FeignPlusClient;

import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/25 02:54
 * @since JDK 11
 */
@FeignPlusClient(name = "github", url = "${github.url}")
public interface Github {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<GitHubRes> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
