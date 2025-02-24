package com.project.learningz.repository;

import com.project.learningz.entity.PDF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PdfRepository  extends JpaRepository<PDF, Long> {

    @Query("""
    SELECT pdf FROM PDF pdf WHERE pdf.lesson.id = ?1
""")
    List<PDF> findListByLessonId(int lessonId);

    @Query("""
    SELECT pdf FROM PDF pdf WHERE pdf.lesson.id = ?1 AND pdf.title LIKE CONCAT('%', ?2, '%') 
""")
    List<PDF> findDocs(int lessonId, String keyword);

    @Query("""
    SELECT pdf FROM PDF pdf WHERE pdf.id = ?1
""")
    PDF getPdfById(int id);

}
