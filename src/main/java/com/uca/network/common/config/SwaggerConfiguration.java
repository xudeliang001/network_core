package com.uca.network.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Autowired
    private ServerConfig config;

    @Bean
    public Docket ucaNetworkCore() {
        List<Parameter> params = new ArrayList<>();
        Parameter parameter = new ParameterBuilder()
                .name("x-auth-token")
                .description("验证token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        params.add(parameter);
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(Boolean.valueOf(true))
                .apiInfo(apiInfo())
                .globalOperationParameters(params)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.uca.network"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("uca network core")
                .description("Uca Network Core的API列表，使用Restful规范")
                .version("1.0")
                .build();
    }
}
