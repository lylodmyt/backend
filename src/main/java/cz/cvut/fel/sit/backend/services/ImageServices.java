package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.entities.Answer;
import cz.cvut.fel.sit.backend.entities.Image;
import cz.cvut.fel.sit.backend.entities.Question;
import cz.cvut.fel.sit.backend.repository.AnswerRepository;
import cz.cvut.fel.sit.backend.repository.ImageRepository;
import cz.cvut.fel.sit.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServices {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public void addImageToQuestion(MultipartFile file, Long questionId) throws IOException {
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            throw new EntityNotFoundException("Question with id: " + questionId + " doesn't exist");
        }
        Image image = repository.save(fileToImageEntity(file));
        question.get().setImage(image);
        questionRepository.saveAndFlush(question.get());
    }

    public Image getImageFromQuestion(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            throw new EntityNotFoundException("Question with id: " + questionId + " doesn't exist");
        }
        Image image = question.get().getImage();
        if (image == null){
            throw new EntityNotFoundException("There is not image in question with id " + questionId);
        }
        return image;
    }

    @Transactional
    public void deleteImageFromQuestion(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        if (!question.isPresent()) {
            throw new EntityNotFoundException("Question with id: " + questionId + " doesn't exist");
        }
        if (question.get().getImage() != null){
            repository.deleteById(question.get().getImage().getId());
            question.get().setImage(null);
            questionRepository.saveAndFlush(question.get());
        }
    }

    @Transactional
    public void addImageToAnswer(MultipartFile file, Long answerId) throws IOException {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            throw new EntityNotFoundException("Question with id: " + answerId + " doesn't exist");
        }
        Image image = repository.save(fileToImageEntity(file));
        answer.get().setImage(image);
        answerRepository.saveAndFlush(answer.get());
    }

    public Image getImageFromAnswer(Long answerId){
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            throw new EntityNotFoundException("Question with id: " + answerId + " doesn't exist");
        }
        Image image = answer.get().getImage();
        if (image == null){
            throw new EntityNotFoundException("There is not image in question with id " + answerId);
        }
        return image;
    }

    @Transactional
    public void deleteImageFromAnswer(Long answerId){
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (!answer.isPresent()) {
            throw new EntityNotFoundException("Question with id: " + answerId + " doesn't exist");
        }
        if (answer.get().getImage() != null){
            repository.deleteById(answer.get().getImage().getId());
            answer.get().setImage(null);
            answerRepository.saveAndFlush(answer.get());
        }
    }

    private Image fileToImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setFilename(file.getOriginalFilename());
        image.setBytes(file.getBytes());
        image.setName(file.getName());
        image.setContentType(file.getContentType());
        return image;
    }
}
