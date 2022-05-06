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
                    pollResult.setReplaced(resultSet.getBoolean("replaced"));
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
    public Map<String, Integer> queryPollCountByQuestion(String question) {
        Map<String, Integer> resultSet = new HashMap<>();
        int [] counter = new int[] {0,0,0,0};

        final String requestPollStatement = "SELECT * FROM poll_registry WHERE question = ?";
        Poll requestedPoll = jdbcOperations.query(requestPollStatement, new PollExtractor(), question).get(0);

        final String requestPollResults = "SELECT * FROM poll_result_registry WHERE poll_id = ? AND replaced = false ";
        List<PollResult> requestResults = jdbcOperations.query(requestPollResults, new PollResultExtractor(), requestedPoll.getUid());

        for(PollResult pollResult : requestResults) {
            switch (pollResult.getChoice()) {
                case 1:
                    counter[0] += 1;
                    break;
                case 2:
                    counter[1] += 1;
                    break;
                case 3:
                    counter[2] += 1;
                    break;
                case 4:
                    counter[3] += 1;
                    break;
            }
        }

        resultSet.put("choice1", counter[0]);
        resultSet.put("choice2", counter[1]);
        resultSet.put("choice3", counter[2]);
        resultSet.put("choice4", counter[3]);

        return resultSet;
    }

    @Override
    @Transactional
    public List<PollResult> queryPollResultByUser(String username, boolean replaced) {

        String statement;
        if(replaced) {
            statement = "SELECT * FROM poll_result_registry WHERE user_id = ?";
            return jdbcOperations.query(statement, new PollResultExtractor(), username);
        } else {
            statement = "SELECT * FROM poll_result_registry WHERE user_id = ? AND replaced = ? ";
            return jdbcOperations.query(statement, new PollResultExtractor(), username, false);
        }
    }

    @Override
    @Transactional
    public List<PollResult> getPollResultByID(String uid) {
        final String statement = "SELECT * FROM poll_result_registry WHERE uid = ?";
        return jdbcOperations.query(statement, new PollResultExtractor(), uid);
    }

    @Override
    public Map<String, ?> isUserVoteQuestionBefore(String username, String id) {
        final String statement = "SELECT * FROM poll_result_registry WHERE user_id = ? AND poll_id = ? AND replaced = false ";
        List<PollResult> resultSet = jdbcOperations.query(statement, new PollResultExtractor(), username, id);

        Map<String, ? super Object> resultMap = new HashMap<>();

        if(resultSet.isEmpty()) {
            resultMap.put("result", "N");
        } else {
            resultMap.put("result", "Y");
            resultMap.put("content", resultSet.get(0).getChoice());
            resultMap.put("pollingUID", resultSet.get(0).getUid());
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
    public <T, V, K> void pollResultModifier(T userID, V pollID, K choice) {
        final String statement = "INSERT INTO poll_result_registry VALUES (?, ?, ?, ?, ?, false )";
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
    public <K> void replaceUpdater(K key) {
        final String statement = "UPDATE poll_result_registry SET replaced = true WHERE uid = ?";
        jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(statement);
                preparedStatement.setString(1, key.toString());
                return preparedStatement;
            }
        });
    }
}
