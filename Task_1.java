import java.time.LocalDate;
import java.util.Scanner;
 //login Form
 //here
     class LoginForm {
        static String user = "User1234";
        static String password = "password12";

        LoginForm(String user, String password) {
            this.user = user;
            this.password = password;
        }

        LoginForm() {
        }

        public boolean authenticateUser() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n--- Login ---");
            System.out.print("Enter Username: ");
            String usernameInput = scanner.nextLine();
            System.out.print("Enter Password: ");
            String passwordInput = scanner.nextLine();

            if (usernameInput.equals(user) && passwordInput.equals(password)) {
                System.out.println("Login successful! Welcome.");
                return true;
            } else {
                System.out.println("Invalid username or password.");
                return false;
            }
        }
    }
 // Cancel Form
 //here
     class CancelForm {
        public void cancelReservation() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n--- Cancel Reservation ---");
            System.out.print("Enter PNR Number to cancel: ");
            String pnrInput = scanner.nextLine();

            System.out.println("Reservation with PNR " + pnrInput + " found.");
            System.out.print("Confirm cancellation? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                System.out.println("Reservation with PNR " + pnrInput + " has been successfully cancelled.");
            } else {
                System.out.println("Cancellation not confirmed. Your reservation remains active.");
            }
        }
    }
 // Reserve form
 // here
     class Reserv_Form{
        public void makeReservation() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n--- Make a Reservation ---");

            System.out.print("Enter your Name: ");
            String customerName = scanner.nextLine();

            System.out.print("Enter Train Number: ");
            String trainNumber = scanner.nextLine();

            System.out.print("Enter Train Name: ");
            String trainName= scanner.nextLine();


            System.out.print("Enter Class Type (e.g., AC, Sleeper): ");
            String classType = scanner.nextLine();

            System.out.print("Enter Date of Journey (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            LocalDate dateOfJourney = LocalDate.parse(dateString);

            System.out.print("Enter Origin Station: ");
            String origin = scanner.nextLine();

            System.out.print("Enter Destination Station: ");
            String destination = scanner.nextLine();


            String pnrNumber = "PNR" + System.currentTimeMillis();

            System.out.println("\nReservation successful!");
            System.out.println("Customer Name: " + customerName);
            System.out.println("Train: " + trainName + " (" + trainNumber + ")");
            System.out.println("Class Type: " + classType);
            System.out.println("Date of Journey: " + dateOfJourney);
            System.out.println("Route: " + origin + " to " + destination);
            System.out.println("Your PNR Number: " + pnrNumber);
        }}
 // The main method here
    public class Task_1 {

        public static void main(String[] args) {
         LoginForm loginForm = new LoginForm();
          Reserv_Form reservationSystem = new Reserv_Form();
            CancelForm cancellationForm = new CancelForm();
            Scanner mainScanner = new Scanner(System.in);

            System.out.println("Welcome to the Online Reservation System!");

            boolean loggedIn = loginForm.authenticateUser();

            if (loggedIn) {
                while (true) {
                    System.out.println("\n--- Main Menu ---");
                    System.out.println("1. Make Reservation");
                    System.out.println("2. Cancel Reservation");
                    System.out.println("3. Change (User and Password)");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = mainScanner.nextInt();
                    mainScanner.nextLine();

                    switch (choice) {
                        case 1:
                            reservationSystem.makeReservation();
                            break;
                        case 2:
                            cancellationForm.cancelReservation();
                            break;
                        case 3:
                            System.out.print("Enter your user : ");
                            String a= mainScanner.nextLine();
                            System.out.print("Enter your password: ");
                            String b=mainScanner.nextLine();
                            LoginForm l1=new LoginForm(a,b);
                            System.out.print("Successfully change id and password");
                            boolean loggedIn1 = loginForm.authenticateUser();
                            if(!loggedIn1){
                                System.out.print("Please enter Correct user id and Password");
                                boolean loggedIn2 = loginForm.authenticateUser();
                            }
                            break;
                        case 4:
                            System.out.println("Thank you");
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            } else {
                System.out.println("Authentication failed. Exiting system.");
            }
        }}


