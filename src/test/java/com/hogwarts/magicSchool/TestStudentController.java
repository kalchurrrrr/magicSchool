package com.hogwarts.magicSchool;

import com.hogwarts.magicSchool.controller.FacultyController;
import com.hogwarts.magicSchool.controller.StudentController;
import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestStudentController {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStudent() {
        ResponseEntity<Student> response = restTemplate.postForEntity("/student/?name=John&age=20", null, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getName());
        assertEquals(20, response.getBody().getAge());
    }

    @Test
    public void testGetStudentById() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/1", Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getName());
        assertEquals(20, response.getBody().getAge());
    }

    @Test
    public void testUpdateStudent() {
        restTemplate.put("/student/1?name=John&age=25", null);
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/1", Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getName());
        assertEquals(25, response.getBody().getAge());
    }

    @Test
    public void testDeleteStudent() {
        restTemplate.delete("/student/1");
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/1", Student.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFilterStudentsByAge() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/filterByAge?age=20", Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("John", response.getBody()[0].getName());
        assertEquals(20, response.getBody()[0].getAge());
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/students/all", Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("John", response.getBody()[0].getName());
        assertEquals(20, response.getBody()[0].getAge());
    }

    @Test
    public void testGetStudentsByAgeRange() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/students?min=18&max=25", Student[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("John", response.getBody()[0].getName());
        assertEquals(20, response.getBody()[0].getAge());
    }

    @Test
    public void testGetStudentFaculty() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/1/faculty", Faculty.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}