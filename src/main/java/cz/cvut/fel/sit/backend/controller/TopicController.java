package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.TopicDto;
import cz.cvut.fel.sit.backend.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TopicController {


    @Autowired
    TopicService topicService;

    @PostMapping("/create")
    public ResponseEntity<TopicDto> createTopic(@RequestBody TopicDto topicDto) {
        try {
            TopicDto createdTopic = topicService.createTopic(topicDto);
            return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTopicById(@PathVariable Long id) {
        try {
            topicService.deleteTopicById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/rename")
    public ResponseEntity<TopicDto> renameTopic(@RequestBody TopicDto topicDto) {
        try {
            TopicDto renamedTopic = topicService.renameTopic(topicDto);
            return new ResponseEntity<>(renamedTopic, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parent/{username}")
    public ResponseEntity<List<TopicDto>> getParentTopicsByUsername(@PathVariable String username) {
        try {
            List<TopicDto> parentTopics = topicService.getParentTopicsByUsername(username);
            return new ResponseEntity<>(parentTopics, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subtopics/{id}")
    public ResponseEntity<List<TopicDto>> getSubtopicsByTopicId(@PathVariable Long id) {
        try {
            List<TopicDto> subtopics = topicService.getSubtopicsByTopicId(id);
            return new ResponseEntity<>(subtopics, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
