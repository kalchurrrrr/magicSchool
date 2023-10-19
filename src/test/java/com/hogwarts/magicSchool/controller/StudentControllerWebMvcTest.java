package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.service.studentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.reflect.Array.get;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)

public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentController studentController;

    @Test
    public void testCreateStudent() throws Exception {
        mockMvc.perform(post("/student/")
                        .param("name", "Ivan")
                        .param("age", "18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ivan")))
                .andExpect(jsonPath("$.age", is(18)));

        verify(studentController, times(1)).createStudent("Ivan", 18);
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student("Ivan", 18);
        when(studentController.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ivan")))
                .andExpect(jsonPath("$.age", is(18)));

        verify(studentController, times(1)).getStudentById(1L);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        mockMvc.perform(put("/student/1")
                        .param("name", "Alex")
                        .param("age", "25"))
                .andExpect(status().isOk());

        verify(studentController, times(1)).updateStudent(1L, "Alex", 25);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());

        verify(studentController, times(1)).deleteStudent(1L);
    }

    @Test
    public void testFilterStudentsByAge() throws Exception {
        Collection<Student> students = new ArrayList<Student>();
                new Student("Ivan", 20),
                new Student("Alice", 22)
        );
        when(studentController.filterStudentsByAge(20)).thenReturn(students);

        mockMvc.perform(get("/student/filterByAge")
                        .param("age", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("ivan")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[1].name", is("Alice")))
                .andExpect(jsonPath("$[1].age", is(22)));

        verify(studentController, times(1)).filterStudentsByAge(20);
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Collection<Student> students = new ArrayList<Student>(
                new Student("John", 20),
                new Student("Alice", 22)
        );
        when(studentController.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/student/students/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[1].name", is("Alice")))
                .andExpect(jsonPath("$[1].age", is(22)));

        verify(studentController, times(1)).getAllStudents();
    }

    @Test
    public void testGetStudentsByAgeRange() throws Exception {
        Collection<Student> students = new ArrayList<Student>(
                new Student("John", 20),
                new Student("Alice", 22)
        );
        when(studentController.getStudentsByAgeRange(18, 25)).thenReturn(students);

        mockMvc.perform(get("/student/")
                        .param("min", "18")
                        .param("max", "25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[1].name", is("Alice")))
                .andExpect(jsonPath("$[1].age", is(22)));

        verify(studentController, times(1)).getStudentsByAgeRange(18, 25);
    }

    @Test
    public void testGetStudentFaculty() throws Exception {
        when(studentController.getStudentById(1L)).thenReturn(null);

        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(status().isNotFound());

        verify(studentController, times(1)).getStudentById(1L);
    }
}