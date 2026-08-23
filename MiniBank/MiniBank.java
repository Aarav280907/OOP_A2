import java.util.Objects;
import java.util.Scanner;

public class MiniBank {

    // ================= CUSTOMER CLASS =================
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

        // Constructor
        public Customer(String name, String email, String mobile, Address address) {
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.address = address;
            this.customerId = generateCustomerId();
        }

        // ================= NESTED ADDRESS CLASS =================
        public static class Address {
            private String line;
            private String city;
            private String pincode;

            public Address(String line, String city, String pincode) {
                this.line = line;
                this.city = city;
                this.pincode = pincode;
            }

            public String getLine() {
                return line;
            }

            public String getCity() {
                return city;
            }

            public String getPincode() {
                return pincode;
            }

            @Override
            public String toString() {
                return line + ", " + city + " - " + pincode;
            }
        }

        // ================= GETTERS =================
        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getMobile() {
            return mobile;
        }

        public String getCustomerId() {
            return customerId;
        }

        public Address getAddress() {
            return address;
        }

        // ================= CLONE =================
        @Override
        public Customer clone() {
            try {
                Customer copy = (Customer) super.clone();

                // Deep copy of Address
                copy.address = new Address(
                        address.getLine(),
                        address.getCity(),
                        address.getPincode()
                );

                return copy;

            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        // ================= TO STRING =================
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


    // ================= ACCOUNT CLASS =================
    static class Account {
        private final String accountNumber;
        private String ownerName;
        private long balance;
        private boolean active;

        private static long accountCounter = 0;

        private static String generateAccountNumber() {
            accountCounter++;
            return String.format("AC%04d", accountCounter);
        }

        // Constructor with balance
        public Account(String ownerName, long openingBalance) {
            this.ownerName = ownerName;
            this.balance = openingBalance;
            this.accountNumber = generateAccountNumber();
            this.active = true;
        }

        // Constructor without balance
        public Account(String ownerName) {
            this(ownerName, 0);
        }

        // ================= DEPOSIT =================
        public void deposit(long amount) {
            if (amount > 0) {
                balance += amount;
                System.out.println("Amount deposited successfully.");
            } else {
                System.out.println("Invalid amount.");
            }
        }

        // ================= WITHDRAW =================
        public boolean withdraw(long amount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }

            return false;
        }

        // ================= GETTERS =================
        public String getAccountNumber() {
            return accountNumber;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public long getBalance() {
            return balance;
        }

        public boolean isActive() {
            return active;
        }

        // ================= toString() =================
        @Override
        public String toString() {
            return "Account[" +
                    "Account Number: " + accountNumber +
                    ", Owner: " + ownerName +
                    ", Balance: Rs." + balance +
                    "]";
        }

        // ================= equals() =================
        @Override
        public boolean equals(Object o) {

            // Same object
            if (this == o)
                return true;

            // Not an Account object
            if (!(o instanceof Account))
                return false;

            Account account = (Account) o;

            // Compare account numbers
            return Objects.equals(
                    accountNumber,
                    account.accountNumber
            );
        }

        // ================= hashCode() =================
        @Override
        public int hashCode() {
            return Objects.hash(accountNumber);
        }
    }


    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("          WELCOME TO MINIBANK");
        System.out.println("======================================");

        // ================= CUSTOMER 1 INPUT =================
        System.out.println("\nEnter details of Customer 1");

        System.out.print("Enter name: ");
        String name1 = sc.nextLine();

        System.out.print("Enter email: ");
        String email1 = sc.nextLine();

        System.out.print("Enter mobile: ");
        String mobile1 = sc.nextLine();

        System.out.print("Enter address line: ");
        String line1 = sc.nextLine();

        System.out.print("Enter city: ");
        String city1 = sc.nextLine();

        System.out.print("Enter pincode: ");
        String pincode1 = sc.nextLine();

        Customer.Address address1 =
                new Customer.Address(line1, city1, pincode1);

        Customer customer1 =
                new Customer(
                        name1,
                        email1,
                        mobile1,
                        address1
                );


        // ================= ACCOUNT 1 INPUT =================
        System.out.print("\nEnter opening balance for Customer 1: ");
        long balance1 = sc.nextLong();
        sc.nextLine();

        Account account1 =
                new Account(customer1.getName(), balance1);


        // ================= CUSTOMER 2 INPUT =================
        System.out.println("\nEnter details of Customer 2");

        System.out.print("Enter name: ");
        String name2 = sc.nextLine();

        System.out.print("Enter email: ");
        String email2 = sc.nextLine();

        System.out.print("Enter mobile: ");
        String mobile2 = sc.nextLine();

        System.out.print("Enter address line: ");
        String line2 = sc.nextLine();

        System.out.print("Enter city: ");
        String city2 = sc.nextLine();

        System.out.print("Enter pincode: ");
        String pincode2 = sc.nextLine();

        Customer.Address address2 =
                new Customer.Address(line2, city2, pincode2);

        Customer customer2 =
                new Customer(
                        name2,
                        email2,
                        mobile2,
                        address2
                );


        // ================= ACCOUNT 2 INPUT =================
        System.out.print("\nEnter opening balance for Customer 2: ");
        long balance2 = sc.nextLong();
        sc.nextLine();

        Account account2 =
                new Account(customer2.getName(), balance2);


        // ================= DISPLAY CUSTOMERS =================
        System.out.println("\n======================================");
        System.out.println("         CUSTOMER DETAILS");
        System.out.println("======================================");

        System.out.println(customer1);
        System.out.println(customer2);


        // ================= DISPLAY ACCOUNTS =================
        System.out.println("\n======================================");
        System.out.println("          ACCOUNT DETAILS");
        System.out.println("======================================");

        // Java automatically calls toString()
        System.out.println(account1);
        System.out.println(account2);


        // ================= EQUALS =================
        System.out.println("\n======================================");
        System.out.println("             EQUALS TEST");
        System.out.println("======================================");

        System.out.println(
                "Are Account 1 and Account 2 equal? "
                        + account1.equals(account2)
        );


        // ================= HASH CODE =================
        System.out.println("\nHashCode of Account 1: "
                + account1.hashCode());

        System.out.println("HashCode of Account 2: "
                + account2.hashCode());


        // ================= CLONING =================
        System.out.println("\n======================================");
        System.out.println("             CLONE TEST");
        System.out.println("======================================");

        Customer clonedCustomer = customer1.clone();

        System.out.println("Original Customer:");
        System.out.println(customer1);

        System.out.println("\nCloned Customer:");
        System.out.println(clonedCustomer);


        // ================= INSTANCEOF =================
        System.out.println("\n======================================");
        System.out.println("          INSTANCEOF TEST");
        System.out.println("======================================");

        if (account1 instanceof Account) {
            System.out.println("account1 is an Account object.");
        }

        if (customer1 instanceof Customer) {
            System.out.println("customer1 is a Customer object.");
        }

        if (customer1.getAddress() instanceof Customer.Address) {
            System.out.println("Address is a Customer.Address object.");
        }


        // ================= ACCOUNT OPERATIONS =================
        System.out.println("\n======================================");
        System.out.println("          ACCOUNT OPERATION");
        System.out.println("======================================");

        System.out.print("Enter amount to deposit into Account 1: ");
        long depositAmount = sc.nextLong();

        account1.deposit(depositAmount);

        System.out.println("Updated Account 1:");
        System.out.println(account1);


        System.out.print("\nEnter amount to withdraw from Account 1: ");
        long withdrawAmount = sc.nextLong();

        if (account1.withdraw(withdrawAmount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed.");
        }

        System.out.println("Final Account 1:");
        System.out.println(account1);


        System.out.println("\n======================================");
        System.out.println("           PROGRAM ENDED");
        System.out.println("======================================");

        sc.close();
    }
}