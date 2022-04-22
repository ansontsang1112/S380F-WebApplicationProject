package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
public class UserRepositoryImpl implements UserRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    public static final class UserExtractor implements ResultSetExtractor<List<User>> {

        @Override
        public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            HashMap<String, User> hashMap = new HashMap<>();

                while (resultSet.next()) {
                    String id = resultSet.getString("uid");
                    User user = hashMap.get(id);
                    if (user == null) {
                        user = new User();
                        user.setUid(id);
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setFullName(resultSet.getString("full_name"));
                        user.setPhoneNumber(resultSet.getString("phone_number"));
                        user.setAddress(resultSet.getString("address"));
                        user.setRole(resultSet.getString("role"));
                        hashMap.put(id, user);
                    }
                }

            return new ArrayList<>(hashMap.values());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> queryAllUsers() {
        final String statement = "SELECT * FROM REGISTER_USER";
        return jdbcOperations.query(statement, new UserExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> queryUser(String username) {
        final String statement = "SELECT * FROM REGISTER_USER WHERE username = ?";
        return jdbcOperations.query(statement, new UserExtractor(), username);
    }

    @Override
    @Transactional
    public String add(final User user) {
        final UUID uid = UUID.randomUUID();
        final String statement = "INSERT INTO REGISTER_USER VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, uid.toString());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getFullName());
                preparedStatement.setString(5, user.getPhoneNumber());
                preparedStatement.setString(6, user.getAddress());
                preparedStatement.setString(7, "USER");
                return preparedStatement;
            }
        });

        return user.getUsername();
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public <T> void update(User user, T fieldOld, T fieldNew) {

    }
}
