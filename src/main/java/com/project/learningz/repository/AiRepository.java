package com.project.learningz.repository;


import com.project.learningz.entity.AiFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRepository extends JpaRepository<AiFeedBack,Integer> {

}
