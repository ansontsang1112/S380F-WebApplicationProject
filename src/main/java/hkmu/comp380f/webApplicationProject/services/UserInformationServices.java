package hkmu.comp380f.webApplicationProject.services;

import hkmu.comp380f.webApplicationProject.component.DataTransformerComponent;
import hkmu.comp380f.webApplicationProject.dao.CourseRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

@Service
public class UserInformationServices {
    @Resource
    private UserRepository userRepository;
    @Resource
    private CourseRepository courseRepository;
    @Autowired
    private DataTransformerComponent dataTransformerComponent;

    public User userObjectMapper(Principal principal) {
        String username = principal.getName();
        User user = userRepository.queryUser(username).get(0);
        return user;
    }

    public List<Course> courseList(Principal principal) {
        User userObject = userRepository.queryUser(principal.getName()).get(0);
        List<Course> courseList = courseRepository.queryCoursesByStudent(userObject);
        courseList = dataTransformerComponent.courseListModifier(courseList);

        for(Course course : courseList) {
            course.setSerializedLectures(dataTransformerComponent.serialization(course.getLectures()));
            course.setSerializedStudents(dataTransformerComponent.serialization(course.getStudentList()));
        }

        if(courseList.isEmpty()) {
            return null;
        } else {
            return courseList;
        }
    }
}
