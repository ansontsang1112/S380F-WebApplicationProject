package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.component.DataTransformerComponent;
import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.UserInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private CourseRepository courseRepository;
    @Autowired
    private DataTransformerComponent dataTransformerComponent;
    @Autowired
    private UserInformationServices userInformationServices;


    @GetMapping("/student")
    public String studentDashboard(Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("userObject",  userInformationServices.userObjectMapper(principal));
        modelMap.addAttribute("courseObjectByUser", userInformationServices.courseList(principal));

        return "/student/index";
    }

    @PostMapping("/student")
    public String studentEventHandler(Principal principal, ModelMap modelMap,
                                      @RequestParam Optional<String> fullName,
                                      @RequestParam Optional<String> phoneNumber,
                                      @RequestParam Optional<String> address,
                                      @RequestParam Optional<String> oPassword,
                                      @RequestParam Optional<String> nPassword,
                                      @RequestParam String key,
                                      @RequestParam Optional<String> action) {

        if(action.isPresent()) {
            String actionType = null;
            try {
                switch (action.get()) {
                    case "updateInfo":
                        actionType = "personal information";
                        fullName.ifPresent(s -> userRepository.update("full_name", fullName.get(), key));
                        phoneNumber.ifPresent(s -> userRepository.update("phone_number", phoneNumber.get(), key));
                        address.ifPresent(s -> userRepository.update("address", address.get(), key));
                        break;

                    case "updatePassword":
                        String ocPassword = userRepository.queryUser(principal.getName()).get(0).getPassword();

                        if (ocPassword.equals(oPassword.get())) {
                            actionType = "password";
                            nPassword.ifPresent(s -> userRepository.update("password", nPassword.get(), key));
                        } else {
                            throw new Exception("Password checking fail");
                        }
                        break;
                }
                modelMap.addAttribute("OK", "Your " + actionType + " updated successfully");
            } catch (Exception e) {
                modelMap.addAttribute("error", "Exception: " + e.getMessage());
            }
        }

        modelMap.addAttribute("courseObjectByUser", userInformationServices.courseList(principal));
        modelMap.addAttribute("userObject", userInformationServices.userObjectMapper(principal));
        return "/student/index";
    }
}
