package com.project.learningz.repository;

import com.project.learningz.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer>, JpaSpecificationExecutor<Subject> {

    @Query("""
    SELECT s FROM Subject s WHERE s.id = ?1
    """)
    Subject getSubjectById(int id);

    @Query("""
    SELECT s FROM Subject s WHERE s.name LIKE CONCAT('%',?1,'%') 
    """)
    List<Subject> getSubjectByKey(String key);
}
