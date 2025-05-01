# Accounting Ledger CLI Application

## Overview

This is a simple CLI java application for tracking ledger entries, each entry records their values of date, time, descriptoin, vendor and amount.
It keeps a ledger to store and access all transaction records that are saved in a CSV file. The built-in CSV file has existing entries for testing purpose.
The application supports several features, users can add, filter or search transactions.

## Features

- **Add Transaction**: Add a deposit/payment to the ledger.
- **Ledger**: View and filter transactions in the following ways:
  - All Transactions
  - Deposits
  - Payments
  - Reports
- **Reports**: Generate reports in the following ways:
  - Month to date
  - Previous month
  - Year to date
  - Previous year
  - Specific vendor
- **Custom Search**: Search by date, time, descriptoin, vendor or amount.

## Requirements

- Java 8 or higher

## Setup

1. Clone this repository to your local machine.

    ```bash
    git clone https://github.com/dmDaisy/capstoneOne_accountingLedger
    cd capstoneOne_accountingLedger
    ```

2. Compile the Java files.

    ```bash
    javac -d out src/main/java/com/pluralsight/*.java
    ```

3. Run the application.

    ```bash
    java -cp out com.pluralsight.Main
    ```

## Screenshots

Below are a few screenshots of the application in action:

### Home Screen

![Screenshot 2025-05-01 at 3 33 35 PM](https://github.com/user-attachments/assets/ea040eea-1b3a-4f0f-a9ed-d5a6a5b931ba)

### Add Deposit

![Screenshot 2025-05-01 at 3 35 16 PM](https://github.com/user-attachments/assets/55d1728d-1ab9-4ed4-a0aa-f5fff1d39e70)

### Ledger Screen

![Screenshot 2025-05-01 at 3 35 51 PM](https://github.com/user-attachments/assets/07b8863a-55f4-46ac-956a-349301a987a7)

### Reports Screen

![Screenshot 2025-05-01 at 3 36 26 PM](https://github.com/user-attachments/assets/6c87cc36-9161-4931-990d-3aa361e8e72e)

### Sample Custom Search

![Screenshot 2025-05-01 at 3 38 17 PM](https://github.com/user-attachments/assets/8fb71c64-a8bc-4e7c-89df-a976c33dd054)


### Interesting Piece of Code
**Combining code to avoid repetition**: For example, the `addDeposit()` and `addPayment()` methods are combined into one method `addTransaction(boolean valueIsPositive)` 
and a helper method `saveTransaction(Transaction t, String type, String fileName)` that does all the following repetitive steps that are needed for both methods:
  - Prompt user for transaction details
  - Determine if amount is positive/negative based on transaction type
  - Save transaction to ledger
  - Save transaction to CSV file
  - Print confirmation message

```java
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
    saveTransaction(new Transaction(dateTime, description, vendor, amount), (valueIsPositive ? "deposit" : "payment"), FILE_NAME);
}

// Helper method for saving the transaction to both the ledger and the CSV file
private static void saveTransaction(Transaction t, String transactionType, String fileName) {
    ledger.add(t);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        writer.write(t.toCsvEntry());
        writer.newLine(); // better than adding "\n"
        System.out.println("The following " + transactionType + " is successfully saved to your ledger file!");
        System.out.println(t);
    } catch(IOException e){
        System.out.println("Error writing to file. ");
    }
}
