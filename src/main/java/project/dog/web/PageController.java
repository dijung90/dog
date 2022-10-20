package project.dog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class PageController {

    @GetMapping("/")
    public String welcome() {

        return "index";
    }
}
