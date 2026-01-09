package app;

import dao.BookDao;
import dao.StudentDao;
import dao.LoanDao;
import entity.Book;
import entity.BookStatus;
import entity.Student;
import entity.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static BookDao bookDao = new BookDao();
    private static StudentDao studentDao = new StudentDao();
    private static LoanDao loanDao = new LoanDao();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== SMART LIBRARY PLUS ===");
            System.out.println("1 - Kitap Ekle");
            System.out.println("2 - Kitapları Listele");
            System.out.println("3 - Öğrenci Ekle");
            System.out.println("4 - Öğrencileri Listele");
            System.out.println("5 - Kitap Ödünç Ver");
            System.out.println("6 - Ödünç Listesini Görüntüle");
            System.out.println("7 - Kitap Geri Teslim Al");
            System.out.println("0 - Çıkış");
            System.out.print("Seçim: ");

            choice = sc.nextInt();
            sc.nextLine(); // buffer temizle

            switch (choice) {
                case 1:
                    kitapEkle(sc);
                    break;
                case 2:
                    kitaplariListele();
                    break;
                case 3:
                    ogrenciEkle(sc);
                    break;
                case 4:
                    ogrencileriListele();
                    break;
                case 5:
                    kitapOduncVer(sc);
                    break;
                case 6:
                    oduncListele();
                    break;
                case 7:
                    kitapIadeAl(sc);
                    break;
                case 0:
                    System.out.println("Çıkılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }

        } while (choice != 0);

        sc.close();
    }

    // ================= METOTLAR =================

    private static void kitapEkle(Scanner sc) {
        System.out.print("Kitap Adı: ");
        String title = sc.nextLine();

        System.out.print("Yazar: ");
        String author = sc.nextLine();

        System.out.print("Yıl: ");
        int year = sc.nextInt();
        sc.nextLine();

        Book book = new Book(title, author, year, BookStatus.AVAILABLE);
        bookDao.save(book);

        System.out.println("✔ Kitap eklendi.");
    }

    private static void kitaplariListele() {
        List<Book> books = bookDao.getAll();
        System.out.println("\n--- Kitaplar ---");

        for (Book b : books) {
            System.out.println(
                    b.getId() + " | " +
                            b.getTitle() + " | " +
                            b.getAuthor() + " | " +
                            b.getYear() + " | " +
                            b.getStatus()
            );
        }
    }

    private static void ogrenciEkle(Scanner sc) {
        System.out.print("Öğrenci Adı: ");
        String name = sc.nextLine();

        System.out.print("Bölüm: ");
        String department = sc.nextLine();

        Student student = new Student(name, department);
        studentDao.save(student);

        System.out.println("✔ Öğrenci eklendi.");
    }

    private static void ogrencileriListele() {
        List<Student> students = studentDao.getAll();
        System.out.println("\n--- Öğrenciler ---");

        for (Student s : students) {
            System.out.println(
                    s.getId() + " | " +
                            s.getName() + " | " +
                            s.getDepartment()
            );
        }
    }

    private static void kitapOduncVer(Scanner sc) {
        System.out.print("Öğrenci ID: ");
        int studentId = sc.nextInt();

        System.out.print("Kitap ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();

        Student student = studentDao.getById(studentId);
        Book book = bookDao.getById(bookId);

        if (student == null || book == null) {
            System.out.println("❌ Öğrenci veya kitap bulunamadı.");
            return;
        }

        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("❌ Kitap zaten ödünçte.");
            return;
        }

        Loan loan = new Loan(LocalDate.now(), student, book);
        loanDao.save(loan);

        book.setStatus(BookStatus.BORROWED);
        bookDao.update(book);

        System.out.println("✔ Kitap ödünç verildi.");
    }

    private static void oduncListele() {
        List<Loan> loans = loanDao.getAll();
        System.out.println("\n--- Ödünç Listesi ---");

        for (Loan l : loans) {
            System.out.println(
                    l.getId() + " | " +
                            l.getStudent().getName() + " | " +
                            l.getBook().getTitle() + " | " +
                            l.getBorrowDate() + " | " +
                            l.getReturnDate()
            );
        }
    }

    private static void kitapIadeAl(Scanner sc) {
        System.out.print("Loan ID: ");
        int loanId = sc.nextInt();
        sc.nextLine();

        Loan loan = loanDao.getById(loanId);

        if (loan == null) {
            System.out.println("❌ Loan bulunamadı.");
            return;
        }

        loan.setReturnDate(LocalDate.now());
        loanDao.update(loan);

        Book book = loan.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookDao.update(book);

        System.out.println("✔ Kitap iade alındı.");
    }
}
