package project.dog.web.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dog.constant.Common;
import project.dog.util.ServletUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
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

    @GetMapping("/login/naver/authorize")
    public void authorize(HttpServletResponse response) throws IOException {
        log.info("authorize in");

        SecureRandom random = new SecureRandom();
        state = new BigInteger(130, random).toString();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("response_type", Common.CODE);
        paramMap.put(Common.CLIENT_ID, clientID);
        paramMap.put(Common.REDIRECT_URI, redirectUrl);
        paramMap.put(Common.STATE, state);

        String url = ServletUtils.get(authorizeUrl, paramMap);

        log.info("state={}", state);

        response.sendRedirect(url);
    }

    @GetMapping("/login/naver/callback")
    public void callback(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(defaultValue = "null") String code,
            @RequestParam(defaultValue = "null") String error) throws IOException {

        log.info("callback in");
        log.info("code={}", code);
        log.info("error={}", error);

        if(error.equals("access_denied")){
            //로그인 화면으로~
        }

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(Common.GRANT_TYPE, "authorization_code");
        paramMap.put(Common.CLIENT_ID, clientID);
        paramMap.put(Common.CLIENT_SECRET, clientSecret);
        paramMap.put(Common.CODE, code);
        paramMap.put(Common.STATE, state);

        ResponseEntity<Map> getToken = ServletUtils.post(tokenUrl, paramMap, MediaType.APPLICATION_FORM_URLENCODED,Map.class);
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
            ResponseEntity<Map> getUserInfo = ServletUtils.post(userInfoUrl, headers, Map.class);
            log.info("userInfo = {}", getUserInfo.getBody());
            Map<String, String> userInfo = (Map<String, String>)getUserInfo.getBody().get("response");
            String userId = userInfo.get("id");
            log.info("userId = {}", userId);

            Cookie cookie = new Cookie("access_token", accessToken);
            cookie.setMaxAge(expiresIn);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            // 이미 회원가입한 사용자일경우 메인 페이지로 이동


            // 첫 로그인(회원x) 사용자일경우 회원가입 페이지로 이동
            HttpSession session = request.getSession();
            session.setAttribute("snsId", "test!");
            //DB연결 전 임시 세션 저장 (삭제할거야~~)
            session.setAttribute("refresh_token", refreshToken);

            response.sendRedirect("/");
            //return "ok";
        }

        //return "false";
    }

    @GetMapping("/login/naver/refresh")
    public String refresh(@SessionAttribute(required = false) String refresh_token,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        log.info("refresh in");
        log.info("refresh_token = {}", refresh_token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(Common.GRANT_TYPE, "refresh_token");
        paramMap.put(Common.CLIENT_ID, clientID);
        paramMap.put(Common.CLIENT_SECRET, clientSecret);
        paramMap.put(Common.REFRESH_TOKEN, refresh_token);

        ResponseEntity<Map> getAccessToken = ServletUtils.post(tokenUrl, paramMap, MediaType.APPLICATION_FORM_URLENCODED, Map.class);
        if(getAccessToken.getStatusCodeValue() == 200){
            log.info("status 200 OK");
            Map<String, String> resultBody = getAccessToken.getBody();
            String accessToken = resultBody.get("access_token");
            int expiresIn = Integer.parseInt(resultBody.get("expires_in"));

            log.info("accessToken = {}", accessToken);
            log.info("expiresIn = {}", expiresIn);

            Cookie cookie = new Cookie("access_token", accessToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(expiresIn);

            response.addCookie(cookie);
            response.sendRedirect("/");

        }else{
            //refresh token 만료. 아예 로그인 다시 해야함
            //로그인 화면으로 이동
            return "login page";
        }

        return "index";

    }

    @GetMapping("/naver/logout")
    public void logout(@CookieValue String access_token,
                       HttpServletResponse response,
                       HttpServletRequest request) throws IOException {
        log.info("logout in");
        log.info("access_token = {}", access_token);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(Common.GRANT_TYPE, "delete");
        paramMap.put(Common.CLIENT_ID, clientID);
        paramMap.put(Common.CLIENT_SECRET, clientSecret);
        paramMap.put(Common.ACCESS_TOKEN, access_token);
        paramMap.put("service_provider", "NAVER");

        ResponseEntity<Map> delete = ServletUtils.post(tokenUrl, paramMap, MediaType.APPLICATION_FORM_URLENCODED, Map.class);

        if(delete.getStatusCodeValue() == 200){
            log.info("delete status 200 OK");
            Map<String, String> resultBody = delete.getBody();
            String accessToken = resultBody.get("access_token");
            String result = resultBody.get("result");
            log.info("delete accessToken = {}", accessToken);
            log.info("delete result = {}", result);

            if (result.equals("success")) {
                log.info("delete success");

                Cookie cookie = new Cookie("access_token", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                HttpSession session = request.getSession(false);
                session.invalidate();

                response.sendRedirect("/");
            }else {
                //토큰 만료와 같은 문제로 로그아웃 에러
                //그냥 메인화면으로 이동
                response.sendRedirect("/");
            }
        }

    }

}