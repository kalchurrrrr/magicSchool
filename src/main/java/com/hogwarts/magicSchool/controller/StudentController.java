package com.hogwarts.magicSchool.controller;

import com.hogwarts.magicSchool.model.Student;
import com.hogwarts.magicSchool.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/")
    public Student createStudent(@RequestParam String name, @RequestParam int age) {
        return studentService.createStudent(name, age);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable Long id, @RequestParam String name, @RequestParam int age) {
        studentService.updateStudent(id, name, age);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
    @GetMapping("/filterByAge")
    public List<Student> filterStudentsByAge(@RequestParam int age) {
        return studentService.filterStudentsByAge(age);
    }
    @GetMapping("/")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
