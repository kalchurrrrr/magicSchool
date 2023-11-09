package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.repository.FacultyRepository;
import com.hogwarts.magicSchool.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Comparator;

@RestController
@RequestMapping("/faculty")

public class FacultyController {
    private final FacultyService facultyService;
    private FacultyRepository facultyRepository;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/")
    public Faculty createFaculty(@RequestParam String name, @RequestParam String color) {
        return facultyService.createFaculty(name, color);
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFaculty(@PathVariable Long id, @RequestParam String name, @RequestParam String color) {
        Faculty updatedFaculty = facultyService.updateFaculty(id, name, color);
        if (updatedFaculty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedFaculty, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
    @GetMapping("/filterByColor")
    public Collection<Faculty> filterFacultiesByColor(@RequestParam String color) {
        return facultyService.filterFacultiesByColor(color);
    }
    @GetMapping("/")
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }
    @GetMapping("/faculties")
    public Collection<Faculty> getFacultiesByNameOrColorIgnoreCase(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        if (name != null) {
            return facultyService.getFacultiesByNameIgnoreCase(name);
        } else if (color != null) {
            return facultyService.getFacultiesByColorIgnoreCase(color);
        } else {
            return facultyService.getAllFaculties();
        }
    }
    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        Collection<Student> students = faculty.getStudents();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/faculty/longest-name")
    public String getLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("");
    }
}