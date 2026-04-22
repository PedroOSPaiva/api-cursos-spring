package com.pedro.api_cursos_spring.controller;

import com.pedro.api_cursos_spring.entity.Course;
import com.pedro.api_cursos_spring.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class CoursePageController {

    private final CourseRepository courseRepository;

    public CoursePageController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses/list";
    }

    @GetMapping("/courses/new")
    public String newCourseForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("isEdit", false);
        return "courses/form";
    }

    @PostMapping("/courses")
    public String saveCourse(Course course) {
        courseRepository.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{id}")
    public String courseDetails(@PathVariable UUID id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        model.addAttribute("course", course);

        return "courses/details";
    }

    @GetMapping("/courses/{id}/edit")
    public String editCourseForm(@PathVariable UUID id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        model.addAttribute("course", course);
        model.addAttribute("isEdit", true);

        return "courses/form";
    }

    @PostMapping("/courses/{id}")
    public String updateCourse(@PathVariable UUID id, Course course) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        existingCourse.setName(course.getName());
        existingCourse.setCategory(course.getCategory());
        existingCourse.setActive(course.getActive());

        courseRepository.save(existingCourse);

        return "redirect:/courses/" + id;
    }

    @PostMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        courseRepository.delete(course);

        return "redirect:/courses";
    }
}