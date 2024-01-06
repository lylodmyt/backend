package cz.cvut.fel.sit.backend.controller;


import cz.cvut.fel.sit.backend.dto.ReportDto;
import cz.cvut.fel.sit.backend.dto.UserDto;
import cz.cvut.fel.sit.backend.security.requests.RegistrationRequest;
import cz.cvut.fel.sit.backend.security.responses.MessageResponse;
import cz.cvut.fel.sit.backend.services.ReportService;
import cz.cvut.fel.sit.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    ReportService reportService;


    @PostMapping("/register")
    public ResponseEntity<?> createAdmin(@RequestBody RegistrationRequest registrationRequest){
        UserDto dto = userService.createAdmin(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("User with username: " + dto.getUsername() + " was created"));
    }

    @GetMapping("/reports")
    public List<ReportDto> getAllReport(){
        return reportService.getReports();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReport(@PathVariable Long id){
        reportService.deleteById(id);
    }
}
