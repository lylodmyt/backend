package cz.cvut.fel.sit.backend;

import cz.cvut.fel.sit.backend.dto.QuestionDto;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.Topic;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.QuestionRepository;
import cz.cvut.fel.sit.backend.repository.TopicRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
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
public class QuestionServiceTest {

    @Autowired
    private QuestionService service;

    @Autowired
    private QuestionRepository questionRepository;

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
    public void createQuestionTest() {
        User user = userRepository.save(initNewUser());

        Topic topic = new Topic();
        topic.setTitle("title");
        topic.setUser(user);
        Topic topicSave = topicRepository.save(topic);

        QuestionDto dto = new QuestionDto();
        dto.setTopicId(topicSave.getId());
        dto.setTitle("question");
        dto.setUsername(user.getUsername());
        QuestionDto res = service.createQuestion(dto);
        Assertions.assertNotNull(res.getId());
    }

    @Test
    public void deleteQuestionTest() {
        User user = userRepository.save(initNewUser());

        Topic topic = new Topic();
        topic.setTitle("title");
        topic.setUser(user);
        Topic topicSave = topicRepository.save(topic);

        QuestionDto dto = new QuestionDto();
        dto.setTopicId(topicSave.getId());
        dto.setTitle("question");
        dto.setUsername(user.getUsername());
        QuestionDto res = service.createQuestion(dto);
        Assertions.assertNotNull(res.getId());
        service.deleteQuestion(res.getId());
        Assertions.assertFalse(questionRepository.findById(res.getId()).isPresent());
    }

    @Test
    public void updateQuestionTest(){
        User user = userRepository.save(initNewUser());

        Topic topic = new Topic();
        topic.setTitle("title");
        topic.setUser(user);
        Topic topicSave = topicRepository.save(topic);

        QuestionDto dto = new QuestionDto();
        dto.setTopicId(topicSave.getId());
        dto.setTitle("question");
        dto.setUsername(user.getUsername());
        QuestionDto res = service.createQuestion(dto);
        Assertions.assertNotNull(res.getId());
        res.setTitle("new");
        QuestionDto res2 = service.updateQuestion(res);
        Assertions.assertEquals("new", res2.getTitle());
    }
}
