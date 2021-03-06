package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.CourseFile;
import hkmu.comp380f.webApplicationProject.model.User;
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

    public static final class CourseFileExtractor implements ResultSetExtractor<List<CourseFile>> {
        @Override
        public List<CourseFile> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            HashMap<String, CourseFile> hashMap = new HashMap<>();
            while (resultSet.next()) {
                String id = resultSet.getString("uid");
                CourseFile courseFile = hashMap.get(id);
                if (courseFile == null) {
                    courseFile = new CourseFile();
                    courseFile.setUid(id);
                    courseFile.setCourseID(resultSet.getString("course_id"));
                    courseFile.setFileType(resultSet.getString("file_type"));
                    courseFile.setDisplay(resultSet.getBoolean("display"));
                    courseFile.setTimestamp(resultSet.getTimestamp("timestamp"));
                    courseFile.setSaveURL(resultSet.getString("url"));
                    hashMap.put(id, courseFile);
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
    @Transactional
    public List<Course> queryCoursesByLecture(User lecture) {
        final String statement = "SELECT * FROM COURSE_REGISTRY";
        List<Course> courseList = new ArrayList<>();
        List<Course> allCourse  = jdbcOperations.query(statement, new CourseExtractor());
        for (Course course : allCourse) {
            if(Arrays.asList(course.getLectures()).contains(lecture.getUsername())) {
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
    public <I> List<CourseFile> queryCourseFileByCourseID(I courseID) {
        final String statement = "SELECT * FROM course_file_registry WHERE course_id = ? AND display = true";
        return jdbcOperations.query(statement, new CourseFileExtractor(), courseID);
    }

    @Override
    public void addFile(CourseFile courseFile) {
        final String statement = "INSERT INTO COURSE_FILE_REGISTRY VALUES (?, ?, ?, ?, ?, ?)";

        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, courseFile.getUid());
                preparedStatement.setString(2, courseFile.getCourseID());
                preparedStatement.setString(3, courseFile.getFileType());
                preparedStatement.setString(4, courseFile.getSaveURL());
                preparedStatement.setTimestamp(5, courseFile.getTimestamp());
                preparedStatement.setBoolean(6, courseFile.isDisplay());
                return preparedStatement;
            }
        });
    }

    @Override
    public <I> void delFile(I fileID) {
        final String statement = "UPDATE COURSE_FILE_REGISTRY SET display = false WHERE uid = ?";

        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, fileID.toString());
                return preparedStatement;
            }
        });
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
