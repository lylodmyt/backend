package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.TestQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestQuestionDto {

    private Long id;

    private Long testId;
    private Long questionId;
    private String question;
    private List<AnswerDto> answers = new ArrayList<>();
    private ImageDto imageDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public static TestQuestionDto convertToDto(TestQuestion testQuestion){
        TestQuestionDto dto = new TestQuestionDto();
        dto.setId(testQuestion.getId());
        dto.setTestId(testQuestion.getTest().getId());
        dto.setQuestionId(testQuestion.getQuestion().getId());
        dto.setQuestion(testQuestion.getQuestion().getText());
        dto.setImageDto(ImageDto.convertToDto(testQuestion.getQuestion().getImage()));
        if (testQuestion.getQuestion().getAnswers() != null){
            List<AnswerDto> answerDtos = testQuestion.getQuestion().getAnswers().stream().map(AnswerDto::convertToDto).collect(Collectors.toList());
            dto.setAnswers(answerDtos);
        }
        return dto;
    }
}
