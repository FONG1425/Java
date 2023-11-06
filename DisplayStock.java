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
public class DisplayStock {

    public static void DisplayS() {

        System.out.println("[Stock Information List]");
        Display();
    }

    public static void Display() {
        Scanner scanner = new Scanner(System.in);
        String stockFlower = "stockFlower.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(stockFlower))) {
            String line;

            System.out.println("==============================================================================================================");
            System.out.printf("| %-6s | %-55s | %-6s | %-8s | %-6s | %-10s |\n", "Code", "Flower", "Price", "Quantity", "MinQty", "ReorderQty");
            System.out.println("==============================================================================================================");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String code = parts[0].trim();
                    String flower = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int quantity = Integer.parseInt(parts[3].trim());
                    int minQuantity = Integer.parseInt(parts[4].trim());
                    int reorderQuantity = Integer.parseInt(parts[5].trim());

                    System.out.printf("| %-6s | %-55s | %-6.2f | %-8d | %-6d | %-10d |\n",
                            code, flower, price, quantity, minQuantity, reorderQuantity);
                }

            }
            System.out.println("==============================================================================================================");

            System.out.print("Press enter to continue...");
            scanner.nextLine();
            Stock.stock();

        } catch (IOException e) {
        }

    }
}


