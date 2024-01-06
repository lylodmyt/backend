package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.TestDto;
import cz.cvut.fel.sit.backend.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/create")
    public ResponseEntity<TestDto> createTest(@RequestBody TestDto testDto) {
        try {
            TestDto createdTest = testService.createTest(testDto);
            return new ResponseEntity<>(createdTest, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TestDto> updateTest(@RequestBody TestDto testDto) {
        try {
            TestDto updatedTest = testService.updateTest(testDto);
            return new ResponseEntity<>(updatedTest, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTestById(@PathVariable Long id) {
        try {
            testService.deleteTestById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<TestDto>> getTestsByUsername(@PathVariable String username) {
        try {
            List<TestDto> userTests = testService.getTestsByUsername(username);
            return new ResponseEntity<>(userTests, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/published")
    public ResponseEntity<List<TestDto>> getPublishedTests() {
        List<TestDto> publishedTests = testService.getPublishedTest();
        return new ResponseEntity<>(publishedTests, HttpStatus.OK);
    }
}
