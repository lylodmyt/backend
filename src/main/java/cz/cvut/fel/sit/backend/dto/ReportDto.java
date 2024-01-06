package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.Report;

import java.util.Date;

public class ReportDto {

    private Long id;
    private String username;
    private String title;
    private String text;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static ReportDto convertToDto(Report report){
        if (report != null){
            ReportDto dto = new ReportDto();
            dto.setId(report.getId());
            dto.setTitle(report.getTitle());
            dto.setText(report.getDescription());
            dto.setUsername(report.getAuthor().getUsername());
            dto.setDate(report.getCreatedAt());
            return dto;
        } else {
            return null;
        }
    }
}
