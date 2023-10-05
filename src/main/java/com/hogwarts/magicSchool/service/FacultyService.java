package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.exceptions.CreateFacultyException;
import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(String name, String color) {
        if (facultyRepository.existsByNameAndColor(name, color)) {
            throw new CreateFacultyException("Факультет с таким же названием и цветом уже существует");
        }
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public void updateFaculty(Long id, String name, String color) {
        Faculty faculty = getFacultyById(id);
        if (faculty != null) {
            faculty.setName(name);
            faculty.setColor(color);
            facultyRepository.save(faculty);
        }
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> filterFacultiesByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }
    public Collection<Faculty> getFacultiesByNameIgnoreCase(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> getFacultiesByColorIgnoreCase(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }
}