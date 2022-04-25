package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Comment;
import hkmu.comp380f.webApplicationProject.model.User;

import java.util.List;

public interface CommentRepository {
    public List<Comment> queryAllComment();
    public List<Comment> queryCommentsByCourseId(String courseID);
    public List<Comment> queryCommentsByStudent(User user);

    public String add(Comment comment);
    public <T> String update(T fieldOld, T fieldNew, T key);
    public <T> String delete(T key);
}
