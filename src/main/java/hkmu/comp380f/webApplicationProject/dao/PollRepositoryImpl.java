package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Poll;
import hkmu.comp380f.webApplicationProject.model.PollResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Poll> queryAllPoll() {
        return null;
    }

    @Override
    public List<Poll> queryPollByID(String uid) {
        return null;
    }

    @Override
    public List<Poll> queryPollByUserID(String username) {
        return null;
    }

    @Override
    public Map<String, Integer> queryPollCountByQuestion(String Question) {
        return null;
    }

    @Override
    public List<PollResult> queryPollResultByUser(String username) {
        return null;
    }

    @Override
    public void addPoll(Poll poll) {

    }

    @Override
    public <T> String delPollQuestion(T str) {
        return null;
    }

    @Override
    public <T, V, K> String updatePollQuestion(T index, V newValue, K key) {
        return null;
    }
}
