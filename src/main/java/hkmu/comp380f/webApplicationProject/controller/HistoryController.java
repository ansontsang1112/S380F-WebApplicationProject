package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.CommentRepository;
import hkmu.comp380f.webApplicationProject.dao.PollRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.PollResult;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.CommentHandlingServices;
import hkmu.comp380f.webApplicationProject.services.PollServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HistoryController {
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private PollRepository pollRepository;
    @Autowired
    private CommentHandlingServices commentHandlingServices;
    @Autowired
    private PollServices pollServices;

    @GetMapping("/comment")
    public ModelAndView comment() {
        return new ModelAndView(new RedirectView("/ocs/history"));
    }

    @GetMapping("/history")
    public String history(Principal principal, ModelMap modelMap,
                          @RequestParam Optional<String> action,
                          @RequestParam Optional<String> mid) {

        // Get User Object
        User user = userRepository.queryUser(principal.getName()).get(0);
        // Get User Role
        String role = user.getRole();
        // Get User history
        List<Comment> comments = commentHandlingServices.commentListHandler(user);

        List<PollResult> pollResults = pollRepository.queryPollResultByUser(user.getUsername(), true);

        // Add param to history page
        modelMap.addAttribute("role", role);
        modelMap.addAttribute("comments", comments);
        modelMap.addAttribute("polls", pollServices.historyIndexDataConvener(pollResults));

        return "/common/history";
    }
}
