package hkmu.comp380f.webApplicationProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PollController {

    @GetMapping("/poll")
    public String poll(Principal principal, ModelMap modelMap) {



        return "/common/poll";
    }
}
