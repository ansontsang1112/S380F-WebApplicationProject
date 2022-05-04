package hkmu.comp380f.webApplicationProject.services;

import hkmu.comp380f.webApplicationProject.dao.PollRepository;
import hkmu.comp380f.webApplicationProject.model.Poll;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
}
