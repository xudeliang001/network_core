package com.uca.network.common.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SuppressWarnings("Duplicates")
public class RestClient {
    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=utf-8";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String X_AUTH_TOKEN = "x-auth-token";
    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(String url, Class<T> returnType) {
        logger.info("get 请求参数url: " + url);
        return getRequest(url, returnType, null);
    }

    private <T> T sendRequestWithParam(String url, HttpMethod method, HttpEntity httpEntity, Map param, Class<T> returnType) {
        return sendRequest(url, method, httpEntity, param, returnType).getBody();
    }

    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, HttpEntity httpEntity, Map param, Class<T> returnType) {
        long startTime = System.currentTimeMillis();
        try {
            logger.info(method.name() + " 请求url: " + url + "  请求参数: " + JSON.toJSONString(httpEntity));
        } catch (Exception e) {
            //防止logger的时候异常导致后面不能进行
            logger.error(e.getMessage());
        }
        try {
            ResponseEntity<T> response = param != null ? restTemplate.exchange(url, method, httpEntity, returnType, param)
                    : restTemplate.exchange(url, method, httpEntity, returnType);

            long endTime = System.currentTimeMillis();
            logger.info(method.name() + " 请求url: " + url + "  响应结果: " + JSON.toJSONString(response));
            logger.info(method.name() + " 请求url: " + url + "  请求时间: " + (endTime - startTime) + "ms");
            return response;
        } catch (HttpClientErrorException err) {
            logger.error(err.getMessage(), err);
            logger.error("client response body: " + err.getResponseBodyAsString());
            throw err;
        }catch (HttpServerErrorException err){
            logger.error(err.getMessage(), err);
            logger.error("server response body: " + err.getResponseBodyAsString());
            throw err;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private <T> T sendRequestWithType(String url, HttpMethod method, HttpEntity httpEntity, Class<T> returnType) {
        return sendRequestWithParam(url, method, httpEntity, null, returnType);
    }

    private <T> T getRequest(String url, Class<T> returnType, HttpEntity<?> requestEntity) {
        return sendRequestWithParam(url, HttpMethod.GET, requestEntity, null, returnType);
    }

    private String sendRequest(String url, HttpEntity httpEntity, HttpMethod method) {
        return sendRequestWithType(url, method, httpEntity, String.class);
    }


    public <T> T putWithToken(String url, String token, Object body, Class<T> returnType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Accept", "application/json;charset=UTF-8");
        headers.add(X_AUTH_TOKEN, token);
        HttpEntity httpEntity = new HttpEntity(body, headers);
        HttpMethod method = HttpMethod.PUT;
        return sendRequestWithType(url, method, httpEntity, returnType);
    }


    public <T> T getWithToken(String url, String token, Class<T> returnType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_AUTH_TOKEN, token);
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Accept", "application/json;charset=UTF-8");
        HttpEntity httpEntity = new HttpEntity(headers);
        HttpMethod method = HttpMethod.GET;
        return sendRequestWithType(url, method, httpEntity, returnType);
    }

    public <T> T postWithToken(String url, String token, Object body, Class<T> returnType) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        headers.add("Accept", "application/json;charset=UTF-8");
        headers.add(X_AUTH_TOKEN, token);
        HttpEntity httpEntity = new HttpEntity(body, headers);
        HttpMethod method = HttpMethod.POST;
        return sendRequestWithType(url, method, httpEntity, returnType);
    }

    public ResponseEntity<String> deleteWithToken(String token, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(X_AUTH_TOKEN, token);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> response = sendRequest(url, HttpMethod.DELETE, httpEntity, null, String.class);
        return response;
    }


    public String delete(String url, Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        String body = JSON.toJSONString(object);
        HttpEntity httpEntity = new HttpEntity<>(body, headers);
        HttpMethod method = HttpMethod.DELETE;
        return sendRequest(url, httpEntity, method);
    }
}
