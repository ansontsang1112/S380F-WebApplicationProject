package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.User;

import java.util.List;

public interface UserRepository {
    public List<User> queryAllUsers();
    public List<User> queryRelatedUserByType(String role);
    public List<User> queryUser(String username);
    public List<User> queryUserByID(String id);
    public String add(User user);
    public <T> void delete(T key);
    public <T> void update(T fieldOld, T fieldNew, T key);
}
