package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {

    @Resource
    public UserRepository userRepository;

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "registerModel", new User());
    }

    @PostMapping("/register")
    public ModelAndView registerEventHandler(@ModelAttribute("registerModel") User user, ModelMap modelMap){
        if(userNullDataChecker(user).isEmpty() && user != null) {
            modelMap.addAttribute("registerUsername", userRepository.add(user));
            return new ModelAndView(new RedirectView("/ocs/login?registerSuccess"));
        } else {
            modelMap.addAttribute("missing", userNullDataChecker(user));
            return new ModelAndView(new RedirectView("/ocs/register?error"));
        }
    }

    public List<Object> userNullDataChecker(User user) {
        List<Object> missingFactors = new ArrayList<>();

        if(user == null) {
            missingFactors.add("object");
        } else if(user.getUsername() == "") {
            missingFactors.add("username");
        } else if (user.getPassword() == "") {
            missingFactors.add("password");
        } else if (user.getFullName() == "") {
            missingFactors.add("full name");
        } else if (user.getPhoneNumber() == "") {
            missingFactors.add("phone number");
        } else if (user.getAddress() == "") {
            missingFactors.add("address");
        }

        System.out.println(missingFactors);
        return missingFactors;
    }
}
