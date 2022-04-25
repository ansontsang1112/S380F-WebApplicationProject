
package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.utils.IterationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private CourseRepository courseRepository;

    @GetMapping(value = {"/", "*", "/home", "/ocs"})
    public String index(ModelMap modelMap, Principal principal) {
        if(principal != null) modelMap.addAttribute("role", userRepository.queryUser(principal.getName()).get(0).getRole());

        List<Object> lecturersList = IterationManager.userIterationByKey(userRepository.queryRelatedUserByType("LECTURER"), "fullname");
        List<Course> coursesObject = courseRepository.queryAllCourses();

        modelMap.addAttribute("lecturersList", lecturersList);
        modelMap.addAttribute("courseObject", coursesObject);
        return "index";
    }

    @GetMapping("/denied")
    public String accessDenied() {
        return "utils/404";
    }

}
