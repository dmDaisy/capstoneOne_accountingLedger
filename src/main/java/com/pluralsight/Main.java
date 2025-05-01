package com.pluralsight;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.time.*;

/*
 * CSV file info:
 * time range: 2023-01-01 to 2025-04-30
 * vendor range:
 *       names: Alex, Yuchen, name 1, name 2, name 3, name 4
 *       companies: Apple, Microsoft, Google, Facebook, Tesla, Netflix, Amazon, YouTube, Uber, Walmart
 * */
public class Main {

    static ArrayList<Transaction> ledger = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String FILE_NAME = "transactions.csv";

    public static void main(String[] args) throws Exception{

        loadTransactions(FILE_NAME);
//        printArrayList(ledger); // for test
//        System.out.println("Absolute file path: " + new File(FILE_NAME).getAbsolutePath()); // for test

        boolean running = true;
        while(running){
            System.out.println("\nWelcome to home screen! Here are the options: " +
                    "\nD) Add deposit" +
                    "\nP) Make payment" +
                    "\nL) Ledger" +
                    "\nX) Exit");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice){
                case "D" -> addTransaction(true);
                case "P" -> addTransaction(false);
                case "L" -> displayLedger(true);
                case "X" -> {
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
        }
    }

    // add deposit or payment
    private static void addTransaction(boolean valueIsPositive){
        System.out.println("Enter description: ");
        String description = scanner.nextLine();
        System.out.println("Enter vendor: ");
        String vendor = scanner.nextLine();

        // foolproof to ensure amount >= 0 for deposit and amount < 0 for payment
        float amount = getUserFloat();
        if((valueIsPositive && amount < 0) || ( ! valueIsPositive && amount > 0)) amount *= -1;

        LocalDateTime dateTime = LocalDateTime.now();

        saveTransaction(new Transaction(dateTime, description, vendor, amount), (valueIsPositive ? "deposit" : "payment"));
    }

    private static void displayLedger(boolean needToSort) throws Exception {

        if (needToSort) sortLedgerByTimeNewest();

        boolean inLedgerMenu = true;
        while (inLedgerMenu) {
            System.out.println("\nWelcome to the ledger page, here are the options: " +
                    "\nA) Display all" +
                    "\nD) Deposits" +
                    "\nP) Payments" +
                    "\nR) Reports" +
                    "\nH) Home" +
                    "\nEnter your choice: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A" -> printArrayList(ledger);
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> displayReports();
                case "H" -> {
                    System.out.println("Going back to home page...");
                    inLedgerMenu = false;
                }
                default -> System.out.println("Invalid input, try again: ");
            }
        }
    }

    private static void displayDeposits(){
        System.out.println("Printing all deposits: ");
        for(Transaction t : ledger)
            if(t.getAmount() > 0)
                System.out.println(t);
    }

    private static void displayPayments(){
        System.out.println("Printing all payments: ");
        for(Transaction t : ledger)
            if(t.getAmount() < 0)
                System.out.println(t);
    }

    private static void displayReports() throws Exception {
        boolean inReportsMenu = true;
        while (inReportsMenu) {
            System.out.println("\nWelcome to the reports page, here are the options:" +
                    "\n1) Month to date" +
                    "\n2) Previous month" +
                    "\n3) Year to date" +
                    "\n4) Previous year" +
                    "\n5) Search by vendor" +
                    "\n6) Custom search" +
                    "\n0) Back to ledger page");

            int choice = getUserInt();

            switch (choice) {
                case 1 -> displayMonthToDate();
                case 2 -> displayPreviousMonth();
                case 3 -> displayYearToDate();
                case 4 -> displayPreviousYear();
                case 5 -> searchByVendor();
                case 6 -> customSearch();
                case 0 -> {
                    System.out.println("Going back to ledger page...");
                    inReportsMenu = false;
                }
                default -> System.out.println("Invalid input, try again.");
            }
        }
    }

    private static void displayMonthToDate() {
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        System.out.println("\nAll transactions from " + today.withDayOfMonth(1) + " to " + today);
        for (Transaction t : ledger) {
            if(t.getDateTime().toLocalDate().getMonthValue() != currentMonth)
                break; // ledger is sorted so break once condition not met
            System.out.println(t);
        }
    }


    private static void displayPreviousMonth() {
        LocalDate today = LocalDate.now();
        int lastMonth = today.minusMonths(1).getMonthValue();
        int yearOfLastMonth = today.minusMonths(1).getYear();
        System.out.println("\nAll transactions of last month: ");
        for (Transaction t : ledger) {
            LocalDate date = t.getDateTime().toLocalDate();
            if(date.getMonthValue() == lastMonth && date.getYear() == yearOfLastMonth)
                System.out.println(t);
        }
    }

    private static void displayYearToDate() {
        int currentYear = LocalDate.now().getYear();
        System.out.println("\nAll transactions from start of " + currentYear + " to today:");
        for (Transaction t : ledger) {
            if (t.getDateTime().toLocalDate().getYear() != currentYear)
                break; // ledger is sorted so break once year doesn't match
            System.out.println(t);
        }
    }

    private static void displayPreviousYear() {
        int previousYear = LocalDate.now().minusYears(1).getYear();
        System.out.println("\nAll transactions from last year:");
        for (Transaction t : ledger) {
            if (t.getDateTime().toLocalDate().getYear() == previousYear)
                System.out.println(t);
        }
    }

    private static void searchByVendor() {
        System.out.println("Enter vendor name: ");
        String vendor = scanner.nextLine().trim();
        System.out.println("\nHere are all the transactions with " + vendor + ": ");
        for(Transaction t : ledger)
            if(t.getVendor().equalsIgnoreCase(vendor))
                System.out.println(t);
    }

    private static void customSearch() {
        try{
            System.out.println("Hit Enter key to skip each of the following values prompted. ");

            System.out.println("Enter start date in format of 'yyyy-MM-dd': ");
            String startDateString = scanner.nextLine();
            LocalDate startDate = startDateString.isEmpty() ? null : LocalDate.parse(startDateString);

            System.out.println("Enter end date in format of 'yyyy-MM-dd': ");
            String endDateString = scanner.nextLine();
            LocalDate endDate = endDateString.isEmpty() ? null : LocalDate.parse(endDateString);

            System.out.println("Enter description: ");
            String description = scanner.nextLine().trim();

            System.out.println("Enter vendor: ");
            String vendor = scanner.nextLine().trim();

            Float amount = null; // error if writing: float amount = null;
            System.out.println("Reply Y if you wanna enter amount: ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if(choice.equals("Y")){
                System.out.println("Enter amount in float: ");
                amount = getUserFloat();
            }

            System.out.println("\nSearch results below: ");
//            boolean isMatch;
//            int nullCounter;
            for (Transaction t : ledger) {
//                isMatch = true;
//                nullCounter = 0;
                LocalDate tDate = t.getDateTime().toLocalDate();

                if(startDate == null && endDate == null && description.isEmpty() && vendor.isEmpty() && amount == null) continue;
                if (startDate != null && tDate.isBefore(startDate)) continue;
                if (endDate != null && tDate.isAfter(endDate)) continue;
                if (!description.isEmpty() && !t.getDescription().toLowerCase().contains(description.toLowerCase())) continue;
                if (!vendor.isEmpty() && !t.getVendor().equalsIgnoreCase(vendor)) continue;
                if (amount != null && t.getAmount() != amount) continue;

                System.out.println(t);
            }

        } catch (Exception e){
            System.out.println("Invalid input, check and try again. ");
            e.printStackTrace();
        }
    }











    //  helper methods at the bottom
//
//
//
    // for test: generate a given number of transactions and save to file
    private static void generateRandomTransactions(int number) {
    }

    // sort ledger, show the newest entries first
    private static void sortLedgerByTimeNewest(){
        ledger.sort(Comparator.comparing(Transaction::getDateTime).reversed());
    }

    // print specifically Transaction array
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
            scanner.nextLine(); // or scanner.next() ?
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // consumes redundant \n, or scanner.next() ?

        return result;
    }

    // foolproof method: guarentees to get float input from user
    private static float getUserFloat(){
        System.out.println("Enter a float amount: ");
        while( ! scanner.hasNextFloat()){
            System.out.println("Invalid input, enter a float: ");
            scanner.nextLine(); // or scanner.next() ?
        }
        float result = scanner.nextFloat();
        scanner.nextLine(); // consumes redundant \n, or scanner.next() ?

        return result;
    }

//    private static void addTransactionToCsvFile(Transaction t, String transactionType) throws Exception{
//        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
//        writer.write(t.toCsvEntry());
//        writer.newLine(); // better than adding "\n"
//        System.out.println("The following " + transactionType + " successfully added to ledger! ");
//        System.out.println(t);
//    }

    // save transaction to ledger and CSV file
    // java syntax: try-with-resources
    // automatically closes BufferedWriter even if an error occurs
    private static void saveTransaction(Transaction t, String transactionType) {

        ledger.add(t);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(t.toCsvEntry());
//            System.out.println("CSV entry: " + t.toCsvEntry()); // for test
            writer.newLine(); // better than adding "\n"
            System.out.println("The following " + transactionType + " is successfully saved to your ledger file!");
            System.out.println(t);
        } catch(IOException e){
            System.out.println("Error writing to file. ");
        }
    }

}