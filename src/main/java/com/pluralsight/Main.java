package com.pluralsight;

import java.util.*;
import java.io.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ArrayList<Transaction> transactions = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String FILE_NAME = "transactions.csv";

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        System.out.println("");
    }

    private static void loadTransactions(String fileName) {

    }
}