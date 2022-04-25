package hkmu.comp380f.webApplicationProject.component;

import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataTransformerComponent {
    @Resource
    UserRepository userRepository;

    public List<User> stringArrayToUserList(String [] usernames) {
        List<User> userList = new ArrayList<>();
        if(usernames.length > 0) {
            for(String username : usernames) {
                userList.add(userRepository.queryUser(username).get(0));
            }
        }

        return userList;
    }

    public List<Course> courseListModifier(List<Course> courseList) {
        List<Course> newCourseList = new ArrayList<>();

        for(Course course : courseList) {
            course.setLecturesObject(stringArrayToUserList(course.getLectures()));
            course.setStudentsObject(stringArrayToUserList(course.getStudentList()));
            newCourseList.add(course);
        }

        return newCourseList;
    }

    public Course courseModifier(String courseID, String courseName, String [] lecturers, String [] students) {
        Course course = new Course();
        course.setCourseID(courseID);
        course.setCourseName(courseName);
        course.setSerializedStudents(serialization(Arrays.asList(students)));
        course.setSerializedLectures(serialization(Arrays.asList(lecturers)));

        return course;
    }

    public Course courseModifier(Course course) {
        course.setSerializedStudents(serialization(Arrays.asList(course.getStudentList())));
        course.setSerializedLectures(serialization(Arrays.asList(course.getLectures())));

        return course;
    }

    public <T extends List> String serialization(T list) {
        String serialized = "";

        for(int i = 0; i < list.size(); i++) {
            if(i == list.size()-1) {
                serialized += list.get(i).toString();
            } else {
                serialized += (list.get(i).toString() + ", ");
            }
        }

        return serialized;
    }

    public <T> String serialization(T [] array) {
        String serialized = "";

        for(int i = 0; i < array.length; i++) {
            if(i == array.length-1) {
                serialized += array[i].toString();
            } else {
                serialized += (array[i].toString() + ", ");
            }
        }

        return serialized;
    }
}
