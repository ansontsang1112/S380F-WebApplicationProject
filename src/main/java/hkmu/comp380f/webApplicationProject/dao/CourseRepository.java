package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;

import java.util.List;

public interface CourseRepository {
    public List<Course> queryAllCourses();
    public List<Course> queryCoursesByID(String uid);
    public List<Course> queryCoursesByCourseID(String courseID);
    public List<Course> queryCoursesByLecture(User lecture);
    public List<Course> queryCoursesByStudent(User student);

    public String add(Course course);
    public <T> void delete(T key);
    public <T> void update(T fieldOld, T fieldNew, T key);
}
