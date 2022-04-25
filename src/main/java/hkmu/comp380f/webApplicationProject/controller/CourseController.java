package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.UserInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CourseController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private CourseRepository courseRepository;
    @Autowired
    private UserInformationServices userInformationServices;

    @GetMapping("/course")
    public ModelAndView course(Principal principal, ModelMap modelMap,
                               @RequestParam Optional<String> courseSelected) {
        ModelAndView modelAndView = new ModelAndView("/common/course");
        User user = userRepository.queryUser(principal.getName()).get(0);

        modelMap.addAttribute("courseObjectByUser", userInformationServices.courseList(principal));
        modelMap.addAttribute("role", user.getRole());

        if(courseSelected.isPresent()) {
            Course course = courseRepository.queryCoursesByCourseID(courseSelected.get()).get(0);
            modelAndView.addObject("courseRequestedByUser", course);
        }
        return modelAndView;
    }
}
