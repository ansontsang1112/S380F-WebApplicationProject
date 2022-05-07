package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.CommentRepository;
import hkmu.comp380f.webApplicationProject.dao.PollRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.Poll;
import hkmu.comp380f.webApplicationProject.model.PollResult;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.PollServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class PollController {
    @Resource
    private PollRepository pollRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommentRepository commentRepository;
    @Autowired
    private PollServices pollServices;

    private final String PAGE = "poll";

    @GetMapping("/poll")
    public String poll(Principal principal, ModelMap modelMap,
                       @RequestParam Optional<String> id) {
        // Query All Question(s)
        List<Poll> pollList = pollRepository.queryAllPoll(false);
        // Get UserObject
        User user = userRepository.queryUser(principal.getName()).get(0);

        if(id.isPresent()) {
            Poll requestedPoll = pollRepository.queryPollByID(id.get()).get(0);
            Map<String, Integer> questionResultCounter = pollRepository.queryPollCountByQuestion(requestedPoll.getQuestion());

            // Check if user vote before
            Map<String, ?> isUserVoteBefore = pollRepository.isUserVoteQuestionBefore(user.getUsername(), requestedPoll.getUid());
            if(isUserVoteBefore.get("result").toString() == "Y") {
                modelMap.addAttribute("pollChooseBefore", pollServices.convertNumToChoice(Integer.parseInt(isUserVoteBefore.get("content").toString()), requestedPoll.getQuestion()));
                modelMap.addAttribute("polledUID", isUserVoteBefore.get("pollingUID").toString());
            }

            List<String> pollChoices = pollServices.choiceList(requestedPoll);
            List<Comment> pollComments = commentRepository.queryCommentsByPollID(requestedPoll.getUid(), false);
            modelMap.addAttribute("isVoteBefore", isUserVoteBefore.get("result"));
            modelMap.addAttribute("pollChoices", pollChoices);
            modelMap.addAttribute("requestedPoll", requestedPoll);
            modelMap.addAttribute("id", id.get());
            modelMap.addAttribute("qrc", questionResultCounter);
            modelMap.addAttribute("comments", pollComments);
        }

        modelMap.addAttribute("userObject", user);
        modelMap.addAttribute("pollList", pollList);
        modelMap.addAttribute("role", user.getRole());
        return "/common/poll";
    }

    @PostMapping("/poll")
    public String pollEvent(Principal principal, ModelMap modelMap,
                            @RequestParam String id,
                            @RequestParam Optional<Integer> choice,
                            @RequestParam Optional<Boolean> update,
                            @RequestParam Optional<String> polledUID,
                            @RequestParam Optional<String> pollID,
                            @RequestParam Optional<String> action,
                            @RequestParam Optional<String> message) {

        if(update.isPresent()) {
            if (update.get()) {
                // Update statement
                if(polledUID.isPresent()) {
                    PollResult pollResult = pollRepository.getPollResultByID(polledUID.get()).get(0);
                    if(pollResult.getChoice() != choice.get()) {
                        pollRepository.replaceUpdater(polledUID.get());
                        pollRepository.pollResultModifier(principal.getName(), id, choice);
                    }
                }
            } else {
                // Add statement
                pollRepository.pollResultModifier(principal.getName(), id, choice);
            }
        }

        if(action.isPresent()) {
            switch (action.get()) {
                case "add":
                    Comment comment = new Comment(UUID.randomUUID().toString(),
                            principal.getName(),
                            message.get(),
                            pollID.get(),
                            new Timestamp(System.currentTimeMillis()),
                            new Timestamp(System.currentTimeMillis()),
                            false,
                            PAGE
                            );
                    commentRepository.add(comment);
                    break;
            }
        }


        // Query All Question(s) and comments
        List<Poll> pollList = pollRepository.queryAllPoll(false);
        List<Comment> pollComments = new ArrayList<>();
        if(pollID.isPresent()) {
            pollComments = commentRepository.queryCommentsByPollID(pollID.get(), false);
        }
        // Get UserObject
        User user = userRepository.queryUser(principal.getName()).get(0);

        modelMap.addAttribute("userObject", user);
        modelMap.addAttribute("pollList", pollList);
        modelMap.addAttribute("role", user.getRole());
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("pollSelected", id);
        modelMap.addAttribute("comments", pollComments);

        return "/common/poll";
    }
}
