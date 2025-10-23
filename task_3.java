import java.util.*;

class BankAcc {
    private String id;
    private String pin;
    private double bal;
    private ArrayList<String> transList;

    public BankAcc(String id, String pin) {
        this.id = id;
        this.pin = pin;
        this.bal = 0;
        this.transList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean checkPin(String p) {
        return pin.equals(p);
    }

    public void deposit(double amt) {
        if (amt <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        bal += amt;
        transList.add("Deposited Rs." + amt);
        System.out.println("Rs." + amt + " deposited successfully.");
        showBal();
    }

    public void withdraw(double amt) {
        if (amt <= 0) {
            System.out.println("Enter valid amount.");
            return;
        }
        if (amt > bal) {
            System.out.println("Not enough balance.");
            return;
        }
        bal -= amt;
        transList.add("Withdrawn Rs." + amt);
        System.out.println("Rs." + amt + " withdrawn successfully.");
        showBal();
    }

    public void transfer(BankAcc rec, double amt) {
        if (rec == null) {
            System.out.println("Receiver not found.");
            return;
        }
        if (amt <= 0 || amt > bal) {
            System.out.println("Transfer failed, check amount.");
            return;
        }
        bal -= amt;
        rec.bal += amt;
        transList.add("Transferred Rs." + amt + " to " + rec.getId());
        rec.transList.add("Received Rs." + amt + " from " + this.id);
        System.out.println("Money transferred successfully.");
        showBal();
    }

    public void showBal() {
        System.out.println("Current balance: Rs." + bal);
    }

    public void showTrans() {
        if (transList.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String t : transList) {
                System.out.println(t);
            }
        }
    }
}

class ATM {
    private HashMap<String, BankAcc> users;
    private BankAcc current;
    private Scanner sc;

    public ATM() {
        users = new HashMap<>();
        sc = new Scanner(System.in);

        // here is a some user id and password for a sample to check this atm interface
        users.put("user1", new BankAcc("user1", "1234"));
        users.put("user2", new BankAcc("user2", "4321"));
    }

    public void start() {
        System.out.println("Welcome to ATM");
        System.out.print("Enter user id: ");
        String id = sc.nextLine();
        System.out.print("Enter pin: ");
        String pin = sc.nextLine();

        if (login(id, pin)) {
            System.out.println("Login successful.");
            menu();
        } else {
            System.out.println("Wrong user id or pin.");
        }
    }

    private boolean login(String id, String pin) {
        BankAcc acc = users.get(id);
        if (acc != null && acc.checkPin(pin)) {
            current = acc;
            return true;
        }
        return false;
    }

    private void menu() {
        int ch;
        do {
            System.out.println("\n1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter number only: ");
                sc.next();
            }

            ch = sc.nextInt();

            switch (ch) {
                case 1:
                    current.showTrans();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double w = sc.nextDouble();
                    current.withdraw(w);
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double d = sc.nextDouble();
                    current.deposit(d);
                    break;
                case 4:
                    sc.nextLine(); // consume leftover
                    System.out.print("Enter receiver id: ");
                    String rid = sc.nextLine();
                    BankAcc rcv = users.get(rid);
                    System.out.print("Enter amount to transfer: ");
                    double t = sc.nextDouble();
                    current.transfer(rcv, t);
                    break;
                case 5:
                    System.out.println("Thank you for using ATM.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        } while (ch != 5);
    }
}

public class task_3 {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
