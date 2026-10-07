package service;

import exception.InvalidAmountException;
import model.Account;

/** PR-9: a Runnable that deposits into one shared account many times. */
public class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " started  (state = " + Thread.currentThread().getState() + ")");
        for (int i = 0; i < times; i++) {
            try {
                account.deposit(amount);
            } catch (InvalidAmountException e) {
                System.out.println(name + " failed: " + e.getMessage());
                return;
            }
        }
        System.out.println(name + " finished");
    }
}
