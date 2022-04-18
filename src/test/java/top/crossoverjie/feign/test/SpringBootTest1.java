package top.crossoverjie.feign.test;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import top.crossoverjie.feign.plus.register.EnableFeignPlusClients;

import javax.annotation.Resource;
import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2020/7/25 14:00
 * @since JDK 11
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test0")
@SpringBootTest(classes = SpringBootTest1.class)
@EnableFeignPlusClients(basePackages = "top.crossoverjie.feign.test")
@EnableAutoConfiguration
@Slf4j
public class SpringBootTest1 {

    @Resource
    private Github github ;

    @Test
    public void test1(){
        List<GitHubRes> contributors = github.contributors("crossoverJie", "feign-plus");
        log.info("contributors={}", new Gson().toJson(contributors));
    }
}
