package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestDto {

    private Long id;
    private String title;
    private String username;

    private List<TestQuestionDto> testQuestionDtoList = new ArrayList<>();
    private boolean isPublished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public List<TestQuestionDto> getTestQuestionDtoList() {
        return testQuestionDtoList;
    }

    public void setTestQuestionDtoList(List<TestQuestionDto> testQuestionDtoList) {
        this.testQuestionDtoList = testQuestionDtoList;
    }

    public static TestDto convertToDto(Test test){
        if (test != null){
            TestDto dto = new TestDto();
            dto.setId(test.getId());
            dto.setUsername(test.getUser().getUsername());
            dto.setTitle(test.getTitle());
            dto.setPublished(test.isPublish());
            if (!test.getTestQuestions().isEmpty()){
                dto.setTestQuestionDtoList(test.getTestQuestions().stream().map(TestQuestionDto::convertToDto).collect(Collectors.toList()));
            }
            return dto;
        }
        return null;
    }
}
