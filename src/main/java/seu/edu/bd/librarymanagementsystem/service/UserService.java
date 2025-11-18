/*package seu.edu.bd.librarymanagementsystem.service;
import org.springframework.stereotype.Service;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long countAllMembers() {
        return userRepository.countByRoleIgnoreCase("MEMBER");
    }

    public boolean registerUser(User user) {
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return false;
        }
        // If new user
        if (!userRepository.existsById(user.getEmailAddress())) {
            user.setRole("MEMBER"); // set default role
        }
        userRepository.save(user); // save (new or update)
        return true;
    }

    public User findByEmailAddress(String emailAddress) {
        return userRepository.findById(emailAddress).orElse(null);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

   public List<User> getAllUsers() {
        return userRepository.findAll();
    }

   public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public List<User> findAllMembers() {
        return userRepository.findByRoleIgnoreCase("MEMBER");
    }

    public void deleteByEmail(String email) {
        userRepository.deleteById(email);
    }
}*/


package seu.edu.bd.librarymanagementsystem.service;

import org.springframework.stereotype.Service;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long countAllMembers() {
        return userRepository.countByRoleIgnoreCase("MEMBER");
    }

    public boolean registerUser(User user) {
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            return false;
        }
        if (!userRepository.existsById(user.getEmailAddress())) {
            user.setRole("MEMBER");
        }
        userRepository.save(user);
        return true;
    }

    public User findByEmailAddress(String emailAddress) {
        return userRepository.findById(emailAddress).orElse(null);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findAllMembers() {
        return userRepository.findByRoleIgnoreCase("MEMBER");
    }

    public void deleteByEmail(String email) {
        userRepository.deleteById(email);
    }

    public long countAllUsers() {
        return userRepository.count();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
