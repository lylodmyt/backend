package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDto {

    private Long id;
    private String title;
    private Long topicId;
    private String username;
    private String topic;
    private List<AnswerDto> answers = new ArrayList<>();
    private ImageDto imageDto;

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

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }

    public void setImageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static QuestionDto covertToDto(Question question){
        if (question != null){
            QuestionDto dto = new QuestionDto();
            dto.setId(question.getId());
            dto.setTitle(question.getText());
            dto.setTopicId(question.getTopic().getId());
            dto.setTopic(question.getTopic().getTitle());
            if (!question.getAnswers().isEmpty()){
                dto.setAnswers(question.getAnswers().stream().map(AnswerDto::convertToDto).collect(Collectors.toList()));
            }
            dto.setImageDto(ImageDto.convertToDto(question.getImage()));
            return dto;
        }
        return null;
    }
}
