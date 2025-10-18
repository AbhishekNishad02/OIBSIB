package Task_2;
import java.util.Random;
import java.util.Scanner;

    public class Game {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            Random Rnum = new Random();
            System.out.print("How many rounds would you like to play? ");
            int rounds = sc.nextInt();
            int totalScore = 0;
            for (int i = 1; i <= rounds; i++) {
                System.out.println("\n--- Round " + i + " ---");
                int Snum = Rnum.nextInt(100) + 1; // 1 to 100
                int attempts = 0;
                int maxAttempts = 10;
                boolean Gcurrect= false;
                while (attempts < maxAttempts) {
                    System.out.print("Enter your guess (1-100): ");
                    int a = sc.nextInt();
                    attempts++;
                    if (a == Snum) {
                        System.out.println("Well done! You take attempt:"+attempts);
                        Gcurrect = true;
                        totalScore += (maxAttempts - attempts + 1) * 10;
                        break;
                    } else if (a < Snum) {
                        System.out.println("low! Try a bigger num.");
                    } else {
                        System.out.println("high! Try a smaller num.");
                    }
                }
            if (!Gcurrect) {
                    System.out.println("Out of attempts! The number was: " + Snum);
                }
                System.out.println("Your score after round " + i+ ": " + totalScore);
            }
            System.out.println("\n Game over! Your total score: " + totalScore);
            sc.close();
        }
    }


