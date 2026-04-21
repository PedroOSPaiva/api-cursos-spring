package com.pedro.api_cursos_spring.repository;


import com.pedro.api_cursos_spring.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);
}
