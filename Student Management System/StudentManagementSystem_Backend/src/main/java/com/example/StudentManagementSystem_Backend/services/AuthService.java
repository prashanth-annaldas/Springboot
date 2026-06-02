package com.example.StudentManagementSystem_Backend.services;

import com.example.StudentManagementSystem_Backend.DTO.*;
import com.example.StudentManagementSystem_Backend.entity.*;
import com.example.StudentManagementSystem_Backend.repository.*;
import com.example.StudentManagementSystem_Backend.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    @Autowired
    private CoursesRepo coursesRepo;

    @Autowired
    private EnrollmentsRepo enrollmentsRepo;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequestDTO dto){
        if(usersRepo.existsByEmail(dto.getEmail())){
            return "User already exists";
        }
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Users.Role.STUDENT);
        user.setStatus(Users.Status.PENDING);
        Users savedUsers = usersRepo.save(user);

        Student student = new Student();
        student.setDepartment(dto.getDepartment());
        student.setPhone(dto.getPhone());
        student.setUser(savedUsers);
        studentRepo.save(student);
        return "REGISTRATION SUCCESSFULL";
    }

    public LoginResponseDTO login(LoginRequestDTO dto, HttpServletResponse res){
        Users user = usersRepo.findByEmail(dto.getEmail()).orElse(null);
        if(user == null){
            return new LoginResponseDTO("USER NOT EXISTED", null);
        }
        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            return new LoginResponseDTO("INCORRECT CREDENTIALS", null);
        }
        if(user.getStatus() != Users.Status.ACTIVE){
            return new LoginResponseDTO("ACCOUNT NOT APPROVED YET", null);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        res.addCookie(cookie);

        return new LoginResponseDTO("LOGIN SUCCESSFULL", user.getRole().name());
    }

    public List<Users> getPendingStudents() {
        return usersRepo.findByStatus(
                Users.Status.PENDING
        );
    }

    public String approveStudent(Long id){

        Users user = usersRepo
                .findById(id)
                .orElse(null);

        if(user == null){
            return "USER NOT FOUND";
        }

        user.setStatus(Users.Status.ACTIVE);

        usersRepo.save(user);

        return "STUDENT APPROVED";
    }

    public String createFaculty(FacultyRequestDTO dto){
        if(usersRepo.existsByEmail(dto.getEmail())){
            return "FACULTY ALREADY EXISTS";
        }
        Users users = new Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        users.setPassword(encoder.encode(dto.getPassword()));
        users.setRole(Users.Role.FACULTY);
        users.setStatus(Users.Status.ACTIVE);

        Users savedUser = usersRepo.save(users);

        Faculty faculty = new Faculty();
        faculty.setDepartment(dto.getDepartment());
        faculty.setEmployeeId(dto.getEmployeeId());
        faculty.setUser(savedUser);

        facultyRepo.save(faculty);

        return "FACULTY SAVED";
    }

    public String createCourse(CourseRequestDTO dto){
        if(coursesRepo.existsByCourseName(dto.getCourseName())){
            return "COURSE ALREADY EXISTED";
        }
        Faculty faculty = facultyRepo.findById(dto.getFacultyId()).orElseThrow();
        Course course = new Course();
        course.setCourseName(dto.getCourseName());
        course.setFaculty(faculty);

        coursesRepo.save(course);
        return "COURSE CREATED";
    }

    public List<Course> getCoursesByDepartment(String email){
        Users user = usersRepo.findByEmail(email).orElseThrow();
        Student student = studentRepo.findByUser(user).orElseThrow();
        return coursesRepo.findByFaculty_Department(student.getDepartment());
    }

    public String enrollCourse(String email, Long courseId){
        Users user = usersRepo.findByEmail(email).orElseThrow();
        Student student = studentRepo.findByUser(user).orElseThrow();
        Course course = coursesRepo.findById(courseId).orElseThrow();
        if(enrollmentsRepo.existsByStudentAndCourse(student, course)){
            return "ALREADY ENROLLED";
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollmentsRepo.save(enrollment);
        return "ENROLLMENT SAVED";
    }

    public List<EnrollmentResponseDTO> getMyEnrollments(String email){
        Users user = usersRepo.findByEmail(email).orElseThrow();
        Student student = studentRepo.findByUser(user).orElseThrow();
        List<Enrollment> enrollments = enrollmentsRepo.findByStudent(student);
        List<EnrollmentResponseDTO> res = new ArrayList<>();
        for(Enrollment enrollment : enrollments){
            EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
            dto.setCourseName(enrollment.getCourse().getCourseName());
            dto.setGrade(enrollment.getGrade());
            res.add(dto);
        }
        return res;
    }

    public List<FacultyEnrollmentDTO> getFacultyStudents(String email){
        Users user = usersRepo.findByEmail(email).orElseThrow();
        Faculty faculty = facultyRepo.findByUser(user).orElseThrow();
        List<Enrollment> enrollments = enrollmentsRepo.findByCourse_faculty(faculty);
        List<FacultyEnrollmentDTO> res = new ArrayList<>();
        for(Enrollment enrollment : enrollments){
            FacultyEnrollmentDTO dto = new FacultyEnrollmentDTO();
            dto.setEnrollmentId(enrollment.getId());
            dto.setStudentName(enrollment.getStudent().getUser().getName());
            dto.setCourseName(enrollment.getCourse().getCourseName());
            dto.setGrade(enrollment.getGrade());
            res.add(dto);
        }
        return res;
    }

    public String updateGrade(Long enrollmentId, String grade){
        Enrollment enrollment = enrollmentsRepo.findById(enrollmentId).orElseThrow();
        enrollment.setGrade(grade);
        enrollmentsRepo.save(enrollment);
        return "UPDATED GRADAE";
    }

    public String myLogout(HttpServletResponse res){
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        SecurityContextHolder.clearContext();
        return "LOGOUT SUCCESSFULL";
    }

    public StudentResponseDTO getStudentDetails(String email){
        Users user = usersRepo.findByEmail(email).orElseThrow();
        Student student = studentRepo.findByUser(user).orElseThrow();
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setStudentName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setDepartment(student.getDepartment());
        dto.setPhone(student.getPhone());
        return dto;
    }

    public FacultyAndAdminResponseDTO getFacultyAndAdminDetails(String email){
        Users user = usersRepo.findByEmail(email).orElseThrow();
        FacultyAndAdminResponseDTO dto = new FacultyAndAdminResponseDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return dto;
    }

    public String removeFaculty(Long id){

        Faculty faculty = facultyRepo.findById(id).orElseThrow();

        Users user = faculty.getUser();

        if(user == null){
            return "FACULTY NOT FOUND";
        }

        facultyRepo.delete(faculty);
        usersRepo.delete(user);

        return "FACULTY REMOVED";
    }

    public String forgotPassword(ForgotPasswordDTO dto){
        Users user = usersRepo.findByEmail(dto.getEmail()).orElse(null);

        if(user == null){
            return "USER DOES NOT EXISTS";
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        usersRepo.save(user);
        return "PASSWORD CHANGED";
    }
}