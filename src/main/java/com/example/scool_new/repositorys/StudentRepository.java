package com.example.scool_new.repositorys;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Collection<Student> findByAgeBetween(int ageFrom,int ageTo);

    @Query(value = "SELECT Count(name) FROM student",nativeQuery = true )
    int getNumbersAllStudent();

    @Query(value = "SELECT AVG(age) FROM student",nativeQuery = true)
    double getAvgAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5",nativeQuery = true)
    Collection<Student> getFiveLastStudents();
}
