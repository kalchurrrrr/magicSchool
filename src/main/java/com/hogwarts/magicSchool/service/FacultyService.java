package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.exceptions.CreateFacultyException;
import com.hogwarts.magicSchool.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties;
    private Long counter;

    public FacultyService() {
        this.faculties = new HashMap<>();
        this.counter = 1L;
    }

    public Faculty createFaculty(String name, String color) {
        for (Faculty faculty : faculties.values()) {
            if (faculty.getName().equals(name) && faculty.getColor().equals(color)) {
                throw new CreateFacultyException("Факультет с таким же названием и цветом уже существует");
            }
        }
        Faculty faculty = new Faculty(counter, name, color);
        faculties.put(counter, faculty);
        counter++;
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public void updateFaculty(Long id, String name, String color) {
        if (faculties.containsKey(id)) {
            Faculty faculty = faculties.get(id);
            faculty.setName(name);
            faculty.setColor(color);
        }
    }

    public void deleteFaculty(Long id) {
        faculties.remove(id);
    }
    public List<Faculty> filterFacultiesByColor(String color) {
        List<Faculty> filteredFaculties = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (faculty.getColor().equalsIgnoreCase(color)) {
                filteredFaculties.add(faculty);
            }
        }
        return filteredFaculties;
    }
    public Map<Long, Faculty> getAllFaculties() {
        return faculties;
    }
}

