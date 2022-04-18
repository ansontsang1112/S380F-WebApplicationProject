
package hkmu.comp380f.s380f_webapplicationproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    
    @GetMapping
    public String index() {
        return "redirect:/nb/index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
}
