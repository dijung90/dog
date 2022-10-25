package project.dog.web.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.dog.domain.SnsMember;
import project.dog.repository.dto.member.SnsMemberRegisterDTO;
import project.dog.service.member.SnsMemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SnsMemberRegisterController {

    private final SnsMemberService service;

    @GetMapping("/snsRegister")
    public String snsRegister(){

        return "snsRegister";
    }

    @PostMapping("/snsRegister")
    public String snsRegister(SnsMemberRegisterDTO registerDTO,
                              HttpServletRequest request,
                              @SessionAttribute String snsId,
                              @SessionAttribute String refreshToken){

        registerDTO.setSnsId(snsId);
        registerDTO.setRefreshToken(refreshToken);

        boolean result = service.register(registerDTO);
        if (result) {
            SnsMember findMember = service.findById(snsId);
            HttpSession session = request.getSession();
            session.setAttribute("snsId", findMember.getSnsId());
            session.setAttribute("nickname", findMember.getNickname());
            session.removeAttribute("refreshToken");
            
            return "redirect:/";
        }
        return "redirect:/snsRegister";
    }
}
