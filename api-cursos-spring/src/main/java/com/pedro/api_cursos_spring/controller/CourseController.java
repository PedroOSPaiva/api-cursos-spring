package com.pedro.api_cursos_spring.controller;

import com.pedro.api_cursos_spring.entity.Course;
import com.pedro.api_cursos_spring.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    private final CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return repository.save(course);
    }

    @GetMapping
    public List<Course> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ) {
        if (name == null && category == null) {
            return repository.findAll();
        }

        String filterName = (name == null) ? "" : name;
        String filterCategory = (category == null) ? "" : category;

        return repository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(filterName, filterCategory);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable UUID id, @RequestBody Course updatedCourse) {

        Course course = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (updatedCourse.getName() != null) {
            course.setName(updatedCourse.getName());
        }

        if (updatedCourse.getCategory() != null) {
            course.setCategory(updatedCourse.getCategory());
        }

        return repository.save(course);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        repository.delete(course);
    }

    @PatchMapping("/{id}/active")
    public Course toggleActive(@PathVariable UUID id) {

        Course course = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        course.setActive(!course.getActive());

        return repository.save(course);
    }
}
