package project.dog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@DependsOn("restTemplate")
public class ServletUtils {

    private static RestTemplate restTemplate;

    @Resource(name = "restTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static String get(String url, Map<String, String> paramMap){
        HttpHeaders headers = new HttpHeaders();
        String params =  setUrlParams(paramMap);
        RequestEntity<?> request = new RequestEntity(headers, HttpMethod.GET, URI.create(url + "?" + params));
        log.info("request url ={}", request.getUrl());
        log.info("request header={}", request.getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        log.info("response header ={}", response.getHeaders());
        log.info("response status = {}", response.getStatusCode());

        return request.getUrl().toString();
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, String> paramMap, MediaType mediaType, Class<T> responseType){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        if(!paramMap.isEmpty()){
            Map<String, String> convertParamMap = paramMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
            requestBody.setAll(convertParamMap);
        }

        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<>(requestBody, headers, HttpMethod.POST, URI.create(url));

        return restTemplate.exchange(request, responseType);
    }

    public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, Class<T> responseType){
        RequestEntity<?> request = new RequestEntity(headers, HttpMethod.POST, URI.create(url));

        return restTemplate.exchange(request, responseType);
    }

    private static String setUrlParams(Map<String, String> paramMap){
        StringBuffer urlParam = new StringBuffer();
        paramMap.forEach((key, value) -> {
            if(urlParam.length() != 0) {
                urlParam.append("&");
            }
            urlParam.append(key + "=" + value);
        });

        log.info("urlParam={}", urlParam);
        return urlParam.toString();
    }
}
