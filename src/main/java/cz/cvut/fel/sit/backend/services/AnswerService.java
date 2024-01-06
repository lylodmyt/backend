package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.AnswerDto;
import cz.cvut.fel.sit.backend.entities.Answer;
import cz.cvut.fel.sit.backend.entities.Question;
import cz.cvut.fel.sit.backend.repository.AnswerRepository;
import cz.cvut.fel.sit.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository repository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<AnswerDto> addAnswersToQuestion(List<AnswerDto> answerDtos, Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        List<AnswerDto> dtos = new ArrayList<>();
        if (question.isPresent()){
            for (AnswerDto dto : answerDtos){
                Answer answer = new Answer();
                answer.setQuestion(question.get());
                answer.setText(dto.getText());
                answer.setTrue(dto.isCorrect());
                Answer send = repository.save(answer);
                dtos.add(AnswerDto.convertToDto(send));
            }
            return dtos;
        } else {
            throw new EntityNotFoundException("Question with id: " + questionId + " not found");
        }
    }
    public AnswerDto addAnswerToQuestion(AnswerDto answerDto, Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()){
            Answer answer = new Answer();
            answer.setQuestion(question.get());
            answer.setText(answerDto.getText());
            answer.setTrue(answerDto.isCorrect());
            Answer send = repository.save(answer);
            return AnswerDto.convertToDto(send);
        } else {
            throw new EntityNotFoundException("Question with id: " + questionId + " not found");
        }
    }

    public List<AnswerDto> getAnswersByQuestionId(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()){
            List<AnswerDto> dtos = new ArrayList<>();
            question.get().getAnswers().forEach(answer -> dtos.add(AnswerDto.convertToDto(answer)));
            return dtos;
        }else {
            throw new EntityNotFoundException("Question with id: " + questionId + " not found");
        }
    }

    @Transactional
    public void deleteAnswerById(Long id){
        Optional<Answer> answer = repository.findById(id);
        if (answer.isPresent()){
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Answer with id: " + id + " not found");
        }
    }

    public AnswerDto updateAnswer(AnswerDto answerDto){
        Optional<Answer> answer = repository.findById(answerDto.getId());
        if(answer.isPresent()){
            if (answerDto.getText() != null){
                answer.get().setText(answerDto.getText());
            }
            answer.get().setTrue(answerDto.isCorrect());
            Answer save = repository.saveAndFlush(answer.get());
            return AnswerDto.convertToDto(save);
        } else {
            throw new EntityNotFoundException("Answer with id: " + answerDto.getId() + " not found");
        }
    }


}
