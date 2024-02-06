package com.learning.springBoot.quizapp.service;

import com.learning.springBoot.quizapp.dao.QuestionDao;
import com.learning.springBoot.quizapp.dao.QuizDao;
import com.learning.springBoot.quizapp.model.Question;
import com.learning.springBoot.quizapp.model.QuestionWrapper;
import com.learning.springBoot.quizapp.model.Quiz;
import com.learning.springBoot.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> addQuiz(String category, int numQ, String title){
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDb = quiz.get().getQuestions();
        List<QuestionWrapper> questionsToUsers = new ArrayList<>();
        for(Question q: questionsFromDb){
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),
                    q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsToUsers.add(questionWrapper);
        }
        return new ResponseEntity<>(questionsToUsers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResults(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int marks=0;
//        for(int i=0; i< responses.size(); i++){
//            int qId = responses.get(i).getId();
//            String response = responses.get(i).getResponse();
//            for(int j=0; j<questions.size();j++){
//                if(qId == questions.get(j).getId()){
//                    if(response.equals(questions.get(j).getRightAnswer())){
//                        marks++;
//                        break;
//                    }
//
//                }
//            }
//        }
        int i=0;
        for(Response response: responses){
            if (response.getResponse().equals(questions.get(i).getRightAnswer())){
                marks++;
            }
            i++;
        }
        return  new ResponseEntity<>(marks, HttpStatus.OK);
    }
}
