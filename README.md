# feign-plus

A better feign client library to combine with `SpringBoot`.

---



Write [Feign](https://github.com/OpenFeign/feign) client with `annotation`, like this:

We can provider an interface.

```java
@FeignPlusClient(name = "github", url = "${github.url}")
public interface Github {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<GitHubRes> contributors(@Param("owner") String owner, @Param("repo") String repo);
}
```

On the basis of `@SpringBootApplication`:

```java
@SpringBootApplication
@EnableFeignPlusClients(basePackages = "top.crossoverjie.feign.test")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```

Now we can use it as we normally use Spring.

```java
    @Autowired
    private Github github ;
    
    List<GitHubRes> contributors = github.contributors("crossoverJie", "feign-plus");
    logger.info("contributors={}", new Gson().toJson(contributors));    
```


# More configuration

```properties
feign.plus.max-idle-connections = 520
feign.plus.connect-timeout = 11000
feign.plus.read-timeout = 12000
# default(okhttp3)
feign.httpclient=http2Client
```