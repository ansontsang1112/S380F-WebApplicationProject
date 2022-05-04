package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.PollRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PollController {
    @Resource
    private PollRepository pollRepository;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private PollServices pollServices;

    @GetMapping("/poll")
    public String poll(Principal principal, ModelMap modelMap,
                       @RequestParam Optional<String> id) {
        // Query All Question(s)
        List<Poll> pollList = pollRepository.queryAllPoll();
        // Get UserObject
        User user = userRepository.queryUser(principal.getName()).get(0);

        if(id.isPresent()) {
            Poll requestedPoll = pollRepository.queryPollByID(id.get()).get(0);

            // Check if user vote before
            Map<String, ?> isUserVoteBefore = pollRepository.isUserVoteQuestionBefore(user.getUsername(), requestedPoll.getUid());
            if(isUserVoteBefore.get("result").toString() == "Y") {
                modelMap.addAttribute("pollChooseBefore", pollServices.convertNumToChoice(Integer.parseInt(isUserVoteBefore.get("content").toString()), requestedPoll.getQuestion()));
            }

            List<String> pollChoices = pollServices.choiceList(requestedPoll);
            modelMap.addAttribute("isVoteBefore", isUserVoteBefore.get("result"));
            modelMap.addAttribute("pollChoices", pollChoices);
            modelMap.addAttribute("requestedPoll", requestedPoll);
            modelMap.addAttribute("id", id.get());
        }

        modelMap.addAttribute("userObject", user);
        modelMap.addAttribute("pollList", pollList);
        modelMap.addAttribute("role", user.getRole());
        return "/common/poll";
    }

    @PostMapping("/poll")
    public String pollEvent(Principal principal, ModelMap modelMap,
                            @RequestParam String id,
                            @RequestParam Integer choice,
                            @RequestParam String isVoteBefore) {
        if(isVoteBefore == "true") {
            // Update statement

        } else {
            // Add statement
            pollRepository.addPollResult(principal.getName(), id, choice);
        }

        // Query All Question(s)
        List<Poll> pollList = pollRepository.queryAllPoll();
        // Get UserObject
        User user = userRepository.queryUser(principal.getName()).get(0);

        modelMap.addAttribute("userObject", user);
        modelMap.addAttribute("pollList", pollList);
        modelMap.addAttribute("role", user.getRole());
        modelMap.addAttribute("id", id);

        return "/common/poll";
    }
}
