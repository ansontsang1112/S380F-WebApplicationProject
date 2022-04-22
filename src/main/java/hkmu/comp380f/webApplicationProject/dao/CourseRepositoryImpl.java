package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseRepositoryImpl implements CourseRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    CourseRepositoryImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    public static final class CourseExtractor implements ResultSetExtractor<List<Course>> {

        @Override
        public List<Course> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            HashMap<String, Course> hashMap = new HashMap<>();

            while (resultSet.next()) {
                String id = resultSet.getString("uid");
                Course course = hashMap.get(id);
                if (course == null) {
                    course = new Course();
                    course.setCourseID(id);
                    course.setCourseID(resultSet.getString("course_id"));
                    course.setCourseName(resultSet.getString("course_name"));
                    course.setLectures(resultSet.getString("lectures").split(", "));
                    course.setStudentList(resultSet.getString("students").split(", "));
                    hashMap.put(id, course);
                }
            }

            return new ArrayList<>(hashMap.values());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> queryAllCourses() {
        final String statement = "SELECT * FROM COURSE_REGISTRY";
        return jdbcOperations.query(statement, new CourseExtractor());
    }

    @Override
    public List<Course> queryCoursesByID(String uid) {
        final String statement = "SELECT * FROM COURSE_REGISTRY WHERE uid = ?";
        return jdbcOperations.query(statement, new CourseExtractor(), uid);
    }

    @Override
    public List<Course> queryCoursesByLecture(String lecture) {
        final String statement = "SELECT * FROM COURSE_REGISTRY WHERE lectures LIKE %?%";
        return jdbcOperations.query(statement, new CourseExtractor(), lecture);
    }

    @Override
    public List<Course> queryCoursesByStudent(String student) {
        final String statement = "SELECT * FROM COURSE_REGISTRY WHERE student = LIKE %?%";
        return jdbcOperations.query(statement, new CourseExtractor(), student);
    }

    @Override
    public String add(Course course) {
        return null;
    }

    @Override
    public String delete(Course course) {
        return null;
    }

    @Override
    public <T> String update(Course course, T fieldOld, T fieldNew) {
        return null;
    }
}
