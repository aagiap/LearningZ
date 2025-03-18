package com.project.learningz.repository;

import com.project.learningz.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE " +
            "(:gradeId IS NULL OR p.grade.id = :gradeId) AND " +
            "(:subjectId IS NULL OR p.subject.id = :subjectId)")
    Page<Post> findByGradeAndSubject(@Param("gradeId") Integer gradeId,
                                     @Param("subjectId") Integer subjectId,
                                     Pageable pageable);

}
