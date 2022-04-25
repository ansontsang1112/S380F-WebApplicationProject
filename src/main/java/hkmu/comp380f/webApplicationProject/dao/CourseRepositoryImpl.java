package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.component.DataTransformerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
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
                    course.setId(id);
                    course.setCourseID(resultSet.getString("course_id"));
                    course.setCourseName(resultSet.getString("course_name"));
                    course.setLectures(resultSet.getString("lectures").split(", "));
                    course.setStudentList(resultSet.getString("student_list").split(", "));
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
    @Transactional(readOnly = true)
    public List<Course> queryCoursesByID(String uid) {
        final String statement = "SELECT * FROM COURSE_REGISTRY WHERE uid = ?";
        return jdbcOperations.query(statement, new CourseExtractor(), uid);
    }

    @Override
    @Transactional
    public List<Course> queryCoursesByCourseID(String courseID) {
        final String statement = "SELECT * FROM COURSE_REGISTRY WHERE course_id = ?";
        return jdbcOperations.query(statement, new CourseExtractor(), courseID);
    }

    @Override
    public List<Course> queryCoursesByLecture(User lecture) {
        final String statement = "SELECT * FROM COURSE_REGISTRY";
        List<Course> courseList = new ArrayList<>();
        List<Course> allCourse  = jdbcOperations.query(statement, new CourseExtractor());
        for (Course course : allCourse) {
            if(Arrays.asList(course.getStudentList()).contains(lecture.getUsername())) {
                courseList.add(course);
            }
        }
        return courseList;
    }

    @Override
    @Transactional
    public List<Course> queryCoursesByStudent(User student) {
        final String statement = "SELECT * FROM COURSE_REGISTRY";
        List<Course> courseList = new ArrayList<>();
        List<Course> allCourse  = jdbcOperations.query(statement, new CourseExtractor());
        for (Course course : allCourse) {
            if(Arrays.asList(course.getStudentList()).contains(student.getUsername())) {
                courseList.add(course);
            }
        }
        return courseList;
    }

    @Override
    @Transactional
    public String add(Course course) {
        final String statement = "INSERT INTO COURSE_REGISTRY VALUES (?, ?, ?, ?, ?)";
        UUID id = UUID.randomUUID();

        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, id.toString());
                preparedStatement.setString(2, course.getCourseID());
                preparedStatement.setString(3, course.getCourseName());
                preparedStatement.setString(4, course.getSerializedLectures());
                preparedStatement.setString(5, course.getSerializedStudents());
                return preparedStatement;
            }
        });
        return course.getCourseID();
    }

    @Override
    @Transactional
    public <T> void delete(T key) {
        final String statement = "DELETE FROM COURSE_REGISTRY WHERE uid = ?";
        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, key.toString());
                return preparedStatement;
            }
        });
    }

    @Override
    @Transactional
    public <T> void update(T fieldOld, T fieldNew, T key) {
        final String statement = "UPDATE COURSE_REGISTRY SET " + fieldOld.toString() + "=? WHERE uid = ?";
        jdbcOperations.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(statement);
            preparedStatement.setString(1, fieldNew.toString());
            preparedStatement.setString(2, key.toString());
            return preparedStatement;
        });
    }
}
