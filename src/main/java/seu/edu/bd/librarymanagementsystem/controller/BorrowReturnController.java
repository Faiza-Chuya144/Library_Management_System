package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seu.edu.bd.librarymanagementsystem.model.Book;
import seu.edu.bd.librarymanagementsystem.model.BorrowRecord;
import seu.edu.bd.librarymanagementsystem.model.User;
import seu.edu.bd.librarymanagementsystem.service.BookService;
import seu.edu.bd.librarymanagementsystem.service.BorrowRecordService;
import seu.edu.bd.librarymanagementsystem.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BorrowReturnController {

    private final UserService userService;
    private final BookService bookService;
    private final BorrowRecordService borrowRecordService;

    public BorrowReturnController(UserService userService, BookService bookService, BorrowRecordService borrowRecordService) {
        this.userService = userService;
        this.bookService = bookService;
        this.borrowRecordService = borrowRecordService;
    }

    // Populating the model with necessary data
    private void populateModel(Model model) {
        List<BorrowRecord> nonReturnedRecords = borrowRecordService.getAllNonReturnedRecords();
        model.addAttribute("users", userService.findAllMembers());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("borrowRecords", nonReturnedRecords);
    }

    // GET handler for displaying borrow and return form
    @GetMapping("/borrow-return")
    public String showBorrowAndReturnForm(@RequestParam(value = "recordId", required = false) Long recordId, Model model) {
        populateModel(model);

        if (recordId != null) {
            BorrowRecord selectedRecord = borrowRecordService.getBorrowRecordById(recordId);
            if (selectedRecord != null) {
                double fine = borrowRecordService.calculateLateFine(selectedRecord);
                model.addAttribute("selectedRecord", selectedRecord);
                model.addAttribute("calculatedFine", fine);
                model.addAttribute("today", LocalDate.now());
            }
        }

        return "borrow_return"; // Thymeleaf page for borrow-return
    }

    // POST handler for processing form submissions
    @PostMapping("/borrow-return")
    public String handleFormSubmission(@RequestParam("actionType") String actionType,
                                       @RequestParam(required = false) String userEmail,
                                       @RequestParam(required = false) Long bookId,
                                       @RequestParam(required = false) Long recordId,
                                       Model model) {

        if ("borrow".equals(actionType)) {
            if (userEmail != null && bookId != null) {
                User user = userService.findByEmailAddress(userEmail);
                Book book = bookService.getBookById(bookId);

                // Check if the user exists and is not an admin, and if the book is available
                if (user != null && book != null && book.getCopies() > 0) {
                    // Prevent admin from borrowing books
                    if ("ADMIN".equals(user.getRole())) {
                        model.addAttribute("errorMessage", "Admin users cannot borrow books.");
                    } else {
                        borrowRecordService.borrowBook(userEmail, book);
                        model.addAttribute("successMessage", "Book borrowed successfully!");
                        return "redirect:/borrow-return";
                    }
                } else {
                    model.addAttribute("errorMessage", "Book is out of stock or user not found.");
                }
            } else {
                model.addAttribute("errorMessage", "Please select both member and book.");
            }
        } else if ("return".equals(actionType)) {
            if (recordId != null) {
                BorrowRecord borrowRecord = borrowRecordService.getBorrowRecordById(recordId);

                if (borrowRecord != null) {
                    borrowRecordService.returnBook(borrowRecord);
                    model.addAttribute("successMessage", "Book returned successfully!");
                    return "redirect:/borrow-return";
                } else {
                    model.addAttribute("errorMessage", "Borrow record not found.");
                }
            } else {
                model.addAttribute("errorMessage", "Please select a borrow record to return.");
            }
        }

        populateModel(model);
        return "borrow_return";
    }
}
