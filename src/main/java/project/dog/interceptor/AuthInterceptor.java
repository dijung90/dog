package project.dog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import project.dog.util.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        boolean isSnsLogin = SessionUtils.isSnsLogin(request);

        log.info("isSnsLogin ={}", isSnsLogin);

        if(isSnsLogin){
            List<String> cookieValueList = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("access_token"))
                    .sorted()
                    .map(cookie -> cookie.getValue())
                    .collect(Collectors.toList());

            if(!cookieValueList.isEmpty()){
                String accessToken = cookieValueList.get(0);
                log.info("access_token ={}", accessToken);

            }else{
                log.info("access_token null");
                //재발급
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/naver/refresh");
                requestDispatcher.forward(request, response);
                return false;
            }
        }



        //뒤에 추가된 인터셉터가 있을시 HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
