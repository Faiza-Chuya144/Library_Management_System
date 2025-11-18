package seu.edu.bd.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seu.edu.bd.librarymanagementsystem.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String username);
    List<User> findByRoleIgnoreCase(String role);
    boolean existsById(String emailAddress);
    void deleteById(String emailAddress);

    // New count method
    long countByRoleIgnoreCase(String role);
}
