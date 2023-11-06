/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class menu {

    public void main_Menu() {

        System.out.println("=================================================");
        System.out.println("|       WELCOME TO THE HEYYO SALES SYSTEM       |");
        System.out.println("=================================================");
        System.out.println("| Please enter your option :                    |");
        System.out.println("|  1 - Add New Sales Order                      |");
        System.out.println("|  2 - Search Sales Order                       |");
        System.out.println("|  3 - Delete Sales Order                       |");
        System.out.println("|  4 - Display Sales Report                     |");
        System.out.println("|  0 - Back to main page                        |");
        System.out.println("=================================================");

    }

    public void flowerMenu() {
        String menu = "stockFlower.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(menu))) {
            String line;

            System.out.println("=============================================================================");
            System.out.printf("| %-6s | %-55s | %-6s |\n", "Code", "Flower", "Price");
            System.out.println("============================================================================");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String code = parts[0].trim();
                    String flower = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());

                    System.out.printf("| %-6s | %-55s | %-6.2f |\n",
                            code, flower, price);
                }

            }
            System.out.println("=============================================================================");

        } catch (IOException e) {
        }
    }
}