package project.dog.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.dog.domain.Member;
import project.dog.repository.dto.member.MemberLoginDTO;
import project.dog.repository.dto.member.MemberRegisterDTO;
import project.dog.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService service;

    @GetMapping("/register")
    public String register(){

        return "register";
    }

    @PostMapping("/register")
    public void register(MemberRegisterDTO registerDTO,
                         HttpServletResponse response) throws IOException {
        log.info("register DTO = {}", registerDTO.toString());
        boolean result = service.register(registerDTO);
        if(result){
            response.sendRedirect("/login");
        }else {
            response.sendRedirect("/register");
        }
    }

    @GetMapping("/login")
    public String login(){
        log.info("login in GET");

        return "login";
    }

    @PostMapping("/login")
    public String login(MemberLoginDTO loginDTO,
                              HttpServletRequest request) {
        log.info("login in POST");

        boolean isLogin = service.login(loginDTO);

        if (isLogin) {
            Member findMember = service.findById(loginDTO.getId()).orElse(null);
            HttpSession session = request.getSession();
            session.setAttribute("id", findMember.getId());
            session.setAttribute("nickname", findMember.getNickname());
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "index";
    }
}
