package seu.edu.bd.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seu.edu.bd.librarymanagementsystem.model.Book;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books by title
    List<Book> findBooksByTitleContainingIgnoreCase(String title);

    // Find books by authors (note that authors could be a list, so adjust as needed)
    List<Book> findBooksByAuthorsContainingIgnoreCase(String authors);

    // Find books by ISBN
    List<Book> findByIsbn(String isbn);

    // Find books with more than a certain number of copies
    List<Book> findBooksByCopiesGreaterThan(int copies);
}
