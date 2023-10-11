package com.example.scool_new.service;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){

        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id){
        Optional<Faculty> fac = facultyRepository.findById(id);
        return fac.orElse(null);
    }

    public Faculty updateFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id){
        Optional<Faculty> f = facultyRepository.findById(id);
        facultyRepository.deleteById(id);
        return f.orElse(null);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        Collection<Faculty> fac = new ArrayList<>();
        for (var v : facultyRepository.findAll()) {
            if (v.getColor().equals(color)) {
                fac.add(v);
            }
        }
        return fac;
    }

    public Collection<Faculty> findFacultyByColorOrName(String colorOrName) {
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName,colorOrName);
    }

    public List<Student> findStudents(long id) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        return facultyOptional.map(Faculty::getStudents).orElse(null);
    }
}
