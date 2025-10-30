import java.util.*;

class Book {
    int id;
    String name;
    String writer;
    boolean isAvailable = true;

    Book(int id, String name, String writer) {
        this.id = id;
        this.name = name;
        this.writer = writer;
    }

    public String toString() {
        String status = isAvailable ? "Available" : "Issued";
        return id + " - " + name + " by " + writer + " (" + status + ")";
    }
}

class Member {
    int id;
    String fullName;
    double fineAmount = 0.0;

    Member(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String toString() {
        return id + " - " + fullName + " | Fine: " + fineAmount;
    }
}

class Library {
    List<Book> bookList = new ArrayList<>();
    List<Member> memberList = new ArrayList<>();
    Map<Integer, Integer> issued = new HashMap<>();  // bookId -> memberId

    void addBook(String title, String author) {
        int id = bookList.size() + 1;
        Book b = new Book(id, title, author);
        bookList.add(b);
        System.out.println("Book added: " + title);
    }

    void addMember(String name) {
        int id = memberList.size() + 1;
        memberList.add(new Member(id, name));
        System.out.println("Member added: " + name);
    }

    void listBooks() {
        if (bookList.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        for (Book b : bookList) {
            System.out.println(b);
        }
    }

    void listMembers() {
        if (memberList.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        for (Member m : memberList) {
            System.out.println(m);
        }
    }

    void issueBook(int bookId, int memberId) {
        Book b = findBook(bookId);
        Member m = findMember(memberId);

        if (b == null || m == null) {
            System.out.println("Invalid Book or Member ID.");
            return;
        }
        if (!b.isAvailable) {
            System.out.println("Sorry, this book is already issued.");
            return;
        }

        b.isAvailable = false;
        issued.put(bookId, memberId);
        System.out.println("Book issued to " + m.fullName);
    }

    void returnBook(int bookId) {
        Book b = findBook(bookId);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        Integer memberId = issued.remove(bookId);
        if (memberId == null) {
            System.out.println("This book was not issued.");
            return;
        }

        b.isAvailable = true;
        Member m = findMember(memberId);

        // simple fine logic
        double fine = (int)(Math.random() * 25);
        m.fineAmount += fine;

        System.out.println("Book returned by " + m.fullName + ". Fine charged: " + fine);
    }

    void searchBook(String text) {
        boolean found = false;
        for (Book b : bookList) {
            if (b.name.toLowerCase().contains(text.toLowerCase()) ||
                    b.writer.toLowerCase().contains(text.toLowerCase())) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No matching book found.");
    }

    Book findBook(int id) {
        for (Book b : bookList) {
            if (b.id == id) return b;
        }
        return null;
    }

    Member findMember(int id) {
        for (Member m : memberList) {
            if (m.id == id) return m;
        }
        return null;
    }

    void showIssuedBooks() {
        if (issued.isEmpty()) {
            System.out.println("No books are currently issued.");
            return;
        }
        System.out.println("=== Issued Books ===");
        for (Map.Entry<Integer, Integer> entry : issued.entrySet()) {
            Book b = findBook(entry.getKey());
            Member m = findMember(entry.getValue());
            System.out.println("Book: " + b.name + " -> Issued to: " + m.fullName);
        }
    }
}

public class Task_5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        while (true) {
            System.out.println("\n===== LIBRARY MENU =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. View Books");
            System.out.println("4. View Members");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Search Book");
            System.out.println("8. Issued Report");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int ch;
            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (ch) {
                case 1 -> {
                    System.out.print("Enter book title: ");
                    String t = sc.nextLine();
                    System.out.print("Enter author: ");
                    String a = sc.nextLine();
                    lib.addBook(t, a);
                }
                case 2 -> {
                    System.out.print("Enter member name: ");
                    String n = sc.nextLine();
                    lib.addMember(n);
                }
                case 3 -> lib.listBooks();
                case 4 -> lib.listMembers();
                case 5 -> {
                    System.out.print("Enter Book ID: ");
                    int bid = sc.nextInt();
                    System.out.print("Enter Member ID: ");
                    int mid = sc.nextInt();
                    sc.nextLine();
                    lib.issueBook(bid, mid);
                }
                case 6 -> {
                    System.out.print("Enter Book ID to return: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    lib.returnBook(id);
                }
                case 7 -> {
                    System.out.print("Enter title or author to search: ");
                    String s = sc.nextLine();
                    lib.searchBook(s);
                }
                case 8 -> lib.showIssuedBooks();
                case 9 -> {
                    System.out.println("Exiting Goodbye");
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }
}

