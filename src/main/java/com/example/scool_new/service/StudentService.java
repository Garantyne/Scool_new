package com.example.scool_new.service;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudent(long id){
        Optional<Student> stu = studentRepository.findById(id);
        return stu.orElse(null);
    }

    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }

    public void deleteStudent(long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        Collection<Student> stud = new ArrayList<>();
        for (var v : studentRepository.findAll()) {
            if(v.getAge() == age){
                stud.add(v);
            }
        }
        return stud;
    }

    public ResponseEntity<Collection<Student>> findByAgeBetween(int ageFrom, int ageTo) {
        Collection<Student> stu = studentRepository.findByAgeBetween(ageFrom,ageTo);
        if(stu == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stu);
    }

    public Optional<Faculty> findFaculty(long id) {
        Optional<Student> fac = studentRepository.findById(id);
        Optional<Faculty> faculty = fac.map(Student::getFaculty);
        return faculty;
    }

    public Student findStudent(Long id){
        Optional<Student> stu = studentRepository.findById(id);
        return stu.orElse(null);
    }

    public int getStudentOfSchool(){
        return studentRepository.getNumbersAllStudent();
    }

    public double getAvgOfStudents(){
        return studentRepository.getAvgAgeOfStudents();
    }

    public Collection<Student> fiveLastStudents(){
        return studentRepository.getFiveLastStudents();
    }
}
