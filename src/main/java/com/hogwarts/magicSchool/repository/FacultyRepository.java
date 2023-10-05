package com.hogwarts.magicSchool.repository;

import com.hogwarts.magicSchool.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    boolean existsByNameAndColor(String name, String color);

    List<Faculty> findByColorIgnoreCase(String color);
}
