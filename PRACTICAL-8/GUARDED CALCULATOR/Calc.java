//Guarded calculator: read two numbers and an operator; throw a custom
//DivideByZeroException and catch invalid-number input separately, printing a clear
//message each time; loop until a valid calculation succeeds and use finally to log each
//attempt.
import java.util.Scanner;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class Calc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean success = false;

        System.out.println("Welcome to the Guarded Calculator!");

        while (!success) {
            int num1 = 0, num2 = 0;
            char operator = ' ';

            try {
                System.out.print("Enter the first number: ");
                num1 = sc.nextInt();

                System.out.print("Enter the second number: ");
                num2 = sc.nextInt();

                System.out.print("Enter the operator (+, -, *, /): ");
                operator = sc.next().charAt(0);

                switch (operator) {
                    case '+':
                        System.out.println("Addition: " + (num1 + num2));
                        success = true;
                        break;
                    case '-':
                        System.out.println("Subtraction: " + (num1 - num2));
                        success = true;
                        break;
                    case '*':
                        System.out.println("Multiplication: " + (num1 * num2));
                        success = true;
                        break;
                    case '/':
                        if (num2 == 0) {
                            throw new DivideByZeroException("Cannot divide by zero.");
                        }
                        System.out.println("Division: " + ((double) num1 / num2));
                        success = true;
                        break;
                    default:
                        System.out.println("Invalid operator. Please use +, -, *, or /.");
                }

            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid numbers.");
                sc.nextLine(); // clear the invalid input
            } finally {
                System.out.println("Calculation attempt logged.");
            }
        }

        sc.close();
        System.out.println("Calculation succeeded. Program ends.");
    }
}
