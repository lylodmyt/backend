package cz.cvut.fel.sit.backend.controller;


import cz.cvut.fel.sit.backend.dto.UserDto;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.security.requests.LoginRequest;
import cz.cvut.fel.sit.backend.security.requests.RegistrationRequest;
import cz.cvut.fel.sit.backend.security.responses.JwtResponse;
import cz.cvut.fel.sit.backend.security.responses.MessageResponse;
import cz.cvut.fel.sit.backend.security.detailsImpl.UserDetailsImpl;
import cz.cvut.fel.sit.backend.services.UserService;
import cz.cvut.fel.sit.backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(userDetails.getId(), jwt, userDetails.getUsername(), userDetails.getEmail(), roles));
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegistrationRequest registrationRequest){
        if (userService.userExistByUsername(registrationRequest.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("User with username: " + registrationRequest.getUsername() + " exist"));
        }
        if (userService.userExistByEmail(registrationRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("User with email: " + registrationRequest.getEmail() + " exist"));
        }
        UserDto dto = userService.createUser(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("User with username: " + dto.getUsername() + " was created"));
    }
}
