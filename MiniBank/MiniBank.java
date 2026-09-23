import java.util.Objects;
import java.util.Scanner;

public class MiniBank {

    // Stores the customer's contact and address details.
    static class Customer implements Cloneable {
        private String name;
        private String email;
        private String mobile;
        private final String customerId;
        private Address address;

        private static long customerCounter = 100;

        private static String generateCustomerId() {
            customerCounter++;
            return "CUST" + customerCounter;
        }

        public Customer(String name, String email, String mobile, Address address) {
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.address = address;
            this.customerId = generateCustomerId();
        }

        public static class Address {
            private String line;
            private String city;
            private String pincode;

            public Address(String line, String city, String pincode) {
                this.line = line;
                this.city = city;
                this.pincode = pincode;
            }

            public String getLine() { return line; }
            public String getCity() { return city; }
            public String getPincode() { return pincode; }

            @Override
            public String toString() {
                return line + ", " + city + " - " + pincode;
            }
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getMobile() { return mobile; }
        public String getCustomerId() { return customerId; }
        public Address getAddress() { return address; }

        @Override
        public Customer clone() {
            try {
                Customer copy = (Customer) super.clone();
                copy.address = new Address(address.getLine(), address.getCity(), address.getPincode());
                return copy;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        @Override
        public String toString() {
            return "Customer[" +
                    customerId +
                    ", Name: " + name +
                    ", Email: " + email +
                    ", Mobile: " + mobile +
                    ", Address: " + address +
                    "]";
        }
    }

    // Abstract Account class
    public static abstract class Account {
        private final String accountNumber;
        private String ownerName;
        protected long balance;
        private boolean active;

        private static long accountCounter = 0;

        private static String generateAccountNumber() {
            accountCounter++;
            return String.format("AC%04d", accountCounter);
        }

        public Account(String ownerName, long openingBalance) {
            this.ownerName = ownerName;
            this.balance = openingBalance;
            this.accountNumber = generateAccountNumber();
            this.active = true;
        }

        public Account(String ownerName) { this(ownerName, 0); }

        public void deposit(long amount) {
            if (amount > 0) {
                balance += amount;
                System.out.println("Amount deposited successfully.");
            } else {
                System.out.println("Invalid amount.");
            }
        }

        public boolean withdraw(long amount) {
            if (amount > 0 && canWithdraw(amount)) {
                balance -= amount;
                return true;
            }
            return false;
        }

        public String getAccountNumber() { return accountNumber; }
        public String getOwnerName() { return ownerName; }
        public long getBalance() { return balance; }
        public boolean isActive() { return active; }

        @Override
        public String toString() {
            return "Account[" +
                    "Account Number: " + accountNumber +
                    ", Owner: " + ownerName +
                    ", Balance: Rs." + balance +
                    "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Account)) return false;
            Account account = (Account) o;
            return Objects.equals(accountNumber, account.accountNumber);
        }

        @Override
        public int hashCode() { return Objects.hash(accountNumber); }

        // Abstract methods
        public abstract double interestRate();
        public abstract boolean canWithdraw(long amount);
    }

    // SavingsAccount subclass
    public static class SavingsAccount extends Account {
        private long minBalance;

        public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
            super(ownerName, openingBalance);
            this.minBalance = minBalance;
        }

        @Override
        public double interestRate() { return 4.0; }

        @Override
        public boolean canWithdraw(long amount) {
            return (balance - amount) >= minBalance;
        }
    }

    // CurrentAccount subclass
    public static class CurrentAccount extends Account {
        private long overdraftLimit;

        public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
            super(ownerName, openingBalance);
            this.overdraftLimit = overdraftLimit;
        }

        @Override
        public double interestRate() { return 0.0; }

        @Override
        public boolean canWithdraw(long amount) {
            return (balance - amount) >= -overdraftLimit;
        }
    }

    // FixedDepositAccount subclass
    public static class FixedDepositAccount extends Account {
        public FixedDepositAccount(String ownerName, long openingBalance) {
            super(ownerName, openingBalance);
        }

        @Override
        public double interestRate() { return 7.0; }

        @Override
        public boolean canWithdraw(long amount) { return false; }
    }

    // Run the bank demonstration from the command line.
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("          WELCOME TO MINIBANK");
        System.out.println("======================================");

        // Existing Customer input code (Customer 1 and Customer 2) remains here...
        // (unchanged from your original version)

        // --- NEW POLYMORPHISM DEMO WITH USER INPUT ---
        System.out.println("\n======================================");
        System.out.println("     POLYMORPHISM WITH ACCOUNT TYPES");
        System.out.println("======================================");

        System.out.print("Enter number of accounts to create: ");
        int n = sc.nextInt();
        sc.nextLine();

        Account[] accounts = new Account[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Account " + (i + 1));

            System.out.print("Enter owner name: ");
            String owner = sc.nextLine();

            System.out.print("Enter opening balance: ");
            long openingBalance = sc.nextLong();
            sc.nextLine();

            System.out.println("Choose account type: ");
            System.out.println("1. SavingsAccount");
            System.out.println("2. CurrentAccount");
            System.out.println("3. FixedDepositAccount");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter minimum balance: ");
                    long minBalance = sc.nextLong();
                    sc.nextLine();
                    accounts[i] = new SavingsAccount(owner, openingBalance, minBalance);
                }
                case 2 -> {
                    System.out.print("Enter overdraft limit: ");
                    long overdraft = sc.nextLong();
                    sc.nextLine();
                    accounts[i] = new CurrentAccount(owner, openingBalance, overdraft);
                }
                case 3 -> {
                    accounts[i] = new FixedDepositAccount(owner, openingBalance);
                }
                default -> {
                    System.out.println("Invalid choice, defaulting to SavingsAccount with minBalance=1000");
                    accounts[i] = new SavingsAccount(owner, openingBalance, 1000);
                }
            }
        }

        System.out.println("\n======================================");
        System.out.println("       ACCOUNT POLYMORPHISM TEST");
        System.out.println("======================================");

        for (Account acc : accounts) {
            System.out.println(acc);
            System.out.println("Interest Rate: " + acc.interestRate() + "%");

            if (acc instanceof SavingsAccount sa) {
                System.out.println("This is a Savings Account with minBalance = " + sa.minBalance);
            }
            System.out.println("--------------------------------------");
        }

        sc.close();
    }
}
