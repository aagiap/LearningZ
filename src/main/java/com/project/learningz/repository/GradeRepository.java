package com.project.learningz.repository;

import com.project.learningz.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer>, JpaSpecificationExecutor<Grade> {
    Grade findByName(String name);

    @Query("SELECT DISTINCT g.name FROM Grade g")
    List<String> findDistinctGradeName();

    @Query("SELECT g FROM Grade g WHERE g.id=?1")
    Grade findById(int gradeId);
}

