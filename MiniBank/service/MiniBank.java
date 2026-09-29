package service;

import model.*;
import util.*;
import java.util.Scanner;

public class MiniBank {

    record BankInfo(String name, String branch) {}

    enum MenuOption {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // PR-1: BankInfo header — ask user for bank name and branch
        System.out.print("Enter bank name: ");
        String bankName = sc.nextLine();
        System.out.print("Enter branch name: ");
        String branchName = sc.nextLine();
        BankInfo bank = new BankInfo(bankName, branchName);
        System.out.println("==========================================");
        System.out.println("  Welcome to " + bank.name() + " - " + bank.branch());
        System.out.println("==========================================");

        // PR-1: Menu loop
        int choice;
        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            String msg = switch (choice) {
                case 1 -> "Open Account — to be implemented in a later lab.";
                case 2 -> "Deposit — to be implemented in a later lab.";
                case 3 -> "Withdraw — to be implemented in a later lab.";
                case 4 -> "Transfer — to be implemented in a later lab.";
                case 5 -> "Goodbye! Thank you for using " + bank.name() + ".";
                default -> "Invalid option. Please try again.";
            };
            System.out.println(msg);
        } while (choice != 5);

        // PR-2 & PR-3: Create accounts from user input
        System.out.println("\n==========================================");
        System.out.println("         ACCOUNT CREATION (PR-2)");
        System.out.println("==========================================");
        System.out.print("How many accounts do you want to create? ");
        int n = sc.nextInt();
        sc.nextLine();

        Account[] accounts = new Account[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Account " + (i + 1) + " ---");
            System.out.print("Enter owner name: ");
            String owner = sc.nextLine();

            System.out.print("Enter opening balance: ");
            long balance = sc.nextLong();
            sc.nextLine();

            System.out.println("Select account type:");
            System.out.println("  1. Savings Account");
            System.out.println("  2. Current Account");
            System.out.println("  3. Fixed Deposit Account");
            System.out.print("Your choice: ");
            int type = sc.nextInt();
            sc.nextLine();

            accounts[i] = switch (type) {
                case 1 -> {
                    System.out.print("Enter minimum balance: ");
                    long minBal = sc.nextLong();
                    sc.nextLine();
                    yield new SavingsAccount(owner, balance, minBal);
                }
                case 2 -> {
                    System.out.print("Enter overdraft limit: ");
                    long overdraft = sc.nextLong();
                    sc.nextLine();
                    yield new CurrentAccount(owner, balance, overdraft);
                }
                case 3 -> new FixedDepositAccount(owner, balance);
                default -> {
                    System.out.println("Invalid type. Defaulting to Savings with minBalance=1000.");
                    yield new SavingsAccount(owner, balance, 1000);
                }
            };
            System.out.println("Created: " + accounts[i]);
        }

        // PR-3: toString, equals, instanceof
        System.out.println("\n==========================================");
        System.out.println("       ACCOUNT DETAILS (PR-3)");
        System.out.println("==========================================");
        for (Account acc : accounts) {
            System.out.println(acc);
        }
        if (n >= 2) {
            System.out.println("\nAre accounts[0] and accounts[1] equal? "
                    + accounts[0].equals(accounts[1]));
        }
        for (Account acc : accounts) {
            if (acc instanceof SavingsAccount) {
                System.out.println(acc.getOwnerName() + "'s account is a SavingsAccount.");
            } else if (acc instanceof CurrentAccount) {
                System.out.println(acc.getOwnerName() + "'s account is a CurrentAccount.");
            } else if (acc instanceof FixedDepositAccount) {
                System.out.println(acc.getOwnerName() + "'s account is a FixedDepositAccount.");
            }
        }

        // PR-3: deposit and withdraw from user input
        System.out.println("\n==========================================");
        System.out.println("     DEPOSIT / WITHDRAW TEST (PR-3)");
        System.out.println("==========================================");
        System.out.print("Enter account index to deposit into (0 to " + (n-1) + "): ");
        int depIdx = sc.nextInt();
        System.out.print("Enter deposit amount: ");
        long depAmt = sc.nextLong();
        sc.nextLine();
        accounts[depIdx].deposit(depAmt);
        System.out.println("After deposit: " + accounts[depIdx]);

        System.out.print("Enter account index to withdraw from (0 to " + (n-1) + "): ");
        int witIdx = sc.nextInt();
        System.out.print("Enter withdrawal amount: ");
        long witAmt = sc.nextLong();
        sc.nextLine();
        boolean success = accounts[witIdx].withdraw(witAmt);
        System.out.println("Withdrawal " + (success ? "successful" : "failed (insufficient funds or locked)") + ".");
        System.out.println("After withdrawal: " + accounts[witIdx]);

        // PR-4: Validator — user inputs values to validate
        System.out.println("\n==========================================");
        System.out.println("        VALIDATOR TEST (PR-4)");
        System.out.println("==========================================");
        System.out.print("Enter a mobile number to validate: ");
        String mob = sc.nextLine();
        System.out.println("isValidMobile(" + mob + "): " + Validator.isValidMobile(mob));

        System.out.print("Enter an email to validate: ");
        String email = sc.nextLine();
        System.out.println("isValidEmail(" + email + "): " + Validator.isValidEmail(email));

        System.out.print("Enter a PAN to validate: ");
        String pan = sc.nextLine();
        System.out.println("isValidPan(" + pan + "): " + Validator.isValidPan(pan));

        System.out.print("Enter an IFSC to validate: ");
        String ifsc = sc.nextLine();
        System.out.println("isValidIfsc(" + ifsc + "): " + Validator.isValidIfsc(ifsc));

        // PR-4: CommandParser — user types a command string
        System.out.println("\n--- Command Parser Test (PR-4) ---");
        System.out.print("Enter a command (e.g. DEPOSIT AC0001 500): ");
        String cmdLine = sc.nextLine();
        try {
            Command cmd = CommandParser.parse(cmdLine);
            System.out.println("Parsed -> Type: " + cmd.type()
                    + ", Account: " + cmd.accountNumber()
                    + ", Amount: " + cmd.amount());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command: " + e.getMessage());
        }

        // PR-4: StatementFormatter — user picks account to print
        System.out.println("\n--- Statement (PR-4) ---");
        System.out.print("Enter account index for statement (0 to " + (n-1) + "): ");
        int stmtIdx = sc.nextInt();
        sc.nextLine();
        System.out.println(StatementFormatter.buildStatement(accounts[stmtIdx]));

        // PR-5: Polymorphism — loop all accounts
        System.out.println("\n==========================================");
        System.out.println("      POLYMORPHISM DEMO (PR-5)");
        System.out.println("==========================================");
        for (Account acc : accounts) {
            System.out.println(acc.getOwnerName()
                    + " | Rate: " + acc.interestRate() + "%"
                    + " | Yearly Interest: Rs." + acc.yearlyInterest(acc.getBalance()));
        }

        // PR-6: WithdrawRule — user inputs amount to check
        System.out.println("\n==========================================");
        System.out.println("      WITHDRAWRULE DEMO (PR-6)");
        System.out.println("==========================================");

        // Anonymous class
        WithdrawRule rule1 = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.getBalance() >= amount;
            }
        };
        System.out.print("Enter account index to test WithdrawRule (0 to " + (n-1) + "): ");
        int ruleIdx = sc.nextInt();
        System.out.print("Enter amount to check: ");
        long ruleAmt = sc.nextLong();
        sc.nextLine();
        System.out.println("Anonymous rule (balance >= amount): "
                + rule1.allow(accounts[ruleIdx], ruleAmt));

        // Lambda
        WithdrawRule rule2 = (account, amount) -> amount < account.getBalance() / 2;
        System.out.println("Lambda rule (amount < half balance): "
                + rule2.allow(accounts[ruleIdx], ruleAmt));

        sc.close();
    }
}
