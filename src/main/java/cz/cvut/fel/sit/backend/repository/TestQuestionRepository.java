package cz.cvut.fel.sit.backend.repository;

import cz.cvut.fel.sit.backend.entities.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
}
