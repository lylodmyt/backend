package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.UserDto;
import cz.cvut.fel.sit.backend.entities.Role;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import cz.cvut.fel.sit.backend.security.requests.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            return user.get();
        } else {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }
    }

    public UserDto createUser(RegistrationRequest registrationRequest){
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setRole(Role.USER);

        User send = userRepository.save(user);
        return UserDto.convertToDto(send);
    }

    public UserDto updatePassword(UserDto userDto){
        Optional<User> user = userRepository.findByUsername(userDto.getUsername());
        if (user.isEmpty()){
            throw new EntityNotFoundException("User with username: " + userDto.getUsername() + " not found");
        }

        user.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
        User send = userRepository.saveAndFlush(user.get());
        return UserDto.convertToDto(send);
    }


    public UserDto createAdmin(RegistrationRequest registrationRequest){
        if (userRepository.existsByUsername(registrationRequest.getUsername())){
            throw new EntityNotFoundException("User with username: " + registrationRequest.getUsername() + " exist");
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())){
            throw new EntityNotFoundException("User with email: " + registrationRequest.getEmail() + " exist");
        }

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setRole(Role.ADMIN);

        User send = userRepository.save(user);
        return UserDto.convertToDto(send);
    }

    public boolean userExistByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean userExistByEmail(String email){
        return userRepository.existsByEmail(email);
    }



}
