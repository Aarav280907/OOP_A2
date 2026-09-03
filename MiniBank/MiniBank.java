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

        // Create a customer and assign a new customer ID.
        public Customer(String name, String email, String mobile, Address address) {
            this.name = name;
            this.email = email;
            this.mobile = mobile;
            this.address = address;
            this.customerId = generateCustomerId();
        }

        // Keep the customer's address together as one value.
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

        // Expose the customer details needed by the bank.
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

        // Copy the customer, including a separate copy of the address.
        @Override
        public Customer clone() {
            try {
                Customer copy = (Customer) super.clone();

                // Clone the address so the two customers do not share it.
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

        // Show the customer's details in a readable format.
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


    // Represents a bank account and its current balance.
    public static class Account {
        private final String accountNumber;
        private String ownerName;
        private long balance;
        private boolean active;

        private static long accountCounter = 0;

        private static String generateAccountNumber() {
            accountCounter++;
            return String.format("AC%04d", accountCounter);
        }

        // Open an account with an initial balance.
        public Account(String ownerName, long openingBalance) {
            this.ownerName = ownerName;
            this.balance = openingBalance;
            this.accountNumber = generateAccountNumber();
            this.active = true;
        }

        // Open an account with no opening balance.
        public Account(String ownerName) {
            this(ownerName, 0);
        }

        // Add money only when the amount is positive.
        public void deposit(long amount) {
            if (amount > 0) {
                balance += amount;
                System.out.println("Amount deposited successfully.");
            } else {
                System.out.println("Invalid amount.");
            }
        }

        // Withdraw money when the account has enough funds.
        public boolean withdraw(long amount) {
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }

            return false;
        }

        // Provide the account details used by the program.
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

        // Show the account details in a readable format.
        @Override
        public String toString() {
            return "Account[" +
                    "Account Number: " + accountNumber +
                    ", Owner: " + ownerName +
                    ", Balance: Rs." + balance +
                    "]";
        }

        // Accounts are equal when they have the same account number.
        @Override
        public boolean equals(Object o) {

            // The same object is always equal to itself.
            if (this == o)
                return true;

            // Objects of other types cannot represent this account.
            if (!(o instanceof Account))
                return false;

            Account account = (Account) o;

            // The account number uniquely identifies the account.
            return Objects.equals(
                    accountNumber,
                    account.accountNumber
            );
        }

        // Keep the hash code consistent with equals().
        @Override
        public int hashCode() {
            return Objects.hash(accountNumber);
        }
    }


    // Run the bank demonstration from the command line.
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("======================================");
        System.out.println("          WELCOME TO MINIBANK");
        System.out.println("======================================");

        // Read the first customer's details.
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


        // Open the first customer's account.
        System.out.print("\nEnter opening balance for Customer 1: ");
        long balance1 = sc.nextLong();
        sc.nextLine();

        Account account1 =
                new Account(customer1.getName(), balance1);


        // Read the second customer's details.
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


        // Open the second customer's account.
        System.out.print("\nEnter opening balance for Customer 2: ");
        long balance2 = sc.nextLong();
        sc.nextLine();

        Account account2 =
                new Account(customer2.getName(), balance2);


        // Display both customers.
        System.out.println("\n======================================");
        System.out.println("         CUSTOMER DETAILS");
        System.out.println("======================================");

        System.out.println(customer1);
        System.out.println(customer2);


        // Display both accounts.
        System.out.println("\n======================================");
        System.out.println("          ACCOUNT DETAILS");
        System.out.println("======================================");

        // println() uses each account's toString() method here.
        System.out.println(account1);
        System.out.println(account2);


        // Compare the two accounts by account number.
        System.out.println("\n======================================");
        System.out.println("             EQUALS TEST");
        System.out.println("======================================");

        System.out.println(
                "Are Account 1 and Account 2 equal? "
                        + account1.equals(account2)
        );


        // Display the hash code for each account.
        System.out.println("\nHashCode of Account 1: "
                + account1.hashCode());

        System.out.println("HashCode of Account 2: "
                + account2.hashCode());


        // Demonstrate copying a customer safely.
        System.out.println("\n======================================");
        System.out.println("             CLONE TEST");
        System.out.println("======================================");

        Customer clonedCustomer = customer1.clone();

        System.out.println("Original Customer:");
        System.out.println(customer1);

        System.out.println("\nCloned Customer:");
        System.out.println(clonedCustomer);


        // Check the runtime types of the objects.
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


        // Demonstrate depositing and withdrawing money.
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

        // Try the validators and parse one sample command.
        System.out.println("\n======================================");
        System.out.println("      VALIDATION AND COMMAND TEST");
        System.out.println("======================================");

        System.out.println("Mobile (valid): " + Validator.isValidMobile("9876543210"));
        System.out.println("Mobile (invalid): " + Validator.isValidMobile("12345"));
        System.out.println("Email (valid): " + Validator.isValidEmail("user@example.com"));
        System.out.println("Email (invalid): " + Validator.isValidEmail("user@example"));
        System.out.println("PAN (valid): " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("PAN (invalid): " + Validator.isValidPan("ABC123"));
        System.out.println("IFSC (valid): " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("IFSC (invalid): " + Validator.isValidIfsc("SBI123"));

        Command command = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("Command type: " + command.type());
        System.out.println("Command account: " + command.accountNumber());
        System.out.println("Command amount: " + command.amount());

        System.out.println("\n" + StatementFormatter.buildStatement(account1));


        System.out.println("\n======================================");
        System.out.println("           PROGRAM ENDED");
        System.out.println("======================================");

        sc.close();
    }
}