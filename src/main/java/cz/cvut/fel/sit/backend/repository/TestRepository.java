package cz.cvut.fel.sit.backend.repository;

import cz.cvut.fel.sit.backend.entities.Test;
import cz.cvut.fel.sit.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByUser(User user);
    List<Test> findAllByPublish(boolean published);
}
