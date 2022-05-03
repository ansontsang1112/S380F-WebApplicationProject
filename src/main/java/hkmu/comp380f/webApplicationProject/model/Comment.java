package hkmu.comp380f.webApplicationProject.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Comment {
    private String message_id;
    private String userID;
    private String message;
    private String courseID;
    private Timestamp timestamp;
    private Timestamp lastEdit;

    public Comment() {}

    public Comment(String message_id, String userID, String message, String courseID, Timestamp timestamp, Timestamp lastEdit) {
        this.message_id = message_id;
        this.userID = userID;
        this.message = message;
        this.courseID = courseID;
        this.timestamp = timestamp;
        this.lastEdit = lastEdit;
    }

    public Timestamp getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Timestamp lastEdit) {
        this.lastEdit = lastEdit;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
