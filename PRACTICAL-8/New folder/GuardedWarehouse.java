/*Stock issue: a Warehouse where issue(item, qty) throws a custom checked
OutOfStockException carrying the shortfall, and InvalidQuantityException for qty ≤ 0.
Process a list of requests, catching and reporting each failure without stopping the run.*/

import java.util.*;

// Custom exceptions
class OutOfStockException extends Exception {
    private final int shortfall;
    public OutOfStockException(int shortfall) {
        super("Out of stock, shortfall: " + shortfall);
        this.shortfall = shortfall;
    }
    public int getShortfall() { return shortfall; }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(int qty) {
        super("Invalid quantity: " + qty);
    }
}

// Warehouse class
class Warehouse {
    private Map<String, Integer> stock = new HashMap<>();
    public Warehouse() {
        stock.put("Widget", 10);
        stock.put("Gadget", 5);
        stock.put("Thingamajig", 0);
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) throw new InvalidQuantityException(qty);
        int available = stock.getOrDefault(item, 0);
        if (available < qty) throw new OutOfStockException(qty - available);
        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " of " + item);
    }
}

// Runner
public class GuardedWarehouse {
    public static void main(String[] args) {
        Warehouse wh = new Warehouse();
        Scanner sc = new Scanner(System.in);
        boolean success = false;

        System.out.println("Welcome to the Guarded Warehouse!");

        while (!success) {
            try {
                System.out.print("Enter item name(Widget/Gadget/Thingamajig): ");
                String item = sc.nextLine().trim();

                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();
                sc.nextLine(); // clear newline

                wh.issue(item, qty);
                success = true;

            } catch (OutOfStockException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InvalidQuantityException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Quantity must be a number.");
                sc.nextLine(); // clear invalid input
            } finally {
                System.out.println("Request attempt logged.");
            }
        }

        sc.close();
        System.out.println("Request succeeded. Program ends.");
    }
}
