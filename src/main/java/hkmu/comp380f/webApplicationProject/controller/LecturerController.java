package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.component.DataTransformerComponent;
import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class LecturerController {

    public class NullHandler {
        public String nullHandle(String[] strArray, String uid, boolean isStudent) {
            if (strArray.length == 0) {
                if(!isStudent) {
                    return dataTransformerComponent.serialization(courseRepository.queryCoursesByID(uid).get(0).getLectures());
                } else {
                    return dataTransformerComponent.serialization(courseRepository.queryCoursesByID(uid).get(0).getStudentList());
                }
            } else {
                return dataTransformerComponent.serialization(strArray);
            }
        }
    }

    @Resource
    private CourseRepository courseRepository;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private DataTransformerComponent dataTransformerComponent;

    @GetMapping("/lecturer")
    public String lecturerDashboard(ModelMap modelMap) {
        List<Course> courseList = dataTransformerComponent.courseListModifier(courseRepository.queryAllCourses());
        modelMap.addAttribute("courseObject", courseList);
        modelMap.addAttribute("studentObject", userRepository.queryAllUsers());
        return "/lecturer/index";
    }

    @GetMapping("/lecturer/cms")
    public String courseManagement(ModelMap modelMap,
                                   @RequestParam Optional<String> set,
                                   @RequestParam Optional<String> cid) {
        List<Course> courseList = dataTransformerComponent.courseListModifier(courseRepository.queryAllCourses());
        modelMap.addAttribute("courseObject", courseList);
        modelMap.addAttribute("lecturerList", userRepository.queryRelatedUserByType("LECTURER"));
        modelMap.addAttribute("studentList", userRepository.queryRelatedUserByType("USER"));

        if(set.isPresent()) {
            if (cid.isPresent() && cid.get() != "") {
                Course modifiedCourse = dataTransformerComponent.courseModifier(courseRepository.queryCoursesByID(cid.get()).get(0));
                switch (set.orElseGet(()->"default")) {
                    case "modify":
                        if(cid.isPresent()) modelMap.addAttribute("modifyCourseObject", modifiedCourse);
                        break;
                    case "delete":
                        if(cid.isPresent()) modelMap.addAttribute("deleteCourseObject", modifiedCourse);
                        break;
                    default:
                }
            }
        }

        return "lecturer/cms";
    }

    @PostMapping("/lecturer/cms")
    public String courseEventHandler(ModelMap modelMap,
                                     @RequestParam Optional<String> action,
                                     @RequestParam Optional<String []> students,
                                     @RequestParam Optional<String []> lecturers,
                                     @RequestParam Optional<String> courseID,
                                     @RequestParam Optional<String> courseName,
                                     @RequestParam Optional<String> key) {
        String courseIDView = null;
        if(action.isPresent()) {
            switch (action.get()) {
                case "add":
                    Course course = dataTransformerComponent.courseModifier(courseID.get(), courseName.get(), lecturers.get(), students.get());
                    modelMap.addAttribute("newCourseAdded",  "Course (" + courseRepository.add(course) + ") add to database successfully");
                    break;

                case "update":
                    if(key.isPresent()) {
                        courseIDView = courseRepository.queryCoursesByID(key.get()).get(0).getCourseID();
                        students.ifPresent(s -> courseRepository.update("student_list", new NullHandler().nullHandle(students.get(), key.get(), true) , key.get()));
                        lecturers.ifPresent(s -> courseRepository.update("lectures", new NullHandler().nullHandle(lecturers.get(), key.get(), false) , key.get()));
                        courseName.ifPresent(s -> courseRepository.update("course_name", courseName.get(), key.get()));
                    }
                    break;

                case "delete":
                    if(key.isPresent()) {
                         courseIDView = courseRepository.queryCoursesByID(key.get()).get(0).getCourseID();
                         courseRepository.delete(key.get());
                    }

            }

            if(key.isPresent()) modelMap.addAttribute("OK", "Course (" + courseIDView + ") " + action.get() + " operated successfully.");

        }

        List<Course> courseList = dataTransformerComponent.courseListModifier(courseRepository.queryAllCourses());
        modelMap.addAttribute("courseObject", courseList);
        modelMap.addAttribute("lecturerList", userRepository.queryRelatedUserByType("LECTURER"));
        modelMap.addAttribute("studentList", userRepository.queryRelatedUserByType("USER"));
        return "lecturer/cms";
    }

    @GetMapping("/lecturer/sms")
    public String studentManagement(ModelMap modelMap, @RequestParam Optional<String> set, @RequestParam Optional<String> sid) {
        if(set.isPresent()) {
            switch (set.get()) {
                case "modify":
                    if(sid.isPresent()) modelMap.addAttribute("modifyStudentObject", userRepository.queryUserByID(sid.get()).get(0));
                    break;

                case "delete":
                    if(sid.isPresent()) modelMap.addAttribute("deleteStudentObject", userRepository.queryUserByID(sid.get()).get(0));
                    break;

                default:
            }
        }

        modelMap.addAttribute("studentObject", userRepository.queryAllUsers());
        return "lecturer/sms";
    }

    @PostMapping("/lecturer/sms")
    public String studentEventHandler(ModelMap modelMap,
                                      @RequestParam Optional<String> action,
                                      @RequestParam Optional<String> fullName,
                                      @RequestParam Optional<String> phoneNumber,
                                      @RequestParam Optional<String> address,
                                      @RequestParam String key) {

        if(action.isPresent()) {
            User userObject = userRepository.queryUserByID(key).get(0);

            switch (action.get()) {
                case "update":
                    fullName.ifPresent(s -> userRepository.update("full_name", s, key));
                    phoneNumber.ifPresent(s -> userRepository.update("phone_number", s, key));
                    address.ifPresent(s -> userRepository.update("address", s, key));
                    break;

                case "delete":
                    userRepository.delete(key);
                    break;
            }

            modelMap.addAttribute("OK",  userObject.getUsername() + "'s information " + action.get() + " successfully");
        } else {
            modelMap.addAttribute("error", "Fail to " + action.get() + "" + userRepository.queryUserByID(key).get(0).getUsername() + "'s information");
        }

        modelMap.addAttribute("studentObject", userRepository.queryAllUsers());
        return "lecturer/sms";
    }
}
