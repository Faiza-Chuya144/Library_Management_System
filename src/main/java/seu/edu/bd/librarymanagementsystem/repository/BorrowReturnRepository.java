package seu.edu.bd.librarymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seu.edu.bd.librarymanagementsystem.model.BorrowRecord;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowReturnRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByReturnedFalse();
    List<BorrowRecord> findByBorrowDate(LocalDate date);
    List<BorrowRecord> findByDueDateBeforeAndReturnedFalse(LocalDate date);

    // New count methods
    long countByBorrowDate(LocalDate date);
    long countByDueDateBeforeAndReturnedFalse(LocalDate date);

    List<BorrowRecord> findByUserEmail(String email);
}