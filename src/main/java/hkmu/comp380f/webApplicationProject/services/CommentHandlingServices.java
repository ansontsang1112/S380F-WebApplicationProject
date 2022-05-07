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

    public List<Comment> commentListHandler(User user, String page) {
        List<Comment> comments;

        comments = commentRepository.queryCommentsByUser(user.getUsername(), page);

        if(comments.isEmpty()) {
            comments = new ArrayList<>();
        }

        return comments;
    }
}
