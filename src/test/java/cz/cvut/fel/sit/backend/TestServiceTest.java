package cz.cvut.fel.sit.backend;


import cz.cvut.fel.sit.backend.dto.TestDto;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.Test;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.TestRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.services.TestService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.liquibase.enabled=false"})
public class TestServiceTest {

    @Autowired
    TestRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestService service;
    private void init() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(Role.USER);
        userRepository.save(user);

        Test test = new Test();
        test.setTitle("title");
        test.setUser(user);
        test.setPublish(false);
        repository.save(test);

        Test test1 = new Test();
        test1.setTitle("title");
        test1.setUser(user);
        test1.setPublish(true);
        repository.save(test1);

        Test test2 = new Test();
        test2.setTitle("title");
        test2.setUser(user);
        test2.setPublish(true);
        repository.save(test2);
    }

    @org.junit.jupiter.api.Test
    public void createNewTest() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        user.setRole(Role.USER);
        userRepository.save(user);

        TestDto test = new TestDto();
        test.setTitle("title");
        test.setPublished(false);
        test.setUsername(user.getUsername());
        TestDto save = service.createTest(test);
        Assertions.assertTrue(repository.findById(save.getId()).isPresent());
    }

    @org.junit.jupiter.api.Test
    public void getPublishedTests() {
        init();
        List<TestDto> list = service.getPublishedTest();
        Assertions.assertEquals(2, list.size());
    }

    @org.junit.jupiter.api.Test
    public void deleteTestById() {
        init();
        service.deleteTestById(1L);
        Assertions.assertFalse(repository.findById(1L).isPresent());
        service.deleteTestById(2L);
        Assertions.assertFalse(repository.findById(2L).isPresent());
        service.deleteTestById(3L);
        Assertions.assertFalse(repository.findById(3L).isPresent());
    }


}
