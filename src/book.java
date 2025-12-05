import java.time.LocalDate;

public class book {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private int publicationYear;
    private boolean isAvailable;
    private int totalCopies;
    private int availableCopies;
    private int borrowCount = 0;

    private static int totalBooks = 0;
    private static int bookCounter = 0;


    public book() {
        bookId = String.format("BK%03d", ++bookCounter);
        title = "Untitled";
        author = "Unknown";
        category = "General";
        publicationYear = 1900;
        totalCopies = 1;
        availableCopies = 1;
        isAvailable = true;

        totalBooks++;
    }


    public book(String title, String author, String category,
                int publicationYear, int totalCopies) {

        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("title tidak boleh kosong");
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("author tidak boleh kosong");

        category = capitalize(category);
        if (!isValidCategory(category))
            throw new IllegalArgumentException(
                    "category harus salah satu dari: Fiction/Non-Fiction/Science/Technology/History"
            );

        if (publicationYear < 1900 || publicationYear > 2025)
            throw new IllegalArgumentException("publicationYear harus antara 1900 - 2025");

        if (totalCopies < 0)
            throw new IllegalArgumentException("totalCopies harus >= 0");

        this.title = title.trim();
        this.author = author.trim();
        this.category = category;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;

        this.bookId = String.format("BK%03d", ++bookCounter);
        this.isAvailable = availableCopies > 0;

        totalBooks++;
    }


    public void displayBookInfo() {
        System.out.println("=== Book Info ===");
        System.out.println("Book ID         : " + bookId);
        System.out.println("Title           : " + title);
        System.out.println("Author          : " + author);
        System.out.println("Category        : " + category);
        System.out.println("Publication Year: " + publicationYear);
        System.out.println("Book Age        : " + getBookAge() + " tahun");
        System.out.println("Total Copies    : " + totalCopies);
        System.out.println("Available       : " + availableCopies);
        System.out.println("Status          : " + getAvailabilityStatus());
        System.out.println("New Release     : " + (isNewRelease() ? "Ya" : "Tidak"));
        System.out.println("Borrowed Count  : " + borrowCount);
        System.out.println("--------------------------------------------");
    }

    public void displayBookInfoCompact() {
        System.out.println("[" + bookId + "] " + title);
        System.out.println("Penulis       : " + author);
        System.out.println("Kategori      : " + category);
        System.out.println("Tahun Terbit  : " + publicationYear);
        System.out.println("Umur Buku     : " + getBookAge() + " tahun");
        System.out.println("Total Copy    : " + totalCopies + " eksemplar");
        System.out.println("Tersedia      : " + availableCopies + " eksemplar | Status: "
                + getAvailabilityStatus() + (isNewRelease() ? " [NEW RELEASE]" : ""));
        System.out.println("--------------------------------------------");
    }


    public boolean borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
            borrowCount++;
            isAvailable = availableCopies > 0;
            return true;
        }
        return false;
    }

    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            isAvailable = true;
        }
    }



    public int getBookAge() {
        return LocalDate.now().getYear() - publicationYear;
    }

    public boolean isNewRelease() {
        return getBookAge() <= 2;
    }

    public String getAvailabilityStatus() {
        if (availableCopies == 0) return "Tidak Tersedia";
        if (availableCopies <= 5) return "Terbatas";
        return "Banyak Tersedia";
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getPublicationYear() { return publicationYear; }
    public int getAvailableCopies() { return availableCopies; }
    public int getTotalCopies() { return totalCopies; }
    public int getBorrowCount() { return borrowCount; }
    public boolean isAvailable() { return isAvailable; }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("title tidak boleh kosong");
        this.title = title.trim();
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("author tidak boleh kosong");
        this.author = author.trim();
    }

    public void setCategory(String category) {
        category = capitalize(category);
        if (!isValidCategory(category))
            throw new IllegalArgumentException("Kategori tidak valid");
        this.category = category;
    }

    public void setPublicationYear(int year) {
        if (year < 1900 || year > 2025)
            throw new IllegalArgumentException("Tahun terbit tidak valid");
        this.publicationYear = year;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies < 0)
            throw new IllegalArgumentException("Total copies tidak boleh < 0");
        this.totalCopies = totalCopies;

        if (availableCopies > totalCopies) {
            availableCopies = totalCopies;
        }
    }

    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0)
            throw new IllegalArgumentException("Available copies tidak boleh < 0");
        if (availableCopies > totalCopies)
            throw new IllegalArgumentException("Available copies tidak boleh > total copies");

        this.availableCopies = availableCopies;
        this.isAvailable = availableCopies > 0;
    }



    public static int getTotalBooks() { return totalBooks; }


    private static boolean isValidCategory(String c) {
        return "Fiction".equals(c) || "Non-Fiction".equals(c) ||
                "Science".equals(c) || "Technology".equals(c) ||
                "History".equals(c);
    }

    private static String capitalize(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
