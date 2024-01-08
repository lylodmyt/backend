package cz.cvut.fel.sit.backend;

import cz.cvut.fel.sit.backend.dto.AnswerDto;
import cz.cvut.fel.sit.backend.dto.QuestionDto;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.Topic;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.AnswerRepository;
import cz.cvut.fel.sit.backend.repository.TopicRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.services.AnswerService;
import cz.cvut.fel.sit.backend.services.QuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.liquibase.enabled=false"})
public class AnswerServiceTest {

    @Autowired
    AnswerRepository repository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    private static int identification = 1;

    public static User initNewUser() {
        User user = new User();
        user.setUsername("username" + identification);
        user.setPassword("test");
        user.setRole(Role.USER);
        user.setEmail("test@user.com" + identification);
        identification++;
        return user;
    }

    @Test
    public void addAnswerToQuestionTest() {
        User user = userRepository.save(initNewUser());

        Topic topic = new Topic();
        topic.setTitle("title");
        topic.setUser(user);
        Topic topicSave = topicRepository.save(topic);

        QuestionDto dto = new QuestionDto();
        dto.setTopicId(topicSave.getId());
        dto.setTitle("question");
        dto.setUsername(user.getUsername());
        QuestionDto res = questionService.createQuestion(dto);

        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuestionId(res.getId());
        answerDto.setCorrect(false);
        answerDto.setText("text");
        answerService.addAnswerToQuestion(answerDto, res.getId());

        QuestionDto questionDto = questionService.getQuestionById(res.getId());
        Assertions.assertEquals(1, questionDto.getAnswers().size());
    }

    @Test
    public void deleteAnswerTest() {
        User user = userRepository.save(initNewUser());

        Topic topic = new Topic();
        topic.setTitle("title");
        topic.setUser(user);
        Topic topicSave = topicRepository.save(topic);

        QuestionDto dto = new QuestionDto();
        dto.setTopicId(topicSave.getId());
        dto.setTitle("question");
        dto.setUsername(user.getUsername());
        QuestionDto question = questionService.createQuestion(dto);

        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuestionId(question.getId());
        answerDto.setCorrect(false);
        answerDto.setText("text");
        AnswerDto answer = answerService.addAnswerToQuestion(answerDto, question.getId());

        answerService.deleteAnswerById(answer.getId());
        Assertions.assertFalse(repository.findById(answer.getId()).isPresent());
    }
}
