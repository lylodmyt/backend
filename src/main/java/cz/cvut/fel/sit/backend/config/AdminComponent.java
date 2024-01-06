package cz.cvut.fel.sit.backend.config;

import cz.cvut.fel.sit.backend.security.requests.RegistrationRequest;
import cz.cvut.fel.sit.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminComponent implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (!userService.userExistByUsername("admin")) {
            RegistrationRequest request = new RegistrationRequest();
            request.setUsername("admin");
            request.setPassword("admin123");
            request.setEmail("admin@example.com");
            userService.createAdmin(request);
            System.out.println("Admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
