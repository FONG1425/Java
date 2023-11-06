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
public class SearchInfo {

    public static void Search() {
        SearchInfo Sea = new SearchInfo();
        Scanner scanner = new Scanner(System.in);
        String stockFlower = "stockFlower.txt";
        String enterSearch;

        System.out.println("[Search stock information]");
        System.out.print("Enter Item Code(F00/X-back) : ");
        enterSearch = scanner.nextLine();

        if (enterSearch.equalsIgnoreCase("x")) {
            Stock.stock();
        }
        //if (enterSearch.startsWith("F")) {
        //CHECK IS START WITH 'F' AND IS 3 DIGIT
        if (enterSearch.matches("F\\d{2}")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(stockFlower))) {
                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    if (line.contains(enterSearch)) {
                        String[] parts = line.split("\\|");
                        if (parts.length == 6) {
                            String code = parts[0].trim();
                            String flower = parts[1].trim();
                            double price = Double.parseDouble(parts[2].trim());
                            int quantity = Integer.parseInt(parts[3].trim());
                            int minQuantity = Integer.parseInt(parts[4].trim());
                            int reorderQuantity = Integer.parseInt(parts[5].trim());

                            System.out.println("==============================================================================================================");
                            System.out.printf("| %-6s | %-55s | %-6s | %-8s | %-6s | %-10s |\n", "Code", "Flower", "Price", "Quantity", "MinQty", "ReorderQty");
                            System.out.println("==============================================================================================================");

                            System.out.printf("| %-6s | %-55s | %-6.2f | %-8d | %-6d | %-10d |\n",
                                    code, flower, price, quantity, minQuantity, reorderQuantity);

                            System.out.println("==============================================================================================================");
                        }
                        found = true;

                        break;
                    }

                }

                if (!found) {
                    Sea.errorSrc();
                }
            } catch (IOException e) {
                System.err.println("Erroor reading the stockFlower.txt file.");
            }

        } else {
            Sea.errorSrc();
        }
        Sea.conSearch();
    }

    public void conSearch() {
        Scanner scanner = new Scanner(System.in);
        char conSrc;

        System.out.print("Continue search? (Y-yes/N-back) :  ");
        conSrc = scanner.nextLine().charAt(0);

        switch (conSrc) {
            case 'Y', 'y' ->
                Search();
            case 'N', 'n' ->
                Stock.stock();

            default -> {
                System.out.println("invalid input. Please enter again.\n");
                conSearch();
            }
        }

    }

    public void errorSrc() {
        System.out.println("invalid input. Please enter again.\n");
        Search();

    }
}