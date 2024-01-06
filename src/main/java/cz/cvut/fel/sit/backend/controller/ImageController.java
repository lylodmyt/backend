package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.entities.Image;
import cz.cvut.fel.sit.backend.services.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageController {

    @Autowired
    private ImageServices imageServices;
    @PostMapping("/question/{questionId}")
    public ResponseEntity<?> addImageToQuestion(
            @RequestParam("image") MultipartFile file,
            @PathVariable Long questionId
    ) {
        try {
            imageServices.addImageToQuestion(file, questionId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException | IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getImageFromQuestion(@PathVariable Long questionId) {
        try {
            Image image = imageServices.getImageFromQuestion(questionId);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<?> deleteImageFromQuestion(@PathVariable Long questionId) {
        try {
            imageServices.deleteImageFromQuestion(questionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/answer/{answerId}")
    public ResponseEntity<?> addImageToAnswer(
            @RequestParam("image") MultipartFile file,
            @PathVariable Long answerId
    ) {
        try {
            imageServices.addImageToAnswer(file, answerId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException | IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/answer/{answerId}")
    public ResponseEntity<?> getImageFromAnswer(@PathVariable Long answerId) {
        try {
            Image image = imageServices.getImageFromAnswer(answerId);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<?> deleteImageFromAnswer(@PathVariable Long answerId) {
        try {
            imageServices.deleteImageFromAnswer(answerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
