package com.example.scool_new.service;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id){
        logger.info("Was invoked method for get faculty");
        Optional<Faculty> fac = facultyRepository.findById(id);
        return fac.orElseGet(fac::get);
    }

    public Faculty updateFaculty(Faculty faculty){
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id){
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);

    }

    public Collection<Faculty> findFacultyByColor(String color) {
        logger.info("Was invoked method for find faculty by color");
        Collection<Faculty> fac = new ArrayList<>();
        for (var v : facultyRepository.findAll()) {
            if (v.getColor().equals(color)) {
                fac.add(v);
            }
        }
        return fac;
    }

    public Collection<Faculty> findFacultyByColorOrName(String colorOrName) {
        logger.info("Was invoked method for find faculty by color or name");
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName,colorOrName);
    }

    public List<Student> findStudents(long id) {
        logger.info("Was invoked method for find students by id of faculty");
        List<Student> stu = facultyRepository.findById(id).get().getStudents();
        return stu;
    }

    public String getLongNameFaculty() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }
}
