package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.ReportDto;
import cz.cvut.fel.sit.backend.entities.Report;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserService userService;

    public void createReport(ReportDto reportDto){
        User user = userService.getUserByUsername(reportDto.getUsername());
        if (user != null){
            Report report = new Report();
            report.setTitle(reportDto.getTitle());
            report.setDescription(reportDto.getText());
            report.setCreatedAt(new Date());
            report.setAuthor(user);

            reportRepository.save(report);
        } else {
            throw new EntityNotFoundException("User with username: " + reportDto.getUsername() + " not found");
        }
    }

    public List<ReportDto> getReports(){
       return reportRepository.findAll().stream().map(ReportDto::convertToDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            reportRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Report with id: " + id + " not found");
        }
    }
}
