package cz.cvut.fel.sit.backend.controller;

import cz.cvut.fel.sit.backend.dto.UserDto;
import cz.cvut.fel.sit.backend.security.responses.MessageResponse;
import cz.cvut.fel.sit.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    UserService service;

    @PutMapping("/changepass")
    public ResponseEntity<?> updatePassword(@RequestBody UserDto userDto){
        UserDto dto = service.updatePassword(userDto);
        return ResponseEntity.ok(new MessageResponse("User with username: " + dto.getUsername() + " update password"));
    }
}

