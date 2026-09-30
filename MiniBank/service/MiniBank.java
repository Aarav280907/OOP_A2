package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.*;
import util.*;

import java.util.Scanner;

import static util.Validator.isValidMobile;   // PR-6: the one static import

public class MiniBank {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---------- PR-1: header + menu shell ----------
        BankInfo bank = new BankInfo("MiniBank", "Main Branch");
        System.out.println("==========================================");
        System.out.println("  " + bank);
        System.out.println("==========================================");
        runMenu(sc, bank);

        // ---------- later labs: demos with sample data ----------
        Account[] accounts = demoPr2();
        demoPr3(accounts);
        demoPr4(accounts[0]);
        demoPr5();
        demoPr6(accounts);
        demoPr7();
        demoPr8();

        sc.close();
    }

    // ================= PR-1 =================
    private static void runMenu(Scanner sc, BankInfo bank) {
        MenuOption option;
        do {
            System.out.println("\n--- MAIN MENU ---");
            for (MenuOption m : MenuOption.values()) {
                System.out.println((m.ordinal() + 1) + ". " + m);
            }
            System.out.print("Enter choice: ");

            if (!sc.hasNextLine()) {           // input closed -> behave like EXIT
                option = MenuOption.EXIT;
            } else {
                option = readOption(sc.nextLine());
            }

            if (option == null) {
                System.out.println("Invalid choice. Please enter a number from 1 to " + MenuOption.values().length + ".");
                continue;
            }

            String msg = switch (option) {
                case OPEN_ACCOUNT -> "Open Account - to be implemented in a later lab.";
                case DEPOSIT      -> "Deposit - to be implemented in a later lab.";
                case WITHDRAW     -> "Withdraw - to be implemented in a later lab.";
                case TRANSFER     -> "Transfer - to be implemented in a later lab.";
                case EXIT         -> "Goodbye! Thank you for using " + bank.name() + ".";
            };
            System.out.println(msg);
        } while (option != MenuOption.EXIT);
    }

    /** Converts typed text to a MenuOption; returns null for invalid input. */
    private static MenuOption readOption(String text) {
        try {
            int n = Integer.parseInt(text.trim());
            MenuOption[] all = MenuOption.values();
            if (n >= 1 && n <= all.length) {
                return all[n - 1];
            }
        } catch (NumberFormatException ignored) {
            // fall through
        }
        return null;
    }

    // ================= PR-2 =================
    private static Account[] demoPr2() {
        section("PR-2: Accounts (encapsulation, constructors)");
        Account[] accounts = {
                new SavingsAccount("Asha Patel", 5000, 1000),
                new CurrentAccount("Ravi Shah", 2000, 3000),
                new FixedDepositAccount("Meera Joshi", 50000)
        };
        try {
            accounts[0].deposit(1500);
            accounts[1].withdraw(4000);          // uses overdraft
            accounts[2].deposit(10000);
        } catch (BankException e) {
            System.out.println("Error: " + e.getMessage());
        }
        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + " balance = Rs." + a.getBalance());
        }
        return accounts;
    }

    // ================= PR-3 =================
    private static void demoPr3(Account[] accounts) {
        section("PR-3: toString / equals / hashCode / nested Address / clone");
        for (Account a : accounts) {
            System.out.println(a);
        }
        System.out.println("accounts[0].equals(accounts[1]) = " + accounts[0].equals(accounts[1]));
        System.out.println("accounts[0].equals(accounts[0]) = " + accounts[0].equals(accounts[0]));

        Object o = accounts[0];
        System.out.println("o instanceof Account        = " + (o instanceof Account));
        System.out.println("o instanceof CurrentAccount = " + (o instanceof CurrentAccount));

        Customer.Address address = new Customer.Address("12 MG Road", "Vadodara", "390001");
        Customer c1 = new Customer("Asha Patel", "asha@example.com", "9876543210", address);
        Customer c2 = c1.clone();
        System.out.println(c1);
        System.out.println("Clone is a different object: " + (c1 != c2)
                + ", address copied: " + (c1.getAddress() != c2.getAddress()));
    }

    // ================= PR-4 =================
    private static void demoPr4(Account account) {
        section("PR-4: Validator, CommandParser, StatementFormatter");
        System.out.println("Mobile 9876543210 -> " + isValidMobile("9876543210"));
        System.out.println("Mobile 12345      -> " + isValidMobile("12345"));
        System.out.println("Email a@b.com     -> " + Validator.isValidEmail("a@b.com"));
        System.out.println("Email a@b         -> " + Validator.isValidEmail("a@b"));
        System.out.println("PAN ABCDE1234F    -> " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN abcde1234     -> " + Validator.isValidPan("abcde1234"));
        System.out.println("IFSC SBIN0001234  -> " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC SBIN1001234  -> " + Validator.isValidIfsc("SBIN1001234"));

        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Parsed: type=" + cmd.type()
                + ", account=" + cmd.accountNumber() + ", amount=" + cmd.amount());
        try {
            CommandParser.parse("DEPOSIT AC0001 abc");
        } catch (IllegalArgumentException e) {
            System.out.println("Bad command rejected: " + e.getMessage());
        }
        System.out.println(StatementFormatter.buildStatement(account));
    }

    // ================= PR-5 =================
    private static void demoPr5() {
        section("PR-5: Polymorphism and pattern instanceof");
        Account[] accounts = {
                new SavingsAccount("Asha Patel", 8000, 1000),
                new CurrentAccount("Ravi Shah", 3000, 5000),
                new FixedDepositAccount("Meera Joshi", 60000)
        };
        for (Account a : accounts) {
            System.out.println(a.getClass().getSimpleName() + " -> interestRate = " + a.interestRate() + "%");
            if (a instanceof SavingsAccount s) {          // pattern instanceof
                System.out.println("   minimum balance = Rs." + s.getMinBalance());
            }
        }
    }

    // ================= PR-6 =================
    private static void demoPr6(Account[] accounts) {
        section("PR-6: Interfaces, default method, WithdrawRule, Premium");
        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + " yearlyInterest = Rs." + a.yearlyInterest()
                    + (a instanceof Premium ? "  [Premium]" : ""));
        }

        WithdrawRule anonymous = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return account.getBalance() >= amount;
            }
        };
        WithdrawRule lambda = (account, amount) -> amount <= account.getBalance() / 2;

        System.out.println("Anonymous rule (balance >= 4000)  : " + anonymous.allow(accounts[0], 4000));
        System.out.println("Lambda rule    (amount <= half)   : " + lambda.allow(accounts[0], 4000));
    }

    // ================= PR-7 =================
    private static void demoPr7() {
        section("PR-7: Annotations + reflection");
        Account bad = new SavingsAccount("A Very Long Owner Name Here", -500, 0);
        System.out.println("Validating " + bad);
        for (String error : AnnotationValidator.validate(bad)) {
            System.out.println("  ERROR -> " + error);
        }
        Account good = new SavingsAccount("Asha Patel", 2500, 500);
        System.out.println("Errors for a valid account: " + AnnotationValidator.validate(good).length);
    }

    // ================= PR-8 =================
    private static void demoPr8() {
        section("PR-8: Custom exceptions");
        Account savings = new SavingsAccount("Asha Patel", 5000, 1000);
        Account current = new CurrentAccount("Ravi Shah", 1000, 500);
        Account fixed = new FixedDepositAccount("Meera Joshi", 20000);

        try {
            savings.withdraw(4500);                       // leaves < minBalance
        } catch (InsufficientFundsException e) {
            System.out.println("Withdraw failed: " + e.getMessage() + " (shortfall = " + e.getShortfall() + ")");
        } catch (InvalidAmountException e) {
            System.out.println("Withdraw failed: " + e.getMessage());
        }

        try {
            savings.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }

        try {
            fixed.withdraw(1);                            // locked deposit
        } catch (BankException e) {
            System.out.println("FD withdraw failed: " + e.getMessage());
        }

        try {
            savings.transfer(current, 1500);              // succeeds
            savings.transfer(current, 5000);              // fails
        } catch (BankException e) {
            System.out.println("Caught in main: " + e.getMessage());
        } finally {
            System.out.println("Balances -> savings Rs." + savings.getBalance()
                    + ", current Rs." + current.getBalance());
        }

        try {
            savings.transfer(null, 100);
        } catch (AccountNotFoundException e) {
            System.out.println("Caught: " + e.getMessage());
        } catch (BankException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try (AuditLogger logger = new AuditLogger("AUDIT")) {
            logger.log("Session summary for " + savings.getAccountNumber());
        }
    }

    private static void section(String title) {
        System.out.println("\n==========================================");
        System.out.println(" " + title);
        System.out.println("==========================================");
    }
}
