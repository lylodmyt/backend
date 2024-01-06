package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.AnswerDto;
import cz.cvut.fel.sit.backend.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/answer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AnswerController {


    @Autowired
    private AnswerService answerService;

    @PostMapping("/addAnswers/{questionId}")
    public ResponseEntity<List<AnswerDto>> addAnswersToQuestion(
            @RequestBody List<AnswerDto> answerDtos,
            @PathVariable Long questionId
    ) {
        try {
            List<AnswerDto> addedAnswers = answerService.addAnswersToQuestion(answerDtos, questionId);
            return new ResponseEntity<>(addedAnswers, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/{questionId}")
    public ResponseEntity<AnswerDto> addAnswerToQuestion(
            @RequestBody AnswerDto answerDto,
            @PathVariable Long questionId
    ) {
        try {
            AnswerDto addedAnswer = answerService.addAnswerToQuestion(answerDto, questionId);
            return new ResponseEntity<>(addedAnswer, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{questionId}")
    public ResponseEntity<List<AnswerDto>> getAnswersByQuestionId(@PathVariable Long questionId) {
        try {
            List<AnswerDto> answers = answerService.getAnswersByQuestionId(questionId);
            return new ResponseEntity<>(answers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAnswerById(@PathVariable Long id) {
        try {
            answerService.deleteAnswerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<AnswerDto> updateAnswer(@RequestBody AnswerDto answerDto) {
        try {
            AnswerDto updatedAnswer = answerService.updateAnswer(answerDto);
            return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
