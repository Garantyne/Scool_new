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
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;

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
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> findStudentByAge(@PathVariable int age){
        Collection<Student> stu = studentService.findStudentByAge(age);
        if(stu.isEmpty()){
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
        Optional<Faculty> fac = studentService.findFaculty(id);
        if(fac.isEmpty()){
            return null;
        }
        return fac.get();
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
}
