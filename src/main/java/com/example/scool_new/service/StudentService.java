package com.example.scool_new.service;

import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.StudentRepository;
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
        return stu.orElseGet(stu::get);
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
}
