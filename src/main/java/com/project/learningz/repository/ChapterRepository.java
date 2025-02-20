package com.project.learningz.repository;

import com.project.learningz.dto.ChapterDetailDTO;
import com.project.learningz.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findByCourseId(Integer courseId);

    Chapter findChapterById(Integer chapterId);

    @Query("""
    SELECT new com.project.learningz.dto.ChapterDetailDTO(
        ch.id,
        ch.chapterOrder,
        c.title,
        ch.chapterTitle,
        ch.description,
        ch.chapterDriveLink,
        COUNT(l)
    )
    FROM Chapter AS ch 
    LEFT JOIN Course AS c ON ch.course.id = c.id
    LEFT JOIN ch.lessons l
    WHERE c.id = ?1
    GROUP BY ch.id,ch.chapterOrder,c.title,
        ch.chapterTitle,ch.description,ch.chapterDriveLink
    ORDER BY ch.chapterOrder
""")
    List<ChapterDetailDTO> allChapterByCourseId(int courseId);

    @Query("""
    SELECT new com.project.learningz.dto.ChapterDetailDTO(
        ch.id,
        ch.chapterOrder,
        c.title,
        ch.chapterTitle,
        ch.description,
        ch.chapterDriveLink,
        COUNT(l)
    )
    FROM Chapter AS ch 
    LEFT JOIN Course AS c ON ch.course.id = c.id
    LEFT JOIN ch.lessons l
    WHERE c.id = ?1 AND ch.chapterTitle LIKE CONCAT('%', ?2, '%')
    GROUP BY ch.id,ch.chapterOrder,c.title,
        ch.chapterTitle,ch.description,ch.chapterDriveLink
    ORDER BY ch.chapterOrder
""")
    List<ChapterDetailDTO> findChapters(int courseId, String keyword);

    @Query("SELECT ch FROM Chapter ch WHERE ch.course.id = ?1 AND ch.chapterOrder = ?2")
    Chapter findChapter(Integer courseId, Integer order);
}