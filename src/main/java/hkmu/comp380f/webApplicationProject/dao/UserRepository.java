package hkmu.comp380f.webApplicationProject.dao;

import hkmu.comp380f.webApplicationProject.model.User;

import java.util.List;

public interface UserRepository {
    public List<User> queryAllUsers();
    public List<User> queryUser(String username);
    public String add(User user);
    public void delete(User user);
    public <T> void update(User user, T fieldOld, T fieldNew);
}
