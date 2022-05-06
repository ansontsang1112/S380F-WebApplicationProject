package hkmu.comp380f.webApplicationProject.model;

import java.sql.Timestamp;

public class CourseFile {
    private String uid;
    private String courseID;
    private String fileType;
    private String saveURL;
    private Timestamp timestamp;
    private boolean isDisplay;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public CourseFile(String uid, String courseID, String fileType, String saveURL, Timestamp timestamp, boolean isDisplay) {
        this.uid = uid;
        this.courseID = courseID;
        this.fileType = fileType;
        this.saveURL = saveURL;
        this.timestamp = timestamp;
        this.isDisplay = isDisplay;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public CourseFile() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSaveURL() {
        return saveURL;
    }

    public void setSaveURL(String saveURL) {
        this.saveURL = saveURL;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public void setDisplay(boolean display) {
        isDisplay = display;
    }
}
