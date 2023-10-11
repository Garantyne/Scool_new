package com.example.scool_new;

import com.example.scool_new.controller.FacultyController;
import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.FacultyRepository;
import com.example.scool_new.service.FacultyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)//избавится от лишнего построения
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean//млкать надо репозиторий а не сервис
    private FacultyRepository facultyRepository;

    @SpyBean//именно спай бин
    private FacultyService facultyService;

    @InjectMocks//собирает все бины выше(условно)
    private FacultyController facultyController;


    @Test
    void testGetFaculty() throws Exception{
        Faculty fac = new Faculty(1L,"asdf","asdf");
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(fac));     //эни лонг любое значение которое бы я не передал

        mockMvc.perform(get("/faculty/{id}","1"))
                .andExpect(status().isOk())//andExpect показывает что я ожидаю получить
                .andExpect(content().json(objectMapper.writeValueAsString(fac)));   //превращаем объект в джейсон и сравниваем его

        when(facultyRepository.findById(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/faculty/{id}","2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetFacultyByColor() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asdf","asdf");
        Collection<Faculty> fa = new ArrayList<>();
        Faculty f = new Faculty(2L,"asdf","asdf");
        fa.add(f);

        when(facultyService.findFacultyByColor("asdf")).thenReturn(fa);
        when(facultyService.findFacultyByColor(eq("22"))).thenReturn(fa);

        mockMvc.perform(get("/faculty/color/{color}","asdf"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fa)));

        mockMvc.perform(get("/faculty/{color}","asdf"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateFaculty() throws Exception{
        JSONObject userObject = new JSONObject();
        userObject.put("name","color");
        Faculty fac = new Faculty(1L,"asdf","asdf");


        when(facultyRepository.save(any(Faculty.class))).thenReturn(fac);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(fac));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty") //send
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("asdf"));
    }

    @Test
    void testEditFaculty() throws Exception{
        JSONObject userObject = new JSONObject();
        userObject.put("name","color");
        Faculty fac = new Faculty(1L,"asdf","asdf");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(fac);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(fac));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty") //send
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("asdf"));
    }

    @Test
    void testDeleteFaculty() throws Exception{
        JSONObject userObject = new JSONObject();
        userObject.put("name","color");
        Faculty fac = new Faculty(500L,"asdf","asdf");

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(fac));
        when(facultyRepository.findById(eq(222L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}","1")) //send
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fac)));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/{id}", "222"))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetFacultyStudentId() throws Exception{
        List<Student> stu = new ArrayList<>();
        Faculty fac = new Faculty(1L,"qwer","qwer");
        Student stud = new Student(1L,"asdf",22);
        stu.add(stud);
        fac.setStudents(stu);
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(fac));     //эни лонг любое значение которое бы я не передал

        mockMvc.perform(get("/faculty/students/{id}","1"))
                .andExpect(status().isOk())//andExpect показывает что я ожидаю получить
                .andExpect(content().json(objectMapper.writeValueAsString(stu)));   //превращаем объект в джейсон и сравниваем его

        when(facultyRepository.findById(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/faculty/{id}","2"))
                .andExpect(status().isNotFound());
    }

}
