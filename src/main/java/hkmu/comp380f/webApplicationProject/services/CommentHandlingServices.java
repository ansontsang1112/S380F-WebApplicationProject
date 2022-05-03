package hkmu.comp380f.webApplicationProject.services;

import hkmu.comp380f.webApplicationProject.dao.CommentRepository;
import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentHandlingServices {
    @Resource
    public CommentRepository commentRepository;

    public List<Comment> commentListHandler(User user) {
        List<Comment> comments;

        // Get user role
        String role = user.getRole();

        switch (role) {
            case "USER":
                comments = commentRepository.queryCommentsByStudent(user.getUsername());
                break;
            case "LECTURER":
                comments = commentRepository.queryAllComment();
                break;
            default:
                comments = new ArrayList<>();
        }

        if(comments.isEmpty()) {
            comments = new ArrayList<>();
        }

        return comments;
    }
}
