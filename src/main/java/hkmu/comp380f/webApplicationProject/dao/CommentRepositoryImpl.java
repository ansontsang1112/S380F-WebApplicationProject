package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    CommentRepositoryImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    public static final class CommentExtractor implements ResultSetExtractor<List<Comment>> {
        @Override
        public List<Comment> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            HashMap<String, Comment> hashMap = new HashMap<>();

            while (resultSet.next()) {
                String id = resultSet.getString("message_id");
                Comment comment = hashMap.get(id);
                if (comment == null) {
                    comment = new Comment();
                    comment.setMessage_id(id);
                    comment.setUserID(resultSet.getString("user_id"));
                    comment.setMessage(resultSet.getString("message"));
                    comment.setTimestamp(resultSet.getTimestamp("timestamp"));
                    hashMap.put(id, comment);
                }
            }
            return new ArrayList<>(hashMap.values());
        }
    }

    @Override
    public List<Comment> queryAllComment() {
        return null;
    }

    @Override
    public List<Comment> queryCommentsByCourseId(String courseID) {
        return null;
    }

    @Override
    public List<Comment> queryCommentsByStudent(User user) {
        return null;
    }

    @Override
    public String add(Comment comment) {
        return null;
    }

    @Override
    public <T> String update(T fieldOld, T fieldNew, T key) {
        return null;
    }

    @Override
    public <T> String delete(T key) {
        return null;
    }
}
