package com.hogwarts.magicSchool.repository;

import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean existsByNameAndColor(String name, String color);
    Collection<Faculty> findByNameIgnoreCase(String name);
    Collection<Faculty> findByColorIgnoreCase(String color);
    Faculty findByStudentsContains(Student student);
}
