package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.Comment;

import java.util.List;

public interface CommentRepository {
    public List<Comment> queryAllComment(boolean withDeleted, String page);
    public List<Comment> queryCommentsByCourseId(String courseID, boolean withDeleted);
    public List<Comment> queryCommentsByPollID(String uid, boolean withDeleted);
    public List<Comment> queryCommentsByUser(String username, String page);

    public String add(Comment comment);
    public <T> String delete(T key);
}
