package com.example.scool_new.service;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public Collection<Student> filterStudentByLetter(String letter) {
        return studentRepository.findAll().stream()
                .filter(c->c.getName().startsWith(letter))
                .collect(Collectors.toList());
    }

    public Double getAvgStream() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average().getAsDouble();
    }

    public void getStudentStream() {
        List<Student> stu =  studentRepository.findAll();
        System.out.println(stu.get(0).getName());
        System.out.println(stu.get(1).getName());
        new Thread(){
            @Override
            public void run(){
                System.out.println(stu.get(2).getName());
                System.out.println(stu.get(3).getName());
            }
        }.start();

        new Thread(()->{
            System.out.println(stu.get(4).getName());
            System.out.println(stu.get(5).getName());
        }).start();
    }

    public void getStudentStreamTwo() {
        List<Student> stu = studentRepository.findAll();
        ShowStudent(stu.get(0));
        ShowStudent(stu.get(1));
        Thread thread = new Thread(){
            @Override
            public void run(){
                ShowStudent(stu.get(2));
                ShowStudent(stu.get(3));
            }
        };
        Thread thread2 = new Thread(()->{
            ShowStudent(stu.get(4));
            ShowStudent(stu.get(5));
        });
        thread.start();
        thread2.start();
    }

    private void ShowStudent(Student student){
        synchronized (Student.class){
            System.out.println(student.getName());
        }
    }
}
