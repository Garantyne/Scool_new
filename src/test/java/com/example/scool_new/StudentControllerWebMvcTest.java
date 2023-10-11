package com.example.scool_new;

import com.example.scool_new.controller.StudentController;
import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.repositorys.StudentRepository;
import com.example.scool_new.service.AvatarService;
import com.example.scool_new.service.FacultyService;
import com.example.scool_new.service.StudentService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean//млкать надо репозиторий а не сервис
    private StudentRepository studentRepository;

    @SpyBean//именно спай бин
    private StudentService studentService;



    @InjectMocks//собирает все бины выше(условно)
    private StudentController facultyController;

    @Test
    void testGetStudent() throws Exception{
        Student stu = new Student(1L,"asdf",22);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(stu));     //эни лонг любое значение которое бы я не передал

        mockMvc.perform(get("/student/{id}","1"))
                .andExpect(status().isOk())//andExpect показывает что я ожидаю получить
                .andExpect(content().json(objectMapper.writeValueAsString(stu)));   //превращаем объект в джейсон и сравниваем его

        when(studentRepository.findById(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/student/{id}","2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteStudent() throws Exception{
        Student stu = new Student(500L,"asdf",23);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(stu));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/{id}","1")) //send
                .andExpect(status().isOk());
    }

    @Test
    void testCreateStudent() throws Exception{
        JSONObject userObject = new JSONObject();
        userObject.put("name","color");
        Student stu = new Student(1L,"asdf",23);


        when(studentRepository.save(any(Student.class))).thenReturn(stu);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(stu));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student") //send
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("asdf"));
    }

    @Test
    void testGetStudentyByAge() throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age", 22);
        Collection<Student> st = new ArrayList<>();
        Student student = new Student(2L,"asdf",22);
        st.add(student);

        when(studentService.findStudentByAge(22)).thenReturn(st);
        when(studentService.findStudentByAge(eq(22))).thenReturn(st);

        mockMvc.perform(get("/student/age/{age}",22))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(st)));

        mockMvc.perform(get("/student/age/{age}",23))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindFaculty() throws Exception{
        Student stu = new Student(1L,"asdf",22);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(stu));     //эни лонг любое значение которое бы я не передал

        mockMvc.perform(get("/student/{id}","1"))
                .andExpect(status().isOk())//andExpect показывает что я ожидаю получить
                .andExpect(content().json(objectMapper.writeValueAsString(stu)));   //превращаем объект в джейсон и сравниваем его

        when(studentRepository.findById(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/student/{id}","2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetNumberOfStudents() throws Exception{
        Student stu = new Student(1L,"asdf",22);
        when(studentRepository.getNumbersAllStudent()).thenReturn(1);     //эни лонг любое значение которое бы я не передал

        mockMvc.perform(get("/student/student/getNumberStudents","1"))
                .andExpect(status().isOk());

        when(studentRepository.findById(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/student/{id}","2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAvgAgeStudents() throws Exception{
        Student stu = new Student(1L,"asdf",22);
        when(studentRepository.getAvgAgeOfStudents()).thenReturn(22.0);     //эни лонг любое значение которое бы я не передал

        mockMvc.perform(get("/student//student/getAvgAgeStudents"))
                .andExpect(status().isOk());

        when(studentRepository.getAvgAgeOfStudents()).thenReturn(0.0);

        mockMvc.perform(get("/student/{id}","2"))
                .andExpect(status().isBadRequest());
    }

}
