package edu.yuri.elearning.mapper;

import edu.yuri.elearning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
