package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import seu.edu.bd.librarymanagementsystem.model.Book;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.service.BookService;
import seu.edu.bd.librarymanagementsystem.service.BorrowRecordService;
import seu.edu.bd.librarymanagementsystem.service.UserService;

@Controller
public class DashboardController {

    private final BookService bookService;
    private final UserService userService;
    private final BorrowRecordService borrowRecordService;

    public DashboardController(BookService bookService,
                               UserService userService,
                               BorrowRecordService borrowRecordService) {
        this.bookService = bookService;
        this.userService = userService;
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        // Fetching data for the dashboard
        long totalBooks = bookService.countAllBooks();
        long totalMembers = userService.countAllUsers();
        long borrowedToday = borrowRecordService.countBorrowedToday();
        long overdueReturns = borrowRecordService.countOverdueReturns();

        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("borrowedToday", borrowedToday);
        model.addAttribute("overdueReturns", overdueReturns);
        model.addAttribute("welcomeMessage", "Welcome to Library Dashboard");

        return "dashboard";
    }

    @GetMapping("/add-book")
    public String showAddBookPage(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getAllBooks());
        return "add_book";
    }

    @GetMapping("/add-members")
    public String showAddMemberPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("members", userService.findAllMembers());
        return "add_member";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
