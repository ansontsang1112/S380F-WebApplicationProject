package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.Course;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
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
                    comment.setCourseID(resultSet.getString("course_id"));
                    comment.setTimestamp(resultSet.getTimestamp("timestamp"));
                    comment.setLastEdit(resultSet.getTimestamp("lastedit"));
                    comment.setDeleted(resultSet.getBoolean("deleted"));
                    comment.setPage(resultSet.getString("page"));
                    hashMap.put(id, comment);
                }
            }
            return new ArrayList<>(hashMap.values());
        }
    }

    @Override
    public List<Comment> queryAllComment(boolean withDeleted, String page) {
        String statement;
        if(withDeleted) {
            statement = "SELECT * FROM COMMENT_REGISTRY WHERE page = ? ORDER BY timestamp DESC";
        } else {
            statement = "SELECT * FROM COMMENT_REGISTRY WHERE page = ? AND deleted = false ORDER BY timestamp DESC ";
        }

        return jdbcOperations.query(statement, new CommentExtractor(), page);
    }

    @Override
    public List<Comment> queryCommentsByCourseId(String courseID, boolean withDeleted) {
        String statement;

        if(withDeleted) {
            statement = "SELECT * FROM COMMENT_REGISTRY WHERE course_id = ? AND page = 'course' ORDER BY timestamp DESC";
        } else {
            statement = "SELECT * FROM COMMENT_REGISTRY WHERE course_id = ? AND deleted = false AND page = 'course' ORDER BY timestamp DESC ";
        }

        return jdbcOperations.query(statement, new CommentExtractor(), courseID);
    }

    @Override
    public List<Comment> queryCommentsByPollID(String uid, boolean withDeleted) {
        String statement;

        if(withDeleted) {
            statement = "SELECT * FROM COMMENT_REGISTRY WHERE course_id = ? AND page = 'poll' ORDER BY timestamp DESC";
        } else {
            statement = "SELECT * FROM COMMENT_REGISTRY WHERE course_id = ? AND deleted = false AND page = 'poll' ORDER BY timestamp DESC ";
        }

        return jdbcOperations.query(statement, new CommentExtractor(), uid);
    }

    @Override
    public List<Comment> queryCommentsByUser(String username, String page) {
        final String statement = "SELECT * FROM COMMENT_REGISTRY WHERE user_id = ? AND page = ? ORDER BY timestamp DESC ";
        return jdbcOperations.query(statement, new CommentExtractor(), username, page);
    }

    @Override
    public String add(Comment comment) {
        final String statement = "INSERT INTO COMMENT_REGISTRY VALUES (?, ?, ?, ?, ?, ?, false, ?)";

        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, comment.getMessage_id());
                preparedStatement.setString(2, comment.getUserID());
                preparedStatement.setString(3, comment.getMessage());
                preparedStatement.setString(4, comment.getCourseID());
                preparedStatement.setTimestamp(5, comment.getTimestamp());
                preparedStatement.setTimestamp(6, comment.getLastEdit());
                preparedStatement.setString(7, comment.getPage());
                return preparedStatement;
            }
        });

        return comment.getMessage_id();
    }

    @Override
    public <T> String delete(T key) {
        final String statement = "UPDATE COMMENT_REGISTRY SET deleted = true WHERE message_id = ?";

        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, key.toString());
                return preparedStatement;
            }
        });

        return key.toString();
    }
}
