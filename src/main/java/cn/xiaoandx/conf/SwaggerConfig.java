package cn.xiaoandx.conf;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@SuppressWarnings("unchecked")
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Value("${sys.version}")
	private String systemPublish;
	
	
	
	@Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户登录注册相关API")
                .pathMapping("/")
                .select()
                .paths(or(regex("/v2/open/user/.*")))//过滤的接口
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("用户登录注册相关API")
                        .version(systemPublish)
                        .build());
    }
	
	@Bean
    public Docket examApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户答题操作相关API")
                .pathMapping("/")
                .select()
                .paths(or(regex("/v2/open/exam/.*")))//过滤的接口
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("用户答题操作相关API")
                        .version(systemPublish)
                        .build());
    }
}