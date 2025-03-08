package com.project.learningz.repository;

import com.project.learningz.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(Integer userId);
    List<Post> findByGrade_Id(Integer gradeId);
    Page<Post> findAll(Pageable pageable);


}
