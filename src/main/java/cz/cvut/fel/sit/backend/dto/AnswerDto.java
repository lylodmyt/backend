package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.Answer;

public class AnswerDto {

    private Long id;
    private String text;
    private boolean correct;
    private Long questionId;

    private ImageDto imageDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public ImageDto getImageDto() {
        return imageDto;
    }

    public void setImageDto(ImageDto imageDto) {
        this.imageDto = imageDto;
    }

    public static AnswerDto convertToDto(Answer answer){
        if (answer != null){
            AnswerDto dto = new AnswerDto();
            dto.setText(answer.getText());
            dto.setQuestionId(answer.getQuestion().getId());
            dto.setId(answer.getId());
            dto.setCorrect(answer.isTrue());
            dto.setImageDto(ImageDto.convertToDto(answer.getImage()));
            return dto;
        }
        return null;
    }
}
