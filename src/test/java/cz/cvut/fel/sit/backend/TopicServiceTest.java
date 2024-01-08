package cz.cvut.fel.sit.backend;


import cz.cvut.fel.sit.backend.dto.TopicDto;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.TopicRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.services.TopicService;
import cz.cvut.fel.sit.backend.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.liquibase.enabled=false"})
public class TopicServiceTest {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    TopicService topicService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static int identification = 1;
    public static User initNewUser() {
        User user = new User();
        user.setUsername("username" + identification);
        user.setPassword("test");
        user.setRole(Role.USER);
        user.setEmail("test@user.com");
        identification++;
        return user;
    }


    @Test
    public void createTopicTest() {
        User user = initNewUser();
        userRepository.save(user);
        TopicDto dto = new TopicDto();
        dto.setTitle("title");
        dto.setUsername(user.getUsername());
        TopicDto res = topicService.createTopic(dto);
        Assertions.assertTrue(topicRepository.findById(res.getId()).isPresent());
    }

    @Test
    public void createSubTopic() {
        User user = initNewUser();
        userRepository.save(user);
        TopicDto dto = new TopicDto();
        dto.setTitle("title");
        dto.setUsername(user.getUsername());
        TopicDto topic = topicService.createTopic(dto);

        TopicDto dto2 = new TopicDto();
        dto2.setTitle("title2");
        dto2.setUsername(user.getUsername());
        dto2.setParentId(topicRepository.findAll().get(0).getId());
        TopicDto res = topicService.createTopic(dto2);
        Assertions.assertEquals(topicRepository.findById(res.getId()).get().getParentTopic().getId(), topic.getId());
    }

    @Test
    public void renameTopicTest(){
        User user = initNewUser();
        userRepository.save(user);
        TopicDto dto = new TopicDto();
        dto.setTitle("title");
        dto.setUsername(user.getUsername());
        TopicDto topic = topicService.createTopic(dto);
        topic.setTitle("newTitle");
        topicService.renameTopic(topic);
        Assertions.assertEquals("newTitle", topicRepository.findById(topic.getId()).get().getTitle());
    }

    @Test
    public void deleteTopic(){
        User user = initNewUser();
        userRepository.save(user);
        TopicDto dto = new TopicDto();
        dto.setTitle("title");
        dto.setUsername(user.getUsername());
        TopicDto topic = topicService.createTopic(dto);
        topicService.deleteTopicById(topic.getId());
        Assertions.assertFalse(topicRepository.findById(topic.getId()).isPresent());
    }
}
