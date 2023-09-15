package com.example.scool_new;

import com.example.scool_new.controller.FacultyController;
import com.example.scool_new.controller.StudentController;
import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import com.example.scool_new.service.StudentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScoolNewApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception{
        org.assertj.core.api.Assertions.assertThat(facultyController).isNotNull();//проверка на нал (подтянулись ил бины и остальной контекст
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetNumberStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getNumberStudents", Student.class))
                .isNotNull();
    }

    @Test
    public void testPostStudents()throws Exception{
        Student stu = new Student();
        stu.setAge(22);
        stu.setName("vasa");
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/getNumberStudents", stu, Student.class))
                .isNotNull();
    }

    @Test
    public void testGetStudents()throws Exception{

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/1",  Student.class))
                .isNotNull();
    }

    @Test
    public void testDeleteStudents()throws Exception {

        Student stud = restTemplate.getForObject("http://localhost:" + port + "/student/", Student.class);
        assertNotNull(stud);
        restTemplate.delete("http://localhost:" + port + "/1");
        try {
            stud = restTemplate.getForObject("http://localhost:" + port + "/1", Student.class);
        } catch (final HttpClientErrorException e) {
            e.getStatusCode();
        }
    }




    @Test
    public void testPutStudents()throws Exception{

        Student stud = restTemplate.getForObject("http://localhost:" + port + "/student/" , Student.class);
        stud.setName("admin1");

        restTemplate.put("http://localhost:" + port + "/student/", stud);

        Student updatedStudent = restTemplate.getForObject("http://localhost:" + port + "/student/", Student.class);
        assertNotNull(updatedStudent);
    }

    @Test
    public void testGetAgeStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/age/22",Student.class))
                .isNotNull();
    }

    @Test
    public void testGetAgeFromAgeToStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sort/21,25",Student.class))
                .isNotNull();
    }

    @Test
    public void testFacultyOfStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1",Student.class))
                .isNotNull();
    }

    @Test
    public void testGetNumberOfStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getNumberStudents",Student.class))
                .isNotNull();
    }

    @Test
    public void testGetAvgAgeOfStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getAvgAgeStudents",Student.class))
                .isNotNull();
    }


    @Test
    public void testGetFiveLastOfStudents()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getFiveLastStudents",Student.class))
                .isNotNull();
    }

    @Test
    public void testGetFacultyId()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1",Faculty.class))
                .isNotNull();
    }

    @Test
    public void testGetColorOfFaculty()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/color/red",Faculty.class))
                .isNotNull();
    }

    @Test
    public void testGetColorOrNameOfFaculty()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/filter/red",Faculty.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentsOfFaculty()throws Exception{
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/1",Faculty.class))
                .isNotNull();
    }

    @Test
    public void testPostFaculty()throws Exception{
        Faculty fac = new Faculty();
        fac.setColor("red");
        fac.setName("qwer");
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student/getNumberStudents", fac, Faculty.class))
                .isNotNull();
    }

    @Test
    public void testDeleteFaculty()throws Exception {

        Faculty fac = restTemplate.getForObject("http://localhost:" + port + "/faculty/", Faculty.class);
        assertNotNull(fac);
        restTemplate.delete("http://localhost:" + port + "/1");
        try {
            fac = restTemplate.getForObject("http://localhost:" + port + "/1", Faculty.class);
        } catch (final HttpClientErrorException e) {
            e.getStatusCode();
        }
    }




    @Test
    public void testPutFaculty()throws Exception{

        Faculty fac = restTemplate.getForObject("http://localhost:" + port + "/student/" , Faculty.class);
        fac.setName("admin1");

        restTemplate.put("http://localhost:" + port + "/faculty/", fac);

        Faculty updatedFaculty = restTemplate.getForObject("http://localhost:" + port + "/faculty/", Faculty.class);
        assertNotNull(updatedFaculty);
    }

}
