package hkmu.comp380f.webApplicationProject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String id;
    private String courseID; 
    private String courseName;
    private String [] lectures;
    private String [] studentList;

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

    public String [] getLectures() {
        return lectures;
    }

    public String [] getStudentList() {
        return studentList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLectures(String [] lectures) {
        this.lectures = lectures;
    }

    public void setStudentList(String [] studentList) {
        this.studentList = studentList;
    }
}
