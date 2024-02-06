package com.learning.springBoot.quizapp.controller;

import com.learning.springBoot.quizapp.model.Question;
import com.learning.springBoot.quizapp.model.QuestionWrapper;
import com.learning.springBoot.quizapp.model.Response;
import com.learning.springBoot.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> addQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.addQuiz(category, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResults(id, responses);
    }
}
