package seu.edu.bd.librarymanagementsystem.service;

import org.springframework.stereotype.Service;
import seu.edu.bd.librarymanagementsystem.model.Book;
import seu.edu.bd.librarymanagementsystem.model.BorrowRecord;
import seu.edu.bd.librarymanagementsystem.repository.BorrowReturnRepository;
import seu.edu.bd.librarymanagementsystem.repository.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BorrowRecordService {
    private final BorrowReturnRepository borrowReturnRepository;
    private final BookRepository bookRepository;

    public BorrowRecordService(BorrowReturnRepository borrowReturnRepository,
                               BookRepository bookRepository) {
        this.borrowReturnRepository = borrowReturnRepository;
        this.bookRepository = bookRepository;
    }

    // For dashboard
    public long countBorrowedToday() {

        return borrowReturnRepository.countByBorrowDate(LocalDate.now());
    }

    public long countOverdueReturns() {
        return borrowReturnRepository.countByDueDateBeforeAndReturnedFalse(LocalDate.now());
    }

    // For borrowing functionality
    public BorrowRecord borrowBook(String userEmail, Book book) {
        if (userEmail == null || book == null || book.getCopies() <= 0) {
            return null;
        }

        book.setCopies(book.getCopies() - 1);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setUserEmail(userEmail);
        record.setBook(book);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(7));
        record.setLateFine(0.0);
        record.setReturned(false);

        return borrowReturnRepository.save(record);
    }

    public BorrowRecord returnBook(BorrowRecord record) {
        if (record == null) return null;

        record.setReturned(true);
        record.setDueDate(LocalDate.now());
        record.setLateFine(calculateLateFine(record));

        Book book = record.getBook();
        if (book != null) {
            book.setCopies(book.getCopies() + 1);
            bookRepository.save(book);
        }

        return borrowReturnRepository.save(record);
    }

    public double calculateLateFine(BorrowRecord record) {
        if (record == null || record.getDueDate() == null) {
            return 0;
        }

        long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), LocalDate.now());
        return daysOverdue > 0 ? daysOverdue * 5 : 0;
    }

    public List<BorrowRecord> getAllNonReturnedRecords() {
        return borrowReturnRepository.findByReturnedFalse();
    }

    public BorrowRecord getBorrowRecordById(Long id) {
        return borrowReturnRepository.findById(id).orElse(null);
    }

    public List<BorrowRecord> getBorrowRecordsByUserEmail(String email) {
        return borrowReturnRepository.findByUserEmail(email);
    }
}