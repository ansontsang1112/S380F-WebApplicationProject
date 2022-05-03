package hkmu.comp380f.webApplicationProject.controller;

import hkmu.comp380f.webApplicationProject.dao.CommentRepository;
import hkmu.comp380f.webApplicationProject.dao.UserRepository;
import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.User;
import hkmu.comp380f.webApplicationProject.services.CommentHandlingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private CommentHandlingServices commentHandlingServices;

    @GetMapping("/comment")
    public String comment(Principal principal, ModelMap modelMap,
                          @RequestParam Optional<String> action,
                          @RequestParam Optional<String> mid) {
        if(mid.isPresent() && action.isPresent()) {
            switch (action.get()) {
                case "delete":
                    String messageID = commentRepository.delete(mid.get());
                    modelMap.addAttribute("action", "Comment " + messageID + " deleted successfully.");
                    break;

                default:
            }
        }

        // Get User Object
        User user = userRepository.queryUser(principal.getName()).get(0);
        // Get User Role
        String role = user.getRole();
        // Get User comments
        List<Comment> comments = commentHandlingServices.commentListHandler(user);
        // Add param to comment page
        modelMap.addAttribute("role", role);
        modelMap.addAttribute("comments", comments);

        return "/common/comment";
    }
}
