package hkmu.comp380f.s380f_webapplicationproject.dao;

import hkmu.comp380f.s380f_webapplicationproject.model.User;
import java.util.List;

public interface UserRepository {
    public List<User> queryAllUser();
    public List<User> queryUser(String uid);
    public boolean addUser(User user);
    public boolean removeUser(String uid);
    public <T> void editUserInfo(String uid, T oldValue, T newValue);     
}
