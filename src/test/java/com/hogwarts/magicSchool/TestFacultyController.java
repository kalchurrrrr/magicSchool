package com.hogwarts.magicSchool;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestFacultyController {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateFaculty() {
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty/?name=Science&color=Blue", null, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Science", response.getBody().getName());
        assertEquals("Blue", response.getBody().getColor());
    }

    @Test
    public void testGetFacultyById() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/1", Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Science", response.getBody().getName());
        assertEquals("Blue", response.getBody().getColor());
    }

    @Test
    public void testUpdateFaculty() {
        restTemplate.put("/faculty/1?name=Science&color=Red", null);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/1", Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Science", response.getBody().getName());
        assertEquals("Red", response.getBody().getColor());
    }

    @Test
    public void testDeleteFaculty() {
        restTemplate.delete("/faculty/1");
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/1", Faculty.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFilterFacultiesByColor() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty/filterByColor?color=Blue", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Science", response.getBody()[0].getName());
        assertEquals("Blue", response.getBody()[0].getColor());
    }

    @Test
    public void testGetAllFaculties() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty/", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Science", response.getBody()[0].getName());
        assertEquals("Blue", response.getBody()[0].getColor());
    }

    @Test
    public void testGetFacultiesByNameOrColorIgnoreCase() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty/faculties?name=Science", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Science", response.getBody()[0].getName());
        assertEquals("Blue", response.getBody()[0].getColor());

        response = restTemplate.getForEntity("/faculty/faculties?color=Blue", Faculty[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Science", response.getBody()[0].getName());
        assertEquals("Blue", response.getBody()[0].getColor());
    }

    @Test
    public void testGetFacultyStudents() {
        ResponseEntity<Collection<Student>> response = restTemplate.getForEntity("/faculty/1/students", new ParameterizedTypeReference<Collection<Student>>() {
        });
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}