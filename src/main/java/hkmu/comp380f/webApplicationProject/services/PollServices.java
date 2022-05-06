package hkmu.comp380f.webApplicationProject.services;

import hkmu.comp380f.webApplicationProject.dao.PollRepository;
import hkmu.comp380f.webApplicationProject.model.Poll;
import hkmu.comp380f.webApplicationProject.model.PollResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PollServices {
    @Resource
    private PollRepository pollRepository;

    public List<String> choiceList(Poll poll) {
        List<String> pollChoices = new ArrayList<>();

        if(poll.getChoice1() != null) {
            pollChoices.add(poll.getChoice1());
        }

        if(poll.getChoice2() != null) {
            pollChoices.add(poll.getChoice2());
        }

        if(poll.getChoice3() != null) {
            pollChoices.add(poll.getChoice3());
        }

        if(poll.getChoice4() != null) {
            pollChoices.add(poll.getChoice4());
        }

        return pollChoices;
    }

    public String choiceReflecter(Poll poll, int choice) {

        switch (choice) {
            case 1:
                return poll.getChoice1();
            case 2:
                return poll.getChoice2();
            case 3:
                return poll.getChoice3();
            case 4:
                return poll.getChoice4();
            default:
                return "Not Found";
        }

    }

    public String convertNumToChoice(int choice, String question) {
        List<Poll> polls = pollRepository.queryAllPoll();
        
        for(Poll poll : polls) {
            if(question.equals(poll.getQuestion())) {
                switch (choice) {
                    case 1:
                        return poll.getChoice1();
                    case 2:
                        return poll.getChoice2();
                    case 3:
                        return poll.getChoice3();
                    case 4:
                        return poll.getChoice4();
                }
            }
        }
        return "Not Found";
    }

    public List<List<?>> historyIndexDataConvener(List<PollResult> pollResults) {
        List<List<?>> list = new ArrayList<>();

        for(PollResult pollResult : pollResults) {
            list.add(informationTransformer(pollResult));
        }

        return list;
    }

    public List<?> informationTransformer(PollResult pollResult) {
        List<? super Object> infoSet = new ArrayList<>();

        Poll requestedPoll = pollRepository.queryPollByID(pollResult.getPollID()).get(0);

        infoSet.add(requestedPoll.getPollID());
        infoSet.add(choiceReflecter(requestedPoll, pollResult.getChoice()));
        infoSet.add(pollResult.getTimestamp());

        return infoSet;
    }
}
