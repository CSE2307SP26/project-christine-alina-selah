package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.BankAccount;

public class TransferTest {

    private BankAccount account1;
    private BankAccount account2;

    @BeforeEach
    public void setup() {
        account1 = new BankAccount("account1", "1234");
        account2 = new BankAccount("account2", "1234");
        account1.deposit(50);
    }

    @Test
    public void testTransferToAnotherAccount() {
        account1.transfer(account1,account2, 20);
        assertEquals(30, account1.getBalance(), 0.01);
    }

    @Test
    public void testTransferToNullAccount() {
        try {
            account1.transfer(account1,null, 20);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testTransferWhenInsufficientFund() {
        try {
            account1.transfer(account1, account2, 100);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testTransferToSameAcccount() {
        try {
            account1.transfer(account1, account1, 20);
        } catch (IllegalArgumentException e) {
            
        }
    }
    
}
