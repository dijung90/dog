package project.dog.web.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dog.constant.Common;
import project.dog.util.Servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/login/naver")
public class NaverController {

    @Value("${CLIENT_ID}")
    private String clientID;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Value("${AUTHORIZE_URL}")
    private String authorizeUrl;

    @Value("${TOKEN_URL}")
    private String tokenUrl;

    @Value("${REDIRECT_URL}")
    private String redirectUrl;

    @Value("${USER_INFO_URL}")
    private String userInfoUrl;

    private String state;

    @GetMapping("/authorize")
    public void authorize(HttpServletResponse response) throws IOException {
        log.info("authorize in");

        SecureRandom random = new SecureRandom();
        state = new BigInteger(130, random).toString();

        String url = authorizeUrl+"?response_type=code&client_id="+clientID +"&redirect_uri=" + redirectUrl + "&state="+ state;
        log.info("state={}", state);

        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public String callback(
            HttpServletResponse response,
            @RequestParam(defaultValue = "null") String code,
            @RequestParam(defaultValue = "null") String error){

        log.info("callback in");
        log.info("code={}", code);
        log.info("error={}", error);

        if(error.equals("access_denied")){
            return "error";
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(Common.GRANT_TYPE, "authorization_code");
        paramMap.put(Common.CLIENT_ID, clientID);
        paramMap.put(Common.CLIENT_SECRET, clientSecret);
        paramMap.put(Common.CODE, code);
        paramMap.put(Common.STATE, state);

        ResponseEntity<Map> getToken = Servlet.post(tokenUrl, paramMap, MediaType.APPLICATION_FORM_URLENCODED,Map.class);
        log.info("getToken Status ={}", getToken.getStatusCodeValue());
        if(getToken.getStatusCodeValue() == 200){
            Map<String, String> resultBody = getToken.getBody();

            String accessToken = resultBody.get("access_token");
            String refreshToken = resultBody.get("refresh_token");
            int expiresIn = Integer.parseInt(resultBody.get("expires_in"));
            log.info("accessToken = {}", accessToken);
            log.info("refreshToken = {}", refreshToken);
            log.info("expiresIn = {}", expiresIn);

            //사용자 정보(식별자) 가져오기
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add(Common.AUTHORIZATION, Common.BEARER + accessToken);
            ResponseEntity<Map> getUserInfo = Servlet.post(userInfoUrl, headers, Map.class);
            log.info("userInfo = {}", getUserInfo.getBody());
            Map<String, String> userInfo = (Map<String, String>)getUserInfo.getBody().get("response");
            String userId = userInfo.get("id");
            log.info("userId = {}", userId);
            // 이미 회원가입한 사용자일경우 메인 페이지로 이동


            // 첫 로그인(회원x) 사용자일경우 회원가입 페이지로 이동
            return "ok";
        }

        return "false";
    }
}