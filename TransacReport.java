/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author qiaorou
 */
public class TransacReport {

    public static void transactionReport() {
        String transactionReport = "transactionReport.txt";
        double totalAmountReport = 0; // Initialize total outside the loop

        System.out.println("Transaction Report");
        System.out.println("=====================");
        System.out.printf("%-20s %-20s %-20s%n", "Transaction Date", "Transaction Time", "Transaction Amount");
        System.out.println("=============================================================");

        try (BufferedReader transactionReader = new BufferedReader(new FileReader(transactionReport))) {
            String read;
            while ((read = transactionReader.readLine()) != null) {
                String[] readParts = read.split("\\|");
                if (readParts.length == 3) {
                    String date = readParts[0].trim();
                    String time = readParts[1].trim();

                    String amountString = readParts[2].trim().replaceAll("\\s*\\|\\s*", "");

                    try {
                        double orderAmount = Double.parseDouble(amountString);
                        System.out.printf("%13s %18s %20.2f%n", date, time, orderAmount);

                        totalAmountReport += orderAmount; // Accumulate the total

                    } catch (NumberFormatException e) {
                        // Handle parsing errors here if needed
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            // Handle IO errors here if needed
            e.printStackTrace();
        }

        System.out.println("=============================================================");
        System.out.printf("Total current amount :%31.2f%n", totalAmountReport);
        System.out.println("=============================================================");
    }

}



