package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Poll;
import hkmu.comp380f.webApplicationProject.model.PollResult;

import java.util.List;
import java.util.Map;

public interface PollRepository {
    public List<Poll> queryAllPoll();
    public List<Poll> queryPollByID(String uid);
    public List<Poll> queryPollByUserID(String username);

    public Map<String, Integer> queryPollCountByQuestion(String Question);
    public List<PollResult> queryPollResultByUser(String username);

    public void addPoll(Poll poll);
    public <T> String delPollQuestion(T str);
    public <T, V, K> String updatePollQuestion(T index, V newValue, K key);
}
