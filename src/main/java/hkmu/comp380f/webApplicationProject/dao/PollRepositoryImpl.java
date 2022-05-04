package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Poll;
import hkmu.comp380f.webApplicationProject.model.PollResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class PollRepositoryImpl implements PollRepository{
    private JdbcOperations jdbcOperations;

    @Autowired
    PollRepositoryImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    public static final class PollExtractor implements ResultSetExtractor<List<Poll>> {

        @Override
        public List<Poll> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            HashMap<String, Poll> hashMap = new HashMap<>();

            while (resultSet.next()) {
                String id = resultSet.getString("uid");
                Poll poll = hashMap.get(id);
                if (poll == null) {
                    poll = new Poll();
                    poll.setUid(id);
                    poll.setQuestion(resultSet.getString("question"));
                    poll.setChoice1(resultSet.getString("choice_1"));
                    poll.setChoice2(resultSet.getString("choice_2"));
                    poll.setChoice3(resultSet.getString("choice_3"));
                    poll.setChoice4(resultSet.getString("choice_4"));
                    poll.setPollID(resultSet.getString("poll_id"));
                    hashMap.put(id, poll);
                }
            }

            return new ArrayList<>(hashMap.values());
        }
    }

    public static final class PollResultExtractor implements ResultSetExtractor<List<PollResult>> {

        @Override
        public List<PollResult> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            HashMap<String, PollResult> hashMap = new HashMap<>();

            while (resultSet.next()) {
                String id = resultSet.getString("uid");
                PollResult pollResult = hashMap.get(id);
                if (pollResult == null) {
                    pollResult = new PollResult();
                    pollResult.setUid(id);
                    pollResult.setUserID(resultSet.getString("user_id"));
                    pollResult.setPollID(resultSet.getString("poll_id"));
                    pollResult.setChoice(resultSet.getInt("choice"));
                    pollResult.setTimestamp(resultSet.getTimestamp("timestamp"));
                    hashMap.put(id, pollResult);
                }
            }

            return new ArrayList<>(hashMap.values());
        }
    }

    @Override
    @Transactional
    public List<Poll> queryAllPoll() {
        final String statement = "SELECT * FROM poll_registry";
        return jdbcOperations.query(statement, new PollExtractor());
    }

    @Override
    @Transactional
    public List<Poll> queryPollByID(String uid) {
        final String statement = "SELECT * FROM poll_registry WHERE uid = ?";
        return jdbcOperations.query(statement, new PollExtractor(), uid);
    }

    @Override
    @Transactional
    public List<Poll> queryPollByUserID(String username) {
        return null;
    }

    @Override
    @Transactional
    public Map<String, Integer> queryPollCountByQuestion(String Question) {
        return null;
    }

    @Override
    @Transactional
    public List<PollResult> queryPollResultByUser(String username) {
        final String statement = "SELECT * FROM poll_result_registry WHERE user_id = ?";
        return jdbcOperations.query(statement, new PollResultExtractor(), username);
    }

    @Override
    @Transactional
    public List<PollResult> queryPollResultByPollID(String pollID) {
        final String statement = "SELECT * FROM poll_result_registry WHERE poll_id = ?";
        return jdbcOperations.query(statement, new PollResultExtractor(), pollID);
    }

    @Override
    public Map<String, ?> isUserVoteQuestionBefore(String username, String id) {
        final String statement = "SELECT * FROM poll_result_registry WHERE user_id = ? AND poll_id = ?";
        List<PollResult> resultSet = jdbcOperations.query(statement, new PollResultExtractor(), username, id);

        Map<String, ? super Object> resultMap = new HashMap<>();

        if(resultSet.isEmpty()) {
            resultMap.put("result", "N");
        } else {
            resultMap.put("result", "Y");
            resultMap.put("content", resultSet.get(0).getChoice());
        }

        return resultMap;
    }

    @Override
    public void addPoll(Poll poll) {

    }

    @Override
    public String delPoll(String str) {
        return null;
    }

    @Override
    public <T, V, K> String updatePoll(T index, V newValue, K key) {
        return null;
    }

    @Override
    @Transactional
    public <T, V, K> void addPollResult(T userID, V pollID, K choice) {
        final String statement = "INSERT INTO poll_result_registry VALUES (?, ?, ?, ?, ?)";
        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, UUID.randomUUID().toString());
                preparedStatement.setString(2, userID.toString());
                preparedStatement.setString(3, pollID.toString());
                preparedStatement.setInt(4, Integer.parseInt(choice.toString()));
                preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                return preparedStatement;
            }
        });
    }

    @Override
    @Transactional
    public <T, V, K> String updatePollResult(T index, V newValue, K key) {
        final String statement = "UPDATE poll_result_registry SET " + index.toString() + "= ? WHERE uid = ?";
        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, newValue.toString());
                preparedStatement.setString(2, key.toString());
                return preparedStatement;
            }
        });

        return newValue.toString();
    }
}
