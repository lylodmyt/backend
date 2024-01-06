package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.ReportDto;
import cz.cvut.fel.sit.backend.security.responses.MessageResponse;
import cz.cvut.fel.sit.backend.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<?> createReport(@RequestBody ReportDto reportDto){
        reportService.createReport(reportDto);
        return ResponseEntity.ok(new MessageResponse("Report created successfully"));
    }
}
