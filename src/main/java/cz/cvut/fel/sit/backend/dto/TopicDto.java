package cz.cvut.fel.sit.backend.dto;

import cz.cvut.fel.sit.backend.entities.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicDto {

    private Long id;
    private String title;
    private List<TopicDto> subTopics;
    private Long parentId;
    private String username;

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

    public List<TopicDto> getSubTopics() {
        return subTopics;
    }

    public void setSubTopics(List<TopicDto> subTopics) {
        this.subTopics = subTopics;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static TopicDto convertToDto(Topic topic){
        if (topic == null){
            return null;
        }
        TopicDto dto = new TopicDto();
        dto.setId(topic.getId());
        dto.setUsername(topic.getUser().getUsername());
        dto.setTitle(topic.getTitle());
        dto.setParentId(topic.getParentTopic() != null ? topic.getParentTopic().getId() : null);
        List<TopicDto> subTopics = new ArrayList<>();
        if (topic.getSubTopics() != null){
            for (Topic sub : topic.getSubTopics()){
                subTopics.add(TopicDto.convertToDto(sub));
            }
            dto.setSubTopics(subTopics);
        } else {
            dto.setSubTopics(subTopics);
        }
        return dto;
    }
}
