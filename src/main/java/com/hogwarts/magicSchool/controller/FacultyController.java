package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/faculty")

public class FacultyController {
    private final FacultyService facultyService;

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
    public void updateFaculty(@PathVariable Long id, @RequestParam String name, @RequestParam String color) {
        facultyService.updateFaculty(id, name, color);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
    @GetMapping("/filterByColor")
    public List<Faculty> filterFacultiesByColor(@RequestParam String color) {
        return facultyService.filterFacultiesByColor(color);
    }
    @GetMapping("/")
    public Map<Long, Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }
}

