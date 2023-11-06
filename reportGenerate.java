/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class reportGenerate {

    public static void printSalesReport() {
        try (BufferedReader br = new BufferedReader(new FileReader("sales.txt"))) {
            String line;

            // Calculate the number of spaces to center the title
            int titleWidth = 50; // Adjust this value based on your desired width
            String title = "Sales Report";
            int spacesBeforeTitle = (titleWidth - title.length()) / 2;

            // Print the header for the sales report with centered title
            System.out.println("==================================================");
            System.out.printf("%" + spacesBeforeTitle + "s%s%" + spacesBeforeTitle + "s%n", "", title, "");
            System.out.println("==================================================");
            System.out.printf("%-12s | %-9s | %-8s | %-20s%n", "Sales Order", "Item Code", "Quantity", "Remark");
            System.out.println("--------------------------------------------------");

            // Read and print each line of the sales data
            while ((line = br.readLine()) != null) {
                // Split the line into fields based on the delimiter (| in this case)
                String[] fields = line.split("\\|");

                // Check if the line has the expected number of fields
                if (fields.length == 4) {
                    String salesOrder = fields[0];
                    String itemCode = fields[1];
                    int quantity = Integer.parseInt(fields[2]);
                    String remark = fields[3];

                    // Print the sales data with proper formatting
                    System.out.printf("%-12s | %-9s | %-8d | %-20s%n", salesOrder, itemCode, quantity, remark);
                }
            }

            // Print a line to separate the report from the footer
            System.out.println("--------------------------------------------------");

            System.out.print("Press enter to back...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            mainSales.salesMainMenu();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}



