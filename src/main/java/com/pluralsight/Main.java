package com.pluralsight;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ArrayList<Transaction> transactions = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "transactions.csv";

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        System.out.println("Welcome to home screen! Here is the menu: " +
                "\nD) Add deposit" +
                "\nP) Make payment" +
                "\nL) Ledger" +
                "\nX) Exit" +
                "\nEnter your choice: ");

        char choice = getUserChar();
        boolean running = true;

        while(running){
            switch (choice){
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'L' -> displayLedger();
                case 'X' -> {
                    running = false;
                    System.out.println("Exiting the system, see you again! ");
                }
                default -> System.out.println("Invalid input, try again. ");
            }
        }
    }

    private static void loadTransactions(String fileName) {

    }

    private static void printArrayList(ArrayList<Transaction> list){
        for(Transaction transaction : list)
            System.out.println(transaction);
    }

    private static char getUserChar() {
        return 'A';
    }

    private static int getUserInt(){
        return 1;
    }

    private static void addDeposit() {
        transactions.sort(Comparator.comparing(Transaction::getDateTime));
    }

    private static void makePayment(){

    }

    private static void displayLedger(){
        System.out.println("Welcome to the Ledger, here are the options: " +
                "\nA) Display all" +
                "\nD) Deposits" +
                "\nP) Payments" +
                "\nR) Reports" +
                "\nH) Home" +
                "\nEnter your choice: ");

        char choice = getUserChar();

        switch (choice){
            case 'A' -> displayAllEntries();
            case 'D' -> displayDeposits();
            case 'P' -> displayPayments();
            case 'R' -> displayReports();
            default -> System.out.println("Going back to home screen...");
        }
    }

    private static void displayAllEntries(){
        transactions.sort(Comparator.comparing(Transaction::getDateTime));
        printArrayList(transactions);
    }

    private static void displayDeposits(){
        ArrayList<Transaction> results = new ArrayList<>();
        for(Transaction t : transactions)
            if(t.getAmount() > 0)
                results.add(t);
        printArrayList(results);
    }

    private static void displayPayments(){
        ArrayList<Transaction> results = new ArrayList<>();
        for(Transaction t : transactions)
            if(t.getAmount() < 0)
                results.add(t);
        printArrayList(results);
    }

    private static void displayReports(){
        System.out.println("Welcome to the reports page, here are the options:" +
                "\n1) Month to date" +
                "\n2) Previous month" +
                "\n3) Year to date" +
                "\n4) Previous year" +
                "\n5) Search by vendor" +
                "\n0) Back" +
                "\nEnter your choice: ");

        int choice = getUserInt();

        
    }
}