package com.example.scool_new.repositorys;

import com.example.scool_new.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
