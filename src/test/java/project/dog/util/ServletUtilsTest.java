package project.dog.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@Slf4j
class ServletUtilsTest {

    @Test
    void get(){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("Key1", "value1");
        paramMap.put("key2", "value2");
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("headerKey1", "headerValue1");

        ServletUtils.get("https://www.naver.com", paramMap, headerMap);
    }

}