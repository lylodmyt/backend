package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.TestQuestionDto;
import cz.cvut.fel.sit.backend.entities.Question;
import cz.cvut.fel.sit.backend.entities.Test;
import cz.cvut.fel.sit.backend.entities.TestQuestion;
import cz.cvut.fel.sit.backend.repository.QuestionRepository;
import cz.cvut.fel.sit.backend.repository.TestQuestionRepository;
import cz.cvut.fel.sit.backend.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestQuestionService {

    @Autowired
    TestQuestionRepository repository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Transactional
    public List<TestQuestionDto> getTestQuestionsByTestId(Long id){
        Optional<Test> test = testRepository.findById(id);
        if (test.isPresent()){
            return test.get().getTestQuestions().stream().map(TestQuestionDto::convertToDto).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Topic with id: " + id + " not found");
        }
    }

    @Transactional
    public TestQuestionDto addQuestionToTest(Long testId, Long questionId){
        Optional<Test> test = testRepository.findById(testId);
        Optional<Question> question = questionRepository.findById(questionId);
        if (!test.isPresent()){
            throw new EntityNotFoundException("Test with id: " + testId + "doesn't exist");
        }
        if (!question.isPresent()){
            throw new EntityNotFoundException("Question with id: " + testId + "doesn't exist");
        }
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setTest(test.get());
        testQuestion.setQuestion(question.get());

        TestQuestion send = repository.save(testQuestion);
        return TestQuestionDto.convertToDto(send);
    }

    @Transactional
    public TestQuestionDto removeQuestionFromTest(Long questionId){
        Optional<TestQuestion> testQuestion = repository.findById(questionId);
        if (!testQuestion.isPresent()){
            throw new EntityNotFoundException("TestQuestion with id: " + questionId + "doesn't exist");
        }
        repository.delete(testQuestion.get());
        return TestQuestionDto.convertToDto(testQuestion.get());
    }

    @Transactional
    public void deleteAllByTestId(Long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (test.isPresent()) {
            List<TestQuestion> testQuestions = test.get().getTestQuestions();

            repository.deleteAll(testQuestions);
        } else {
            throw new EntityNotFoundException("Test with id: " + testId + " not found");
        }
    }
}
