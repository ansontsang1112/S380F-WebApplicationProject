package hkmu.comp380f.webApplicationProject.model;

public class User {
    private String uid;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String role;

    public User() {}

    public User(String uid, String username, String password, String fullName, String phoneNumber, String address, String role) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
