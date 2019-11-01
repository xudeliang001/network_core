package com.uca.network;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
@EnableRabbit
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
/*//        Map map = applicationContext.getBeansOfType(SubnetService.class);
        SubnetService subnetService = applicationContext.getBean(SubnetService.class);
//        SubnetVo subnetVo = new SubnetVo();
//        subnetVo.setName("test123");
//        subnetVo.setCidr("192.168.8.8/29");
//        subnetVo.setNetworkId(UUID.randomUUID().toString());
//        subnetVo.setTenantId(UUID.randomUUID().toString());
//        subnetService.createSubnet(subnetVo);
//        SubnetVo subnetVot = subnetService.querySubnetDetail("2e6d9dfd-6352-4e01-8ada-a999e3a4937f",
//                "9e36edc120e84d8eb32ac269e637f25b");
//        System.out.println(JSON.toJSONString(subnetVot));

        List<SubnetVo> subnetVos = subnetService.querySubnets("2e6d9dfd-6352-4e01-8ada-a999e3a4937f",
                "342f4db8-09e6-487a-902e-9afcfe36fab0");
        System.out.println(JSON.toJSONString(subnetVos));

        subnetVos = subnetService.querySubnets("2d495a5a-64ca-43ae-bdb3-d5781d56bf78","");
        subnetService.deleteSubent(subnetVos.get(0).getId(),null);*/
    }

    /**
     * 后端开放跨域配置
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        // 允许Get的请求方法
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
