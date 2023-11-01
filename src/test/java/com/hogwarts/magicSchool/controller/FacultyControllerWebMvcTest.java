package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyController facultyController;

    @Test
    public void testCreateFaculty() throws Exception {
        mockMvc.perform(post("/faculty/")
                        .param("name", "Puffenduy")
                        .param("color", "Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Puffenduy")))
                .andExpect(jsonPath("$.color", is("Blue")));

        verify(facultyController, times(1)).createFaculty("Puffenduy", "Blue");
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty("Puffenduy", "Blue");
        when(facultyController.getFacultyById(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Puffenduy")))
                .andExpect(jsonPath("$.color", is("Blue")));

        verify(facultyController, times(1)).getFacultyById(1L);
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        mockMvc.perform(put("/faculty/1")
                        .param("name", "Puffenduy")
                        .param("color", "Red"))
                .andExpect(status().isOk());

        verify(facultyController, times(1)).updateFaculty(1L, "Puffenduy", "Red");
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());

        verify(facultyController, times(1)).deleteFaculty(1L);
    }

    @Test
    public void testFilterFacultiesByColor() throws Exception {
        Collection<Faculty> faculties = new ArrayList<Faculty>(
                new Faculty("Puffenduy", "Blue"),
                new Faculty("Arts", "Red")
        );
        when(facultyController.filterFacultiesByColor("Blue")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/filterByColor")
                        .param("color", "Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Puffenduy")))
                .andExpect(jsonPath("$[0].color", is("Blue")))
                .andExpect(jsonPath("$[1].name", is("Arts")))
                .andExpect(jsonPath("$[1].color", is("Red")));

        verify(facultyController, times(1)).filterFacultiesByColor("Blue");
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        Collection<Faculty> faculties = new ArrayList<Faculty>(
                new Faculty("Puffenduy", "Blue"),
                new Faculty("Arts", "Red")
        );
        when(facultyController.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/faculty/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Puffenduy")))
                .andExpect(jsonPath("$[0].color", is("Blue")))
                .andExpect(jsonPath("$[1].name", is("Arts")))
                .andExpect(jsonPath("$[1].color", is("Red")));

        verify(facultyController, times(1)).getAllFaculties();
    }

    @Test
    public void testGetFacultiesByNameOrColorIgnoreCase() throws Exception {
        Collection<Faculty> faculties = new ArrayList<Faculty>(
                new Faculty("Puffenduy", "Blue"),
                new Faculty("Arts", "Red")
        );
        when(facultyController.getFacultiesByNameOrColorIgnoreCase("Puffenduy", null)).thenReturn(faculties.subCollection(0, 1));
        when(facultyController.getFacultiesByNameOrColorIgnoreCase(null, "Red")).thenReturn(faculties.subCollection(1, 2));

        mockMvc.perform(get("/faculty/faculties")
                        .param("name", "Puffenduy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Puffenduy")))
                .andExpect(jsonPath("$[0].color", is("Blue")));

        mockMvc.perform(get("/faculty/faculties")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Arts")))
                .andExpect(jsonPath("$[0].color", is("Red")));

        verify(facultyController, times(1)).getFacultiesByNameOrColorIgnoreCase("Puffenduy", null);
        verify(facultyController, times(1)).getFacultiesByNameOrColorIgnoreCase(null, "Red");
    }

    @Test
    public void testGetFacultyStudents() throws Exception {
        Faculty faculty = new Faculty("Puffenduy", "Blue");
        when(facultyController.getFacultyById(1L)).thenReturn(faculty);

        Collection<Student> students = new ArrayList<Faculty>(
                new Student("John", 20),
                new Student("Alice", 22)
        );
        faculty.setStudents(students);

        mockMvc.perform(get("/faculty/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].age", is(20)))
                .andExpect(jsonPath("$[1].name", is("Alice")))
                .andExpect(jsonPath("$[1].age", is(22)));

        verify(facultyController, times(1)).getFacultyById(1L);
    }
}