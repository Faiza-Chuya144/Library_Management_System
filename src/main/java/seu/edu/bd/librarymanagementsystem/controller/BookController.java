package seu.edu.bd.librarymanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seu.edu.bd.librarymanagementsystem.model.Book;
import seu.edu.bd.librarymanagementsystem.service.BookService;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Show all books
    @GetMapping("/books")
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();  // Fetch all books from the service
        model.addAttribute("books", books);  // Add the books list to the model
        model.addAttribute("book", new Book());  // Add an empty Book object to handle the form
        return "add_book";  // Return the add_book view
    }




    // Save new or update existing book
    @PostMapping("/books/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Edit book form: populate the book in the form
    @PostMapping("/books/edit")
    public String editBook(@RequestParam Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("books", bookService.getAllBooks());
        return "add_book";
    }

    // Delete a book
    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

}
