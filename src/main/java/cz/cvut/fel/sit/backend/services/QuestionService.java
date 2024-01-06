package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.QuestionDto;
import cz.cvut.fel.sit.backend.entities.Question;
import cz.cvut.fel.sit.backend.entities.Topic;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.QuestionRepository;
import cz.cvut.fel.sit.backend.repository.TopicRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    public QuestionDto createQuestion(QuestionDto questionDto){
        Optional<Topic> topic = topicRepository.findById(questionDto.getTopicId());
        if (topic.isPresent()){
            Question question = new Question();
            question.setText(questionDto.getTitle());
            question.setTopic(topic.get());
            Optional<User> user = userRepository.findByUsername(questionDto.getUsername());
            user.ifPresent(question::setUser);
            Question send = repository.save(question);
            return QuestionDto.covertToDto(send);
        } else {
            throw new EntityNotFoundException("Topic with id: " + questionDto.getTopicId() + " not found");
        }
    }

    @Transactional
    public void deleteQuestion(Long id){
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()){
            repository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Question with id: " + id + " not found");
        }
    }

    public QuestionDto updateQuestion(QuestionDto questionDto){
        Optional<Question> question = repository.findById(questionDto.getId());
        if (question.isPresent()){
            question.get().setText(questionDto.getTitle());
            Question send = repository.saveAndFlush(question.get());
            return QuestionDto.covertToDto(send);
        }else {
            throw new EntityNotFoundException("Question with id: " + questionDto.getId() + " not found");
        }
    }


    @Transactional
    public QuestionDto getQuestionById(Long id){
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()){
            return QuestionDto.covertToDto(question.get());
        }else {
            throw new EntityNotFoundException("Question with id: " + id + " not found");
        }
    }

    @Transactional
    public List<QuestionDto> getAllQuestionsByTopicId(Long id){
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()){
            return topic.get().getQuestions().stream().map(QuestionDto::covertToDto).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Topic with id: " + id + " not found");
        }
    }

    @Transactional
    public List<QuestionDto> getAllQuestionsByUsername(String username){

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            List<Question> questions = repository.findAllByUser(user.get());
            return questions.stream().map(QuestionDto::covertToDto).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }
    }
}
