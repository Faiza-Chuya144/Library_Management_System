package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.service.UserService;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        if (userService.countAllUsers() == 0) {
            createDefaultAdmin();
        }
        return "login";
    }

    private void createDefaultAdmin() {
        User admin = new User();
        admin.setEmailAddress("Chuya@gmail.com");
        admin.setUserName("Chuya");
        admin.setPassword("123");
        admin.setRole("ADMIN");
        userService.saveUser(admin);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {

        User user = userService.findByUserName(username);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials!");
            model.addAttribute("user", new User());
            return "login";
        }

        // Pass user details (name and role) to the model
        model.addAttribute("username", user.getUserName());
        model.addAttribute("role", user.getRole());
        model.addAttribute("welcomeMessage", "Welcome to the Book Dashboard!");

        // Redirect based on the user's role
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/dashboard"; // Admin dashboard
        } else if ("MEMBER".equals(user.getRole())) {
            return "member_dashboard"; // Member dashboard
        }

        return "login"; // Default return if no role matches
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        boolean success = userService.registerUser(user);
        if (!success) {
            model.addAttribute("error", "Passwords do not match!");
        } else {
            model.addAttribute("success", "Registration successful! You can now log in.");
        }
        return "login";
    }
}
