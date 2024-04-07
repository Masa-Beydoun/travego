package com.example.security.Service;

import com.example.security.Models.Author;
import com.example.security.Models.BaseUser;
import com.example.security.Repository.BaseUserRepository;
import com.example.security.Request.Register_Login_Request;
import com.example.security.RolesAndPermission.Roles;
import com.example.security.Models.Course;
import com.example.security.Models.Student;
import com.example.security.Repository.CourseRepository;
import com.example.security.Repository.StudentRepository;
import com.example.security.Request.StudentRegisterRequest;
import com.example.security.Security.Config.JwtService;
import com.example.security.Security.Token.Token;
import com.example.security.Security.Token.TokenRepository;
import com.example.security.Security.Token.TokenType;
import com.example.security.Security.auth.AuthenticationResponse;
import com.example.security.Validator.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private  final TokenRepository tokenRepository;

    private  final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
private final ObjectsValidator<Register_Login_Request> validator;


    public void Add_Course_To_my_account(int Course_id) {
/*
        Student student= (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      Course course = courseRepository.getCourseById(Course_id);

      if(student.getMy_Money()>=course.getPrice()) {
          student.getCourses().add(course);
          course.getStudents().add(student);
          student.setMy_Money(student.getMy_Money()-course.getPrice());
          studentRepository.save(student);
          courseRepository.save(course);

      }*/
        Course course = courseRepository.getCourseById(Course_id);
        Student student= studentRepository.getStudentsById(2);
        if(student.getMy_Money()>=course.getPrice()) {
            student.getCourses().add(course);
            course.getStudents().add(student);
            student.setMy_Money(student.getMy_Money() - course.getPrice());
            studentRepository.save(student);
            courseRepository.save(course);
        }
else
     throw new IllegalStateException(     "YOU DONT HAVE MUCH MONEY TO BUY THE COURSE");

    }
//@Cacheable("students")
//@CachePut("students")
    public List<Course> Get_My_Courses(int request) {
        return    courseRepository.findAllByStudentsId(request);


    }

    public void add_money_to_myaccount(double adding_money) {

      Student student_id= (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     Student the_student=studentRepository.getStudentsById(student_id.getId());
        System.out.println(the_student.getEmail());
     the_student.setMy_Money(adding_money+the_student.getMy_Money());
studentRepository.save(the_student);

    }

}
