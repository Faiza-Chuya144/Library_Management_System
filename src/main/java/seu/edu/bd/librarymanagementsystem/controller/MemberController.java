/*package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.service.UserService;

import java.util.List;

@Controller
public class MemberController {

    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/add")
    public String showAddMemberPage(Model model) {
        model.addAttribute("user", new User());
        List<User> members = userService.findAllMembers();
        model.addAttribute("members", members);
        return "add_member";
    }


    @PostMapping("/add")
    public String saveMember(@ModelAttribute("user") User user, Model model) {
        boolean success = userService.registerUser(user);
        if (success) {
            model.addAttribute("success", "Member saved successfully.");
        } else {
            model.addAttribute("error", "Error: password mismatch or email already exists.");
        }
        model.addAttribute("user", new User());
        model.addAttribute("members", userService.findAllMembers());
        return "add_member";
    }


    @GetMapping("/edit")
    public String editMember(@RequestParam("email") String emailAddress, Model model) {
        User existing = userService.findByEmailAddress(emailAddress);
        if (existing == null) {
            model.addAttribute("error", "Member not found.");
            model.addAttribute("user", new User());
        } else {
            existing.setRepeatPassword(existing.getPassword());
            model.addAttribute("user", existing);
        }
        model.addAttribute("members", userService.findAllMembers());
        return "add_member";
    }


    @GetMapping("/delete")
    public String deleteMember(@RequestParam("email") String emailAddress, Model model) {
        userService.deleteByEmail(emailAddress);
        model.addAttribute("success", "Member deleted.");
        model.addAttribute("user", new User());
        model.addAttribute("members", userService.findAllMembers());
        return "add_member";
    }
}*/


package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }

    // Show Add/Edit Member Page
    @GetMapping("/add")
    public String showAddMemberPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        List<User> members = userService.findAllMembers();
        model.addAttribute("members", members);
        return "add_member";
    }

    // Save Member
    @PostMapping("/add")
    public String saveMember(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        boolean success = userService.registerUser(user);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Member saved successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error: password mismatch or email already exists.");
            redirectAttributes.addFlashAttribute("user", user); // preserve input if error
        }
        return "redirect:/members/add";
    }

    // Edit Member
    @GetMapping("/edit")
    public String editMember(@RequestParam("email") String emailAddress, RedirectAttributes redirectAttributes) {
        User existing = userService.findByEmailAddress(emailAddress);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
        } else {
            existing.setRepeatPassword(existing.getPassword());
            redirectAttributes.addFlashAttribute("user", existing);
        }
        return "redirect:/members/add";
    }

    // Delete Member
    @GetMapping("/delete")
    public String deleteMember(@RequestParam("email") String emailAddress, RedirectAttributes redirectAttributes) {
        userService.deleteByEmail(emailAddress);
        redirectAttributes.addFlashAttribute("success", "Member deleted successfully.");
        return "redirect:/members/add";
    }
}
