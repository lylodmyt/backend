package cz.cvut.fel.sit.backend;


import cz.cvut.fel.sit.backend.dto.ReportDto;
import cz.cvut.fel.sit.backend.entities.Report;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.ReportRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.services.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {"spring.liquibase.enabled=false"})
public class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    private static final int identification = 1;
    public static User initNewUser() {
        User user = new User();
        user.setUsername("admin" + identification);
        user.setPassword("test");
        user.setRole(Role.USER);
        user.setEmail("test@user.com");
        return user;
    }

    @Test
    public void createReportTest() {
        User user = userRepository.save(initNewUser());
        ReportDto reportDto = new ReportDto();
        reportDto.setDate(new Date());
        reportDto.setText("REPORT");
        reportDto.setTitle("TITLE");
        reportDto.setUsername(user.getUsername());

        reportService.createReport(reportDto);
        Assertions.assertEquals(1, reportRepository.findAll().size());
    }

    @Test
    public void deleteReportTest() {
        User user = userRepository.save(initNewUser());
        Report report = new Report();
        report.setId(1L);
        report.setAuthor(user);
        report.setCreatedAt(new Date());
        report.setDescription("REPORT");
        report.setTitle("TITLE");
        reportRepository.save(report);
        Assertions.assertEquals(1, reportRepository.findAll().size());
        reportService.deleteById(1L);
        Assertions.assertEquals(0, reportRepository.findAll().size());
    }


}
