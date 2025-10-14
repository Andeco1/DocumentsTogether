package ru.together.documents.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.together.documents.entity.LibUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<LibUser, Long> {
    Optional<LibUser> findByUsername(String username);
    Optional<LibUser> findByEmail(String email);
}
