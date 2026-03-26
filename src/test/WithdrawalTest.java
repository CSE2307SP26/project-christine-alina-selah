package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.BankAccount;
import main.Withdrawal;

public class WithdrawalTest {
    
    private BankAccount account;

    @BeforeEach
    public void setup() {
        account = new BankAccount();
    }

       @Test
    public void testWithdrawalValidAmount() {
        account.deposit(50);
        Withdrawal.withdrawlMoney(account, 25);
        assertEquals(25, account.getBalance(), 0.01);
    }

    @Test
    public void testWithdrawalWithInsufficientFund() {
       
        account.deposit(15);
        try {
            Withdrawal.withdrawlMoney(account, 25);
            fail();
        } catch (IllegalArgumentException e) {

        }
    } 

    @Test
    public void testWithdrawalInvalidAmount() {
        try {
            Withdrawal.withdrawlMoney(account, -10);
            fail();
        }catch(IllegalArgumentException e) {
            
        }

    }
    
}
