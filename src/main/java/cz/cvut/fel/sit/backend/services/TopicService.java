package cz.cvut.fel.sit.backend.services;

import cz.cvut.fel.sit.backend.dto.TopicDto;
import cz.cvut.fel.sit.backend.entities.Topic;
import cz.cvut.fel.sit.backend.entities.User;
import cz.cvut.fel.sit.backend.repository.TopicRepository;
import cz.cvut.fel.sit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    private Topic createNewTopic(TopicDto topicDto, User user) {
        Topic topic = new Topic();
        topic.setTitle(topicDto.getTitle());
        topic.setUser(user);
        return topicRepository.save(topic);
    }

    public TopicDto createTopic(TopicDto topicDto){
        Optional<User> user = userRepository.findByUsername(topicDto.getUsername());
        if (user.isPresent()){
            if (topicDto.getParentId() != null){
                return createSubtopic(topicDto);
            }
            Topic topic = createNewTopic(topicDto, user.get());
            return TopicDto.convertToDto(topic);
        } else {
            throw new EntityNotFoundException("User with username: " + topicDto.getUsername() + " not found");
        }
    }

    private TopicDto createSubtopic(TopicDto topicDto){
        Optional<Topic> parent = topicRepository.findById(topicDto.getParentId());
        if (parent.isPresent()){
            Topic subTopic = createNewTopic(topicDto, parent.get().getUser());
            subTopic.setParentTopic(parent.get());
            parent.get().getSubTopics().add(subTopic);
            Topic send = topicRepository.saveAndFlush(subTopic);
            return TopicDto.convertToDto(send);
        } else {
            throw new EntityNotFoundException("Topic with id: " + topicDto.getId() + " not found");
        }
    }

    @Transactional
    public void deleteTopicById(Long id){
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()){
            topicRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Topic with id: " + id + " not found");
        }
    }

    public TopicDto renameTopic(TopicDto topicDto){
        Optional<Topic> topic = topicRepository.findById(topicDto.getId());
        if (topic.isPresent()){
            topic.get().setTitle(topicDto.getTitle());
            topicRepository.saveAndFlush(topic.get());
            return TopicDto.convertToDto(topic.get());
        } else {
            throw new EntityNotFoundException("Topic with id: " + topicDto.getId() + " not found");
        }
    }

    public List<TopicDto> getParentTopicsByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()){
            List<Topic> topics = topicRepository.findAllByUser(user.get());
            return topics.stream().filter(top -> top.getParentTopic() == null).map(TopicDto::convertToDto).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }
    }

    public List<TopicDto> getSubtopicsByTopicId(Long id){
        Optional<Topic> topic = topicRepository.findById(id);
        if (topic.isPresent()){
            List<Topic> subs = topic.get().getSubTopics();
            return subs.stream().map(TopicDto::convertToDto).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Topic with id: " + id + " not found");
        }
    }
}
