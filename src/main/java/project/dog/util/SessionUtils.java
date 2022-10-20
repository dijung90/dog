package project.dog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class SessionUtils {

    public static boolean isLogin(HttpServletRequest request){
        boolean isLogin = false;

        HttpSession session = request.getSession();
        if(!ObjectUtils.isEmpty(session.getAttribute("id"))){
            isLogin = true;
        }else{
            session.invalidate();
        }

        return isLogin;
    }

    public static boolean isSnsLogin(HttpServletRequest request){
        boolean isSnsLogin = false;

        HttpSession session = request.getSession();
        if(!ObjectUtils.isEmpty(session.getAttribute("snsId"))){
            isSnsLogin = true;
        }else{
            session.invalidate();
        }

        return isSnsLogin;
    }
}
