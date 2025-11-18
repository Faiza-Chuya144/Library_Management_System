/*package seu.edu.bd.librarymanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import seu.edu.bd.librarymanagementsystem.model.Book;
import seu.edu.bd.librarymanagementsystem.service.BookService;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void findAllBooks(){


        List<Book> foundBooks = bookService.getAllBooks();
        assertNotNull(foundBooks );

    }

}*/
