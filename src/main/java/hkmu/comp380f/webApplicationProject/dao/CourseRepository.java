package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Course;

import java.util.List;

public interface CourseRepository {
    public List<Course> queryAllCourses();
    public List<Course> queryCoursesByID(String uid);
    public List<Course> queryCoursesByLecture(String lecture);
    public List<Course> queryCoursesByStudent(String student);

    public String add(Course course);
    public String delete(Course course);
    public <T> String update(Course course, T fieldOld, T fieldNew);
}
