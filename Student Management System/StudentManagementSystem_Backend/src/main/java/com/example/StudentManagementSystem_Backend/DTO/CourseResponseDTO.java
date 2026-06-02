package com.example.StudentManagementSystem_Backend.DTO;

public class CourseResponseDTO {
    private Long id;
    private String courseName;

    public CourseResponseDTO(
            Long id,
            String courseName
    ){
        this.id = id;
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
