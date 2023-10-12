package com.hogwarts.magicSchool;

import com.hogwarts.magicSchool.controller.FacultyController;
import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestFacultyController {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void createFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Griffinor");
        faculty.setColor("red");

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty/", faculty, String.class))
                .isNotNull();
    }

    @Test
    void getFacultyById() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Griffindor");
        faculty.setColor("red");

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty/", faculty, String.class)).isNotNull();

    }


    @Test
    void updateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Slytherin");
        faculty.setColor("green");

        this.restTemplate.put("http://localhost:" + port + "/faculty/", faculty);
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class))
                .isEqualTo(faculty);
    }

    @Test
    void deleteFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Griffindor");
        faculty.setColor("red");

        this.restTemplate.delete("http://localhost:" + port + "/faculty/" + faculty.getId());
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId(), Faculty.class))
                .isNull();
    }

    @Test
    void filterFacultiesByColor() throws Exception {
        String colorSearch = "red";
        Faculty faculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + colorSearch, Faculty.class);
        Assertions.assertThat(faculty).isNotNull();
        Assertions.assertThat(faculty.getColor().toLowerCase()).isEqualTo(colorSearch.toLowerCase());
    }

    @Test
    void getAllFaculties() throws Exception {
        Faculty faculties = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/", Faculty.class);
        Assertions.assertThat(faculties).isNotNull();
    }

    @Test
    void getFacultiesByNameOrColorIgnoreCase() throws Exception {
        String searchTerm = "griffindor";
        Faculty faculty = this.restTemplate.getForObject("http://localhost:" + port + "/faculties" + searchTerm, Faculty.class);
        Assertions.assertThat(faculty).isNotNull();
        Assertions.assertThat(faculty.getName().toLowerCase()).isEqualTo(searchTerm.toLowerCase());
    }

    @Test
    void getFacultyStudents() throws Exception {
        int facultyId = 1;
        Student students = this.restTemplate.getForObject("http://localhost:" + port + "/faculty/" + facultyId + "/students", Student.class);
        Assertions.assertThat(students).isNotNull();
    }
}