package hkmu.comp380f.webApplicationProject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String id;
    private String courseID; 
    private String courseName;
    private String [] lectures;
    private String [] studentList;

    private List<User> lecturesObject;
    private List<User> studentsObject;

    private String serializedLectures;
    private String serializedStudents;

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

    public String getSerializedLectures() {
        return serializedLectures;
    }

    public void setSerializedLectures(String serializedLectures) {
        this.serializedLectures = serializedLectures;
    }

    public String getSerializedStudents() {
        return serializedStudents;
    }

    public void setSerializedStudents(String serializedStudents) {
        this.serializedStudents = serializedStudents;
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

    public List<User> getLecturesObject() {
        return lecturesObject;
    }

    public void setLecturesObject(List<User> lecturesObject) {
        this.lecturesObject = lecturesObject;
    }

    public List<User> getStudentsObject() {
        return studentsObject;
    }

    public void setStudentsObject(List<User> studentsObject) {
        this.studentsObject = studentsObject;
    }
}
