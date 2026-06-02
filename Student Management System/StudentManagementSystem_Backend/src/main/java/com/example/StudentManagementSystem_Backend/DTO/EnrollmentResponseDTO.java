package com.example.StudentManagementSystem_Backend.DTO;

public class EnrollmentResponseDTO {
    private String courseName;
    private String grade;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public EnrollmentResponseDTO(String courseName, String grade){
        this.courseName = courseName;
        this.grade = grade;
    }
    public EnrollmentResponseDTO(){}
}
