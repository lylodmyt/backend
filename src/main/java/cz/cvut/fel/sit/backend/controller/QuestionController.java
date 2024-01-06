package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.QuestionDto;
import cz.cvut.fel.sit.backend.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/question")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto) {
        try {
            QuestionDto createdQuestion = questionService.createQuestion(questionDto);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto) {
        try {
            QuestionDto updatedQuestion = questionService.updateQuestion(questionDto);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        try {
            QuestionDto foundQuestion = questionService.getQuestionById(id);
            return new ResponseEntity<>(foundQuestion, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<QuestionDto>> getAllQuestionsByTopicId(@PathVariable Long id) {
        try {
            List<QuestionDto> questions = questionService.getAllQuestionsByTopicId(id);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll/user/{username}")
    public ResponseEntity<List<QuestionDto>> getAllQuestionsByUsername(@PathVariable String username) {
        try {
            List<QuestionDto> questions = questionService.getAllQuestionsByUsername(username);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
