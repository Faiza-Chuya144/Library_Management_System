package seu.edu.bd.librarymanagementsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow_record")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email_address", nullable = false)
    private String userEmail;  // Storing email directly instead of user_id

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private LocalDate borrowDate;

    @Column
    private LocalDate dueDate;

    @Column
    private Double lateFine;

    @Column(nullable = false)
    private Boolean returned ;

    // Constructors
    public BorrowRecord() {}

    public BorrowRecord(String userEmail, Book book, LocalDate borrowDate) {
        this.userEmail = userEmail;
        this.book = book;
        this.borrowDate = borrowDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate returnDate) {
        this.dueDate = returnDate;
    }

    public Double getLateFine() {
        return lateFine;
    }

    public void setLateFine(Double lateFine) {
        this.lateFine = lateFine;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }


}