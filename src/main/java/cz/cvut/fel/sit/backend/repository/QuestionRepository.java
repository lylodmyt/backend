package cz.cvut.fel.sit.backend.repository;

import cz.cvut.fel.sit.backend.entities.Question;
import cz.cvut.fel.sit.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByUser(User user);
}
