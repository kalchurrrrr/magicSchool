package com.hogwarts.magicSchool.service;

import com.hogwarts.magicSchool.exceptions.CreateFacultyException;
import com.hogwarts.magicSchool.model.Faculty;
import com.hogwarts.magicSchool.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(String name, String color) {
        logger.info("Был вызван метод для создания факультета. Название: {}, Цвет: {}", name, color);

        if (facultyRepository.existsByNameAndColor(name, color)) {
            throw new CreateFacultyException("Факультет с таким же названием и цветом уже существует");
        }

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);

        Faculty createdFaculty = facultyRepository.save(faculty);
        logger.info("Успешно создан факультет: {}", createdFaculty);

        return createdFaculty;
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Был вызван метод для получения факультета по ID: {}", id);

        Optional<Faculty> facultyOptional = facultyRepository.findById(id);
        Faculty faculty = facultyOptional.orElse(null);

        if (faculty == null) {
            logger.warn("Факультет не найден для ID: {}", id);
        } else {
            logger.info("Получен факультет по ID: {}", id);
        }

        return faculty;
    }
    public Faculty updateFaculty(Long id, String name, String color) {
        logger.info("Был вызван метод для обновления факультета. Идентификатор факультета: {}, Новое название: {}, Новый цвет: {}", id, name, color);

        Faculty faculty = facultyRepository.findById(id).orElse(null);

        if (faculty != null) {
            logger.info("Обновление факультета. Идентификатор факультета: {}, Новое название: {}, Новый цвет: {}", id, name, color);

            faculty.setName(name);
            faculty.setColor(color);
            Faculty updatedFaculty = facultyRepository.save(faculty);

            logger.info("Факультет успешно обновлен. Идентификатор факультета: {}", id);
            return updatedFaculty;
        } else {
            logger.warn("Факультет не найден по ID: {}. Операция обновления пропущена.", id);
            return null;
        }
    }

    public void deleteFaculty(Long id) {
        logger.info("Был вызван метод для удаления факультета. Идентификатор факультета: {}", id);

        try {
            facultyRepository.deleteById(id);
            logger.info("Факультет успешно удален. Идентификатор факультета: {}", id);
        } catch (Exception e) {
            logger.error("Произошла ошибка при удалении факультета. Идентификатор факультета: {}", id, e);
        }
    }
    public Collection<Faculty> filterFacultiesByColor(String color) {
        logger.info("Был вызван метод для фильтрации факультетов по цвету: {}", color);

        Collection<Faculty> filteredFaculties = facultyRepository.findByColorIgnoreCase(color);

        logger.info("Найдено {} факультетов с цветом: {}", filteredFaculties.size(), color);

        return filteredFaculties;
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Был вызван метод для получения всех факультетов");

        Collection<Faculty> allFaculties = facultyRepository.findAll();

        logger.info("Получено {} факультетов", allFaculties.size());

        return allFaculties;
    }
    public Collection<Faculty> getFacultiesByNameIgnoreCase(String name) {
        logger.info("Был вызван метод для получения факультетов по названию (игнорируя регистр): {}", name);

        Collection<Faculty> faculties = facultyRepository.findByNameIgnoreCase(name);

        logger.info("Получено {} факультетов с названием (игнорируя регистр): {}", faculties.size(), name);

        return faculties;
    }

    public Collection<Faculty> getFacultiesByColorIgnoreCase(String color) {
        logger.info("Был вызван метод для получения факультетов по цвету (игнорируя регистр): {}", color);

        Collection<Faculty> faculties = facultyRepository.findByColorIgnoreCase(color);

        logger.info("Получено {} факультетов с цветом (игнорируя регистр): {}", faculties.size(), color);

        return faculties;
    }
}