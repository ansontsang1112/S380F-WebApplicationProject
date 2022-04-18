package hkmu.comp380f.s380f_webapplicationproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String id;
    private String courseID; 
    private String courseName;
    private ArrayList<String> lectures;
    private ArrayList<String> studentList;
    
    public void addLectureToList(String lectureUserID) {
        lectures.add(lectureUserID);
    }
    
    public void addStudentToList(String studentUserID) {
        studentList.add(studentUserID);
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
   

    public String getId() {
        return id;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public ArrayList<String> getLectures() {
        return lectures;
    }

    public ArrayList<String> getStudentList() {
        return studentList;
    }

}
