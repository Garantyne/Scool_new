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

}
