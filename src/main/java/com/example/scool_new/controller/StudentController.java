package com.example.scool_new.controller;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.service.AvatarService;
import com.example.scool_new.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id){
        Student stu = studentService.getStudent(id);
        if(stu == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stu);
    }

    @PutMapping()
    public Student editStudent( Student student){
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id){
        return ResponseEntity.ok().build();
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> findStudentByAge(@PathVariable int age){
        Collection<Student> stu = studentService.findStudentByAge(age);
        if(stu == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stu);
    }

    @GetMapping("/sort/{ageFrom},{ageTo}")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@PathVariable int ageFrom,@PathVariable int ageTo){
        return studentService.findByAgeBetween(ageFrom,ageTo);
    }

    @GetMapping("/faculty/{id}")
    public Faculty findFaculty(@PathVariable("id") long id){
        return studentService.findFaculty(id);
    }

    @GetMapping("/student/getNumberStudents")
    public int getNumberOfStudents(){
        return studentService.getStudentOfSchool();
    }

    @GetMapping("/student/getAvgAgeStudents")
    public double getAvgAgeStudents(){
        return studentService.getAvgOfStudents();
    }
    @GetMapping("/student/getFiveLastStudents")
    public Collection<Student> getFiveLastStudents(){
        return studentService.fiveLastStudents();
    }

    @GetMapping("/student/{letter}/getNameByLetter")
    public Collection<Student> getFilterName(@PathVariable String letter){
        return studentService.filterStudentByLetter(letter);
    }

    @GetMapping("/avgAllStudStream")
    public Double getAvgStream(){
        return studentService.getAvgStream();
    }
}
