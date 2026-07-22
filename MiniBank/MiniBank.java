import java.util.Scanner;

public class MiniBank {

    // (3) Record for bank info
    record BankInfo(String name, String branch) {}

    // (4) Enum for menu options
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {
        // Print header
        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println("==================================");
        System.out.println("Welcome to " + bank.name() + " - " + bank.branch());
        System.out.println("==================================");

        Scanner sc= new Scanner(System.in);
        boolean running = true;

        while (running) {
            // (5) Display menu
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            // (6) Switch expression
            String message = switch (choice) {
                case 1 -> "Open Account — to be implemented in a later lab";
                case 2 -> "Deposit — to be implemented in a later lab";
                case 3 -> "Withdraw — to be implemented in a later lab";
                case 4 -> "Transfer — to be implemented in a later lab";
                case 5 -> {
                    running = false;
                    yield "Goodbye! Thank you for using MiniBank.";
                }
                default -> "Invalid choice. Please try again.";
            };

            System.out.println(message);
        }

        sc.close();
    }
}
