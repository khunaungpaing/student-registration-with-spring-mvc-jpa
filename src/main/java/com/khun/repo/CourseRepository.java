package com.khun.repo;

import com.khun.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.active = :active WHERE c.id = :courseId")
    void toggleActive(@Param("courseId") String courseId, @Param("active") boolean active);
}
