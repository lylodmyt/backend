package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.TestDto;
import cz.cvut.fel.sit.backend.entities.Test;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.TestRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired TestQuestionService testQuestionService;

    public TestDto createTest(TestDto testDto){
        Optional<User> user = userRepository.findByUsername(testDto.getUsername());
        if (user.isPresent()){
            Test test = new Test();
            test.setTitle(testDto.getTitle());
            test.setUser(user.get());
            test.setPublish(false);
            Test save = repository.save(test);
            return TestDto.convertToDto(save);
        }else {
            throw new EntityNotFoundException("User with username: " + testDto.getUsername() + " not found");
        }
    }


    public TestDto updateTest(TestDto testDto){
        Optional<Test> test = repository.findById(testDto.getId());
        if (test.isPresent()){
            test.get().setTitle(testDto.getTitle());
            test.get().setPublish(testDto.isPublished());
            repository.saveAndFlush(test.get());
            return TestDto.convertToDto(test.get());
        } else {
            throw new EntityNotFoundException("Test with id: " + testDto.getId() + " not found");
        }
    }

    @Transactional
    public void deleteTestById(Long id){
        Optional<Test> test = repository.findById(id);
        if (test.isPresent()){
            testQuestionService.deleteAllByTestId(id);
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Test with id: " + id + " not found");
        }
    }


    @Transactional
    public List<TestDto> getTestsByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            List<Test> tests = repository.findAllByUser(user.get());
            return tests.stream().map(TestDto::convertToDto).collect(Collectors.toList());
        }else {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }
    }

    @Transactional
    public List<TestDto> getPublishedTest(){
        List<Test> tests = repository.findAllByPublish(true);
        return tests.stream().map(TestDto::convertToDto).collect(Collectors.toList());
    }


}
