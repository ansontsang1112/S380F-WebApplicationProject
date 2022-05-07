package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.CommentRepository;
import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.CourseFile;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.UserInformationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CourseController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private CommentRepository commentRepository;
    @Autowired
    private UserInformationServices userInformationServices;

    private final String PAGE = "course";

    @GetMapping("/course")
    public ModelAndView course(Principal principal, ModelMap modelMap,
                               @RequestParam Optional<String> courseSelected,
                               @RequestParam Optional<String> action,
                               @RequestParam Optional<String> fid) {
        ModelAndView modelAndView = new ModelAndView("/common/course");
        User user = userRepository.queryUser(principal.getName()).get(0);

        modelMap.addAttribute("courseObjectByUser", userInformationServices.courseList(principal));
        modelMap.addAttribute("role", user.getRole());
        modelMap.addAttribute("userObject", user);

        if(courseSelected.isPresent()) {
            Course course = courseRepository.queryCoursesByCourseID(courseSelected.get()).get(0);
            List<Comment> commentListForCourse = (commentRepository.queryCommentsByCourseId(courseSelected.get(), false).isEmpty()) ? new ArrayList<>() : commentRepository.queryCommentsByCourseId(courseSelected.get(), false);
            List<Comment> commentsFromUser = (commentRepository.queryCommentsByUser(principal.getName(), PAGE).isEmpty() ? new ArrayList<>() : commentRepository.queryCommentsByUser(principal.getName(), PAGE));
            List<CourseFile> filesByCourse = courseRepository.queryCourseFileByCourseID(courseSelected.get());

            modelMap.addAttribute("courseFiles", filesByCourse);
            modelMap.addAttribute("courseComments", commentListForCourse);
            modelMap.addAttribute("commentsFromUser", commentsFromUser);
            modelAndView.addObject("courseRequestedByUser", course);
        }

        if(action.isPresent()) {
            switch (action.get()) {
                case "remove":
                    courseRepository.delFile(fid.get());
                    break;
            }
        }


        return modelAndView;
    }

    @PostMapping("/course")
    public ModelAndView courseEventHandler(Principal principal, ModelMap modelMap,
                                           @RequestParam Optional<String> courseID,
                                           @RequestParam Optional<String> message,
                                           @RequestParam Optional<String> action,
                                           @RequestParam Optional<String> messageId) {
        User user = userRepository.queryUser(principal.getName()).get(0);
        modelMap.addAttribute("courseObjectByUser", userInformationServices.courseList(principal));
        modelMap.addAttribute("role", user.getRole());
        modelMap.addAttribute("userObject", user);

        ModelAndView modelAndView = new ModelAndView("/common/course");

        if(courseID.isPresent()) {
            if (action.isPresent()) {
                try {
                    switch (action.get()) {
                        case "add":
                            UUID messageID = UUID.randomUUID();
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                            if (!message.isPresent()) throw new NullPointerException("Message is not present!");
                            Comment comment = new Comment(messageID.toString(), user.getUsername(), message.get(), courseID.get(), timestamp, timestamp, false, PAGE);
                            modelMap.addAttribute("OK", "Comment (" + commentRepository.add(comment) + ")" + action.get() + " successfully");
                            break;

                        case "delete":
                            if(messageId.isPresent()) commentRepository.delete(messageId.get());
                            modelMap.addAttribute("OK", "Comment " + action.get() + " successfully");
                            break;
                    }
                } catch (Exception e) {
                    modelMap.addAttribute("error", e.getMessage());
                }
            }

            Course course = courseRepository.queryCoursesByCourseID(courseID.get()).get(0);
            List<Comment> commentListForCourse = (commentRepository.queryCommentsByCourseId(courseID.get(), false).isEmpty()) ? new ArrayList<>() : commentRepository.queryCommentsByCourseId(courseID.get(), false);
            List<Comment> commentsFromUser = (commentRepository.queryCommentsByUser(principal.getName(), PAGE).isEmpty() ? new ArrayList<>() : commentRepository.queryCommentsByUser(principal.getName(), PAGE));
            List<CourseFile> filesByCourse = courseRepository.queryCourseFileByCourseID(courseID.get());

            modelMap.addAttribute("courseFiles", filesByCourse);
            modelMap.addAttribute("courseComments", commentListForCourse);
            modelMap.addAttribute("commentsFromUser", commentsFromUser);
            modelMap.addAttribute("courseRequestedByUser", course);
            modelAndView.addObject("courseSelected", courseID.get());
        }
        return modelAndView;
    }
}
