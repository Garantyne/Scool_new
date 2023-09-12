package com.example.scool_new.service;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student){
        logger.info("Was invoked method for create student");
        logger.debug("Create student - {} in repository", student);
        return studentRepository.save(student);
    }

    public Student getStudent(long id){
        logger.info("Was invoked method for get student");
        logger.debug("Get student by id = {} from the repository",id);
        Optional<Student> stu = studentRepository.findById(id);
        return stu.orElseGet(stu::get);
    }

    public Student updateStudent(Student student){
        logger.info("Was invoked method for update student");
        logger.debug("Update student {} in repository", student.getName());
        return studentRepository.save(student);
    }

    public void deleteStudent(long id){
        logger.info("Was invoked method for delete student");
        logger.debug("Delete student by id = {} from repository",id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentByAge(int age) {
        logger.info("Was invoked method for find student by age");
        logger.debug("Find student by age{} in repository", age);
        Collection<Student> stud = new ArrayList<>();
        for (var v : studentRepository.findAll()) {
            if(v.getAge() == age){
                stud.add(v);
            }
        }
        return stud;
    }

    public ResponseEntity<Collection<Student>> findByAgeBetween(int ageFrom, int ageTo) {
        logger.info("Was invoked method for find student by age between");
        logger.debug("Find student age from{} , to {}", ageFrom, ageTo);
        Collection<Student> stu = studentRepository.findByAgeBetween(ageFrom,ageTo);
        if(stu == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stu);
    }

    public Faculty findFaculty(long id) {
        logger.info("Was invoked method for find student by faculty");
        logger.debug("Find faculty student by id - {}", id);
        return studentRepository.findById(id).get().getFaculty();
    }

    public Student findStudent(Long id){
        logger.info("Was invoked method for find student by id");
        logger.debug("Find student in repository by id = {}", id);
        Optional<Student> stu = studentRepository.findById(id);
        return stu.orElse(null);
    }

    public int getStudentOfSchool(){
        logger.info("Was invoked method for get student of school");
        logger.debug("Find all student of school");
        return studentRepository.getNumbersAllStudent();
    }

    public double getAvgOfStudents(){
        logger.info("Was invoked method for get student of avg age");
        logger.debug("Get avg age all students");
        return studentRepository.getAvgAgeOfStudents();
    }

    public Collection<Student> fiveLastStudents(){
        logger.info("Was invoked method for get 5 last student");
        logger.debug(" Get 5 last students");
        return studentRepository.getFiveLastStudents();
    }
}
