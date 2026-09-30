package model;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing {

    @Id
    private final String accountNumber;
    @MaxLength(20)
    private String ownerName;
    @Positive
    private long balance;          // changes only via deposit() / withdraw()
    private boolean active;

    private static long accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // ---- PR-8: exceptions instead of boolean flags ----
    @Override
    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        balance += amount;
    }

    @Override
    public boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(amount - availableToWithdraw());
        }
        balance -= amount;
        return true;
    }

    /** Moves money to another account; rolls back if the credit side fails. */
    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("null");
        }
        boolean debited = false;
        try {
            withdraw(amount);
            debited = true;
            to.deposit(amount);
            System.out.println("Transferred Rs." + amount + " from " + accountNumber + " to " + to.accountNumber);
        } catch (BankException e) {
            if (debited) {
                balance += amount;      // rollback the debit
            }
            System.out.println("Transfer failed: " + e.getMessage());
            throw e;                    // rethrow to the caller
        } finally {
            System.out.println("Transfer attempt finished (" + accountNumber + " -> " + to.accountNumber + ").");
        }
    }

    public String getAccountNumber() { return accountNumber; }

    public String getOwnerName() { return ownerName; }

    @Override
    public long getBalance() { return balance; }

    public boolean isActive() { return active; }

    /** How much can currently be taken out; used to compute the shortfall. */
    protected long availableToWithdraw() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account[accountNumber=" + accountNumber
                + ", ownerName=" + ownerName
                + ", balance=Rs." + balance + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account other = (Account) o;
        return Objects.equals(accountNumber, other.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);
}
