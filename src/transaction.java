import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class transaction {

    private String transactionId;
    private member member;
    private book book;

    private String borrowDate;
    private String dueDate;
    private String returnDate;

    private int daysLate;
    private double lateFee;

    private static int totalTransactions = 0;
    private static int transactionCounter = 0;

    public static final double LATE_FEE_PER_DAY = 2000.0;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public transaction(member member, book book, String borrowDate, int borrowDurationDays) {
        if (member == null) throw new IllegalArgumentException("member tidak boleh null");
        if (book == null) throw new IllegalArgumentException("book tidak boleh null");
        if (!isValidDate(borrowDate)) throw new IllegalArgumentException("borrowDate tidak valid (DD-MM-YYYY)");
        if (borrowDurationDays <= 0) throw new IllegalArgumentException("borrowDurationDays harus > 0");

        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;

        LocalDate bDate = LocalDate.parse(borrowDate, FORMAT);
        LocalDate dDate = bDate.plusDays(borrowDurationDays);

        this.dueDate = dDate.format(FORMAT);
        this.returnDate = null;
        this.daysLate = 0;
        this.lateFee = 0;

        this.transactionId = String.format("TRX%03d", ++transactionCounter);
        totalTransactions++;

        book.borrowBook();
    }

    public void processReturn(String returnDate) {
        if (!isValidDate(returnDate)) throw new IllegalArgumentException("returnDate tidak valid (DD-MM-YYYY)");

        LocalDate rDate = LocalDate.parse(returnDate, FORMAT);
        LocalDate bDate = LocalDate.parse(borrowDate, FORMAT);

        if (rDate.isBefore(bDate)) {
            throw new IllegalArgumentException("returnDate tidak boleh sebelum borrowDate");
        }

        this.returnDate = returnDate;

        calculateLateFee();

        book.returnBook();
    }

    public void calculateLateFee() {
        LocalDate rDate = LocalDate.parse(returnDate, FORMAT);
        LocalDate dDate = LocalDate.parse(dueDate, FORMAT);

        if (rDate.isAfter(dDate)) {
            daysLate = (int) ChronoUnit.DAYS.between(dDate, rDate);
        } else {
            daysLate = 0;
            lateFee = 0;
        }
    }

    public boolean isOverdue(String currentDate) {
        if (!isValidDate(currentDate)) throw new IllegalArgumentException("currentDate tidak valid");
        if (returnDate != null) return false;

        LocalDate now = LocalDate.parse(currentDate, FORMAT);
        LocalDate dDate = LocalDate.parse(dueDate, FORMAT);

        return now.isAfter(dDate);
    }

    public String getTransactionStatus(String currentDate) {
        if (returnDate != null) return "Selesai";

        if (isOverdue(currentDate)) return "Terlambat";

        return "Aktif";
    }

    public void displayTransaction(String currentDate) {
        System.out.println("=== TRANSACTION INFO ===");
        System.out.println("ID Transaksi : " + transactionId);
        System.out.println("Member       : " + member.getName());
        System.out.println("Buku         : " + book.getTitle());
        System.out.println("Borrow Date  : " + borrowDate);
        System.out.println("Due Date     : " + dueDate);
        System.out.println("Return Date  : " + (returnDate == null ? "-" : returnDate));
        System.out.println("Status       : " + getTransactionStatus(currentDate));
        System.out.println("Days Late    : " + daysLate);
        System.out.println("Late Fee     : Rp " + lateFee);
        System.out.println("-----------------------------");
    }

    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, FORMAT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTransactionId() { return transactionId; }
    public member getMember() { return member; }
    public book getBook() { return book; }
    public String getBorrowDate() { return borrowDate; }
    public String getDueDate() { return dueDate; }
    public String getReturnDate() { return returnDate; }
    public int getDaysLate() { return daysLate; }
    public double getLateFee() { return lateFee; }

    public static int getTotalTransactions() { return totalTransactions; }
}
