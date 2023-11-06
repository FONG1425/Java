/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author JiinWei
 */
class StockReport {
    
      public static void Report() {
        Scanner scanner = new Scanner(System.in);
        String stockReport = "stockReport.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(stockReport))) {
            String line;

            System.out.println("================================================================================================");
            System.out.printf("| %-6s | %-55s | %-10s | %-12s |\n", "Code", "Flower", "CurrentQty", "Total AddQty");
            System.out.println("================================================================================================");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String code = parts[0].trim();
                    String flower = parts[1].trim();
                    int currentQty = Integer.parseInt(parts[2].trim());
                    int ttlAdd = Integer.parseInt(parts[3].trim());

                    System.out.printf("| %-6s | %-55s | %-10s | %-12s |\n",
                            code, flower, currentQty, ttlAdd);
                }

                System.out.println("------------------------------------------------------------------------------------------------");

            }

            System.out.print("Press enter to continue...");
            scanner.nextLine();
            Stock.stock();

        } catch (IOException e) {
        }

    }
}

