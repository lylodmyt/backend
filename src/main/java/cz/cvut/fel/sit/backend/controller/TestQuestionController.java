package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.TestQuestionDto;
import cz.cvut.fel.sit.backend.services.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/testQuestion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestQuestionController {

    @Autowired
    private TestQuestionService testQuestionService;

    @GetMapping("/get/{id}")
    public ResponseEntity<List<TestQuestionDto>> getTestQuestionsByTestId(@PathVariable Long id) {
        try {
            List<TestQuestionDto> testQuestions = testQuestionService.getTestQuestionsByTestId(id);
            return new ResponseEntity<>(testQuestions, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add/{testId}/{questionId}")
    public ResponseEntity<TestQuestionDto> addQuestionToTest(
            @PathVariable Long testId,
            @PathVariable Long questionId
    ) {
        try {
            TestQuestionDto addedTestQuestion = testQuestionService.addQuestionToTest(testId, questionId);
            return new ResponseEntity<>(addedTestQuestion, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TestQuestionDto> removeQuestionFromTest(@PathVariable Long id) {
        try {
            TestQuestionDto removedTestQuestion = testQuestionService.removeQuestionFromTest(id);
            return new ResponseEntity<>(removedTestQuestion, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
