package hkmu.comp380f.s380f_webapplicationproject.dao;

import hkmu.comp380f.s380f_webapplicationproject.model.Course;
import java.util.ArrayList;

public interface CourseRepository {
    
    public String createCourse(Course course);
    public <T> boolean updateCourse(String uid, T oldValue, T newValue);
    public <T> boolean deleteCourse(String uid, T oldValue, T newValue);
    
    public ArrayList<Course> getCourses();
    public ArrayList<Course> getCourseByID(String uid);
    
}
