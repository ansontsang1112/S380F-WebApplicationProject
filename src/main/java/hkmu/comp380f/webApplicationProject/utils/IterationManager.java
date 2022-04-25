package hkmu.comp380f.webApplicationProject.utils;

import hkmu.comp380f.webApplicationProject.model.User;

import java.util.ArrayList;
import java.util.List;

public class IterationManager {

    public static List<Object> userIterationByKey(List<User> list, String key) {
        List<Object> result = new ArrayList<>();

        for(User obj : list) {
            Object val;

            switch (key) {
                case "username":
                    val = obj.getUsername();
                    break;

                case "password":
                    val = obj.getPassword();
                    break;

                case "fullname":
                    val = obj.getFullName();
                    break;

                case "address":
                    val = obj.getAddress();
                    break;

                case "phonenumber":
                    val = obj.getPhoneNumber();
                    break;

                case "role":
                    val = obj.getRole();
                    break;

                default:
                    throw new NullPointerException("Unknown option found");
            }

            result.add(val);
        }

        return result;
    }
}
