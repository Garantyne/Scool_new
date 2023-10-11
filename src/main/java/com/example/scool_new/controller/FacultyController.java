package com.example.scool_new.controller;

import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fac);
    }

    @PutMapping("")
    public Faculty editFaculty(@RequestBody Faculty faculty){
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id){
        Faculty fac = facultyService.deleteFaculty(id);
       if( fac == null){
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(fac);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Collection<Faculty>> findFacultyByColor(@PathVariable String color){
        Collection<Faculty> fac = facultyService.findFacultyByColor(color);
        if(fac.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fac);
    }

    @GetMapping("/filter/{colorOrName}")
    public ResponseEntity<Collection<Faculty>> findFacultyByColorOrName(@PathVariable String colorOrName){
        Collection <Faculty> fac = facultyService.findFacultyByColorOrName(colorOrName);
        if(fac.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fac);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<List<Student>> findStudents(@PathVariable("id") long id){
        List<Student> stu = facultyService.findStudents(id);
        if(stu == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stu);
    }
}
