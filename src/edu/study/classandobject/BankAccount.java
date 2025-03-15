package edu.study.classandobject;

public class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void deposit(double amount){
        if (amount >= 0) {
            this.balance += amount;
            System.out.println("Deposited " + amount + " to " + accountHolderName + " successfully.");
        }else {
            System.out.println("Invalid amount");
        }
    }

    public void withdraw(double amount){
        if (amount >= 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.println("Withdrawn " + amount + " to " + accountHolderName + " successfully.");
        }else if(amount >= this.balance){
            System.out.println("Insufficient balance");
        }else {
            System.out.println("invalid balance");
        }
    }

    public void displayAccountInfo(){
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }
}
