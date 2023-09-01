package com.example.scool_new.controller;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id){
        Faculty fac = facultyService.getFaculty(id);
        if(fac == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fac);
    }

    @PutMapping("")
    public Faculty editFaculty(@RequestBody Faculty faculty){
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id){
       facultyService.deleteFaculty(id);
       return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Collection<Faculty>> findFacultyByColor(String color){
        Collection<Faculty> fac = facultyService.findFacultyByColor(color);
        if(fac == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fac);
    }
}
