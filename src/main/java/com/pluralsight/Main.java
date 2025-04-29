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

    public static void main(String[] args) throws Exception{

        loadTransactions(FILE_NAME);
//        printArrayList(ledger); // for test

        boolean running = true;

        while(running){
            System.out.println("Welcome to your online ledger home screen! Here are the options: " +
                    "\nD) Add deposit" +
                    "\nP) Make payment" +
                    "\nL) Ledger" +
                    "\nX) Exit");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice){
                case "D" -> addDeposit();
                case "P" -> addPayment();
                case "L" -> displayLedger();
                case "X" -> {
                    running = false;
                    System.out.println("Exiting the system, see you again! ");
                }
                default -> System.out.println("Invalid input, try again. ");
            }
        }
    }

    // for test: generate a given number of transactions and save to file
    private static void generateRandomTransactions(int number){

    }

    private static void loadTransactions(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));

            String input;

            while((input = bufferedReader.readLine()) != null){
                String[] fields = input.split("\\|");

                LocalDate date = LocalDate.parse(fields[0]);
                LocalTime time = LocalTime.parse(fields[1]);
                LocalDateTime localDateTime = LocalDateTime.of(date, time);

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
        System.out.println("Printing the corresponding ledger transactions...");
        for(Transaction transaction : list)
            System.out.println(transaction);
    }

    // foolproof method: guarentees to get int input from user
    private static int getUserInt(){
        System.out.println("Enter a choice in integer: ");
        while( ! scanner.hasNextInt()){
            System.out.println("Invalid input, enter an integer: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // consumes redundant \n

        return result;
    }

    private static void addDeposit() throws IOException {
        System.out.println("Enter description: ");
        String description = scanner.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Enter amount: ");
        float amount  = scanner.nextFloat();
        LocalDateTime dateTime = LocalDateTime.now();

        Transaction t = new Transaction(dateTime, description, vendor, amount);
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(t.toCsvEntry());
        writer.newLine(); // better than adding "\n"
        System.out.println("Deposit added to ledger successfully! ");
    }

    private static void addPayment() throws IOException {
        System.out.println("Enter debit card last 4 digits: ");
        String debitInfo;
        while((debitInfo = scanner.nextLine()).length() != 4)
            System.out.println("Invalid debit info, try again: ");
        String description = "Invoice " + debitInfo + " paid";
        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.println("Enter amount: ");
        float amount = scanner.nextFloat();


    }

    private static void displayLedger(){
        System.out.println("Welcome to the Ledger, here are the options: " +
                "\nA) Display all" +
                "\nD) Deposits" +
                "\nP) Payments" +
                "\nR) Reports" +
                "\nH) Home" +
                "\nEnter your choice: ");

        String choice = scanner.nextLine().toUpperCase();

        switch (choice){
            case "A" -> displayAllEntries();
            case "D" -> displayDeposits();
            case "P" -> displayPayments();
            case "R" -> displayReports();
            case "H" -> System.out.println("Going back to home page...");
            default -> System.out.println("Invalid input, going back to home page...");
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