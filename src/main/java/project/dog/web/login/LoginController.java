package project.dog.web.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(){



        return "";
    }

    @GetMapping("/register")
    public String register(){

        return "register";
    }
}
