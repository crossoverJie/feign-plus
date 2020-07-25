package top.crossoverjie.feign.test;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import top.crossoverjie.feign.plus.register.EnableFeignPlusClients;

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
public class SpringBootTest1 {

    private Logger logger = LoggerFactory.getLogger(SpringBootTest1.class) ;

    @Autowired
    private Github github ;

    @Test
    public void test1(){
        List<GitHubRes> contributors = github.contributors("crossoverJie", "feign-plus");
        logger.info("contributors={}", new Gson().toJson(contributors));
    }
}
