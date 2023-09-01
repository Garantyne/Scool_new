package com.example.scool_new.service;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.repositorys.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
        return fac.orElseGet(fac::get);
    }

    public Faculty updateFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id){
        facultyRepository.deleteById(id);

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
}
