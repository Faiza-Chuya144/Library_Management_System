package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import seu.edu.bd.librarymanagementsystem.model.Book;
import seu.edu.bd.librarymanagementsystem.service.BookService;

import java.util.List;

@Controller
public class MemberDashboardController {

    private final BookService bookService;

    public MemberDashboardController(BookService bookService) {
        this.bookService = bookService;
    }



    @GetMapping("/member-dashboard")
    public String showMemberDashboard(Model model) {
        List<Book> books = bookService.getAllBooks(); // Fetch all books
        long bookCount = bookService.countAllBooks(); // Fetch total book count


        model.addAttribute("books", books);
        model.addAttribute("bookCount", bookCount);
        return "member_dashboard";
    }

}
