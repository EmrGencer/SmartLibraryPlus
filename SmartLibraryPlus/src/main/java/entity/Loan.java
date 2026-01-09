package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Loan() {}

    public Loan(LocalDate borrowDate, Student student, Book book) {
        this.borrowDate = borrowDate;
        this.student = student;
        this.book = book;
    }

    public int getId() { return id; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Student getStudent() { return student; }
    public Book getBook() { return book; }
}
