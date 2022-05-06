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
    public List<PollResult> queryPollResultByUser(String username, boolean replaced);
    public Map<String, ?> isUserVoteQuestionBefore(String username, String id);
    public List<PollResult> getPollResultByID(String uid);

    public void addPoll(Poll poll);
    public String delPoll(String str);
    public <T, V, K> String updatePoll(T index, V newValue, K key);

    public <T, V, K> void pollResultModifier(T userID, V pollID, K choice);
    public <K> void replaceUpdater(K key);
}
