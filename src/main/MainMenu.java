package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 3;
	private static final int MAX_SELECTION = 3;

	private BankAccount userAccount;
    private BankAccount secondAccount;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.secondAccount = new BankAccount();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a transfer");
        System.out.println("3. Exit the app");

    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;

            case 2:
                performTransfer();
                break;
            
            case 3:
                System.out.println("Goodbye");
                break;
            default:
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while(depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        userAccount.deposit(depositAmount);
    }

    public void performTransfer() {
        double transferAmount = -1;
        while(transferAmount < 0) {
            System.out.println("How much money would you like transfer: ");
            transferAmount= keyboardInput.nextInt();

        }
        Transfer.transferService(userAccount, secondAccount, transferAmount);
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
