package com.pluralsight;

import java.lang.*;
import java.time.LocalTime;
import java.util.*;
import java.io.*;
import java.text.*;
import java.time.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ArrayList<Transaction> ledger = new ArrayList<>();
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

        char choice = getUserLetter();
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
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));

            String input;

            while((input = bufferedReader.readLine()) != null){
                String[] fields = input.split("\\|");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(fields[0]);
                LocalTime time = LocalTime.parse(fields[1]);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).with(time);

                String description = fields[2];
                String vendor = fields[3];
                float amount = Float.parseFloat(fields[4]);

                Transaction transaction = new Transaction(localDateTime, description, vendor, amount);
                ledger.add(transaction);
            }

            bufferedReader.close();

        } catch (Exception e) {
            System.out.println("Exception while loading file. ");
            e.printStackTrace();
        }
    }

    private static void printArrayList(ArrayList<Transaction> list){
        for(Transaction transaction : list)
            System.out.println(transaction);
    }

    // foolproof method: guarentees to get letter (upper case) input from user
    private static Character getUserLetter() {
        System.out.println("Enter a letter: ");
        while(true){
            char c = scanner.nextLine().charAt(0);
            if(Character.isLetter(c))
                return Character.toUpperCase(c);
            else
                System.out.println("Invalid input, try again: ");
        }
    }

    // foolproof method: guarentees to get int input from user
    private static int getUserInt(){
        System.out.println("Enter an integer: ");
        while( ! scanner.hasNextInt()){
            System.out.println("Invalid input, enter an integer: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // consumes redundant \n

        return result;
    }

    private static void addDeposit() {
        ledger.sort(Comparator.comparing(Transaction::getDateTime));
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

        char choice = getUserLetter();

        switch (choice){
            case 'A' -> displayAllEntries();
            case 'D' -> displayDeposits();
            case 'P' -> displayPayments();
            case 'R' -> displayReports();
            default -> System.out.println("Going back to home screen...");
        }
    }

    private static void displayAllEntries(){
        ledger.sort(Comparator.comparing(Transaction::getDateTime));
        printArrayList(ledger);
    }

    private static void displayDeposits(){
        ArrayList<Transaction> results = new ArrayList<>();
        for(Transaction t : ledger)
            if(t.getAmount() > 0)
                results.add(t);
        printArrayList(results);
    }

    private static void displayPayments(){
        ArrayList<Transaction> results = new ArrayList<>();
        for(Transaction t : ledger)
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

        switch(choice){
            case 1 -> {

            }
            case 2 -> {

            }
            case 3 -> {

            }
            case 4 -> {

            }
            case 5 -> {

            }
            case 0 -> {

            }
            default -> {

            }
        }
    }

    private static void getUserDateInput(){

    }
}