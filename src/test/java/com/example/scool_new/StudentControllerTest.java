package com.example.scool_new;
import com.example.scool_new.controller.FacultyController;
import com.example.scool_new.controller.StudentController;
import com.example.scool_new.model.Faculty;
import com.example.scool_new.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;


    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        //проверка на нал (подтянулись ил бины и остальной контекст
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetNumberStudents() throws Exception {
        String url = "http://localhost:" + port + "/student/getNumberStudents";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);

    }

    @Test
    public void testPostStudents() throws Exception {
        String url = "http://localhost:" + port + "/student/getNumberStudents";
        Student stu = new Student();
        stu.setAge(22);
        stu.setName("vasa");
        Assertions.assertThat(this.restTemplate.postForObject(url, stu, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.POST,null,Student.class);
    }

    @Test
    public void testGetStudents() throws Exception {
        String url = "http://localhost:" + port + "/1";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);

    }

    @Test
    public void testDeleteStudents() throws Exception {

        Student stud = restTemplate.getForObject("http://localhost:" + port + "/student/", Student.class);
        assertNotNull(stud);
        restTemplate.delete("http://localhost:" + port + "/1");
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student/", HttpMethod.DELETE,null,Student.class);
        try {
            stud = restTemplate.getForObject("http://localhost:" + port + "/1", Student.class);
        } catch (final HttpClientErrorException e) {
            e.getStatusCode();
        }
    }


    @Test
    public void testPutStudents() throws Exception {

        Student stud = restTemplate.getForObject("http://localhost:" + port + "/student/", Student.class);
        stud.setName("admin1");

        restTemplate.put("http://localhost:" + port + "/student/", stud);

        Student updatedStudent = restTemplate.getForObject("http://localhost:" + port + "/student/", Student.class);
        assertNotNull(updatedStudent);
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student/", HttpMethod.PUT,null,Student.class);

    }

    @Test
    public void testGetAgeStudents() throws Exception {
        String url = "http://localhost:" + port + "/age/22";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);
        Assertions.assertThat(response.getBody().getAge());

    }

    @Test
    public void testGetAgeFromAgeToStudents() throws Exception {
        String url = "http://localhost:" + port + "/sort/21,25";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);
    }

    @Test
    public void testFacultyOfStudents() throws Exception {
        String url = "http://localhost:" + port + "/faculty/1";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);
    }

    @Test
    public void testGetNumberOfStudents() throws Exception {
        String url = "http://localhost:" + port + "/student/getNumberStudents";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);
    }

    @Test
    public void testGetAvgAgeOfStudents() throws Exception {
        String url = "http://localhost:" + port + "/student/getAvgAgeStudents";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);
    }


    @Test
    public void testGetFiveLastOfStudents() throws Exception {
        String url = "http://localhost:" + port + "/student/getFiveLastStudents";
        Assertions.assertThat(this.restTemplate.getForObject(url, Student.class))
                .isNotNull();
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.GET,null,Student.class);
    }
}