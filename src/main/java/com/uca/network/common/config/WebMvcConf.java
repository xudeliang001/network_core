package com.uca.network.common.config;

import com.uca.network.common.utils.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {
    @Bean
    public RestTemplate restTemplate() {
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, m);

        return restTemplate;
    }

    @Bean
    RestClient restClient(RestTemplate restTemplate) {
        return new RestClient(restTemplate);
    }


    @Bean
    public ResponseMetricsInterceptor responseMetricsInterceptor() {
        return new ResponseMetricsInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(responseMetricsInterceptor());
    }
}
