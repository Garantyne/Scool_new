package com.example.scool_new.repositorys;

import com.example.scool_new.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    List<Faculty> findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(String color,String name);
}
