 import java.util.Scanner;
public class VendingMachine {
    public static enum Coin {
        ONE,TWO,FIVE,TEN
    }

    public static void main(String[] args) {
        System.out.println("Vending Machine");
        int snackPrice = 15;
        int total = 0;
        Scanner sc = new Scanner(System.in);
        while (total < snackPrice) {
            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            String coinName = sc.nextLine().toUpperCase();
            Coin coin = Coin.valueOf(coinName);
            int coinValue = switch (coin) {
                case ONE -> 1;
                case TWO -> 2;
                case FIVE -> 5;
                case TEN -> 10;
            };
            total += coinValue;
            System.out.println("Total so far: " + total);
        }
        System.out.println("Paid. Change: " + (total - snackPrice));
        sc.close();
    }
    
}
