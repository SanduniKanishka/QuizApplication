package com.learning.springBoot.quizapp.dao;

import com.learning.springBoot.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{

}
