package com.example.scool_new;

import com.example.scool_new.controller.FacultyController;
import com.example.scool_new.controller.StudentController;
import com.example.scool_new.model.Faculty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception{
        org.assertj.core.api.Assertions.assertThat(facultyController).isNotNull();//проверка на нал (подтянулись ил бины и остальной контекст
    }


    @Test
    public void testGetFacultyId()throws Exception{
        String url = "http://localhost:" + port + "/faculty/1";
        Assertions.assertThat(this.restTemplate.getForObject(url, Faculty.class))
                .isNotNull();
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.GET,null,Faculty.class);
    }

    @Test
    public void testGetColorOfFaculty()throws Exception{
        String url = "http://localhost:" + port + "/color/red";

        Assertions.assertThat(this.restTemplate.getForObject(url,Faculty.class))
                .isNotNull();
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.GET,null,Faculty.class);
    }

    @Test
    public void testGetColorOrNameOfFaculty()throws Exception{
        String url = "http://localhost:" + port + "/filter/red";
        Assertions.assertThat(this.restTemplate.getForObject(url,Faculty.class))
                .isNotNull();
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.GET,null,Faculty.class);
    }

    @Test
    public void testGetStudentsOfFaculty()throws Exception{
        String url = "http://localhost:" + port + "/students/1";
        Assertions.assertThat(this.restTemplate.getForObject(url,Faculty.class))
                .isNotNull();
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.GET,null,Faculty.class);
    }

    @Test
    public void testPostFaculty()throws Exception{
        String url ="http://localhost:" + port + "/student/getNumberStudents";
        Faculty fac = new Faculty();
        fac.setColor("red");
        fac.setName("qwer");
        Assertions.assertThat(this.restTemplate.postForObject(url, fac, Faculty.class))
                .isNotNull();
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.POST,null,Faculty.class);
    }

    @Test
    public void testDeleteFaculty()throws Exception {
        String url = "http://localhost:" + port + "/faculty/";
        Faculty fac = restTemplate.getForObject(url, Faculty.class);
        assertNotNull(fac);
        restTemplate.delete("http://localhost:" + port + "/1");
        ResponseEntity<Faculty> response = restTemplate.exchange(url, HttpMethod.DELETE,null,Faculty.class);
        try {
            fac = restTemplate.getForObject("http://localhost:" + port + "/1", Faculty.class);
        } catch (final HttpClientErrorException e) {
            e.getStatusCode();
        }
    }




    @Test
    public void testPutFaculty()throws Exception{

        Faculty fac = restTemplate.getForObject("http://localhost:" + port + "/faaculty/" , Faculty.class);
        fac.setName("admin1");

        restTemplate.put("http://localhost:" + port + "/faculty/", fac);

        Faculty updatedFaculty = restTemplate.getForObject("http://localhost:" + port + "/faculty/", Faculty.class);
        assertNotNull(updatedFaculty);
        ResponseEntity<Faculty> response = restTemplate.exchange("http://localhost:" + port + "/faculty/", HttpMethod.PUT,null,Faculty.class);
    }

}
