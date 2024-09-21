package uz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPhone(String phone);
}
