package cz.cvut.fel.sit.backend;

import cz.cvut.fel.sit.backend.dto.UserDto;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.security.requests.RegistrationRequest;
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
public class UserServiceTest {


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
    public void createUserTest() {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("new");
        registrationRequest.setPassword("new");
        registrationRequest.setEmail("new@user.cz");
        UserDto res = userService.createUser(registrationRequest);

        Assertions.assertEquals("new", res.getUsername());
        Assertions.assertEquals("new@user.cz", res.getEmail());
        Assertions.assertTrue(userRepository.findByUsername("new").isPresent());
    }

    @Test
    public void getUserByUsernameTest() {
        User user = initNewUser();
        userRepository.save(user);
        User res = userService.getUserByUsername(user.getUsername());
        Assertions.assertEquals(res.getUsername(), user.getUsername());
    }

    @Test
    public void createAdminTest() {

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("newAdmin");
        registrationRequest.setPassword("new");
        registrationRequest.setEmail("new@user.cz");
        UserDto res = userService.createAdmin(registrationRequest);

        Assertions.assertEquals("newAdmin", res.getUsername());
        Assertions.assertEquals("new@user.cz", res.getEmail());
        Assertions.assertTrue(userRepository.findByUsername("newAdmin").isPresent());
        Assertions.assertEquals(userRepository.findByUsername("newAdmin").get().getRole(), Role.ADMIN);
    }
}
