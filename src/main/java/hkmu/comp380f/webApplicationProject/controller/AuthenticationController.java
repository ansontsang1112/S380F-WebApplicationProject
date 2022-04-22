package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, authentication);
        }

        return new ModelAndView(new RedirectView("/ocs/login?logout"));
    }

    @GetMapping("/student")
    public String studentDashboard() {
        return "/student/index";
    }

    @GetMapping("/lecturer")
    public String lecturerDashboard() {
        return "/lecturer/index";
    }
}
