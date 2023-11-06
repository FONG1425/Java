/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author JiinWei
 */
public class AddStock {

    public static void Adds() {

        System.out.println("[Add Stock]");
        Add();
    }

    public static void Add() {
        AddStock add = new AddStock();
        Scanner scanner = new Scanner(System.in);
        String stockFlower = "stockFlower.txt";
        String enterAdd;
        int ttlAdd = 0;

        System.out.print("Enter Item Code(F00/X-back) : ");
        enterAdd = scanner.nextLine();

        if (enterAdd.equalsIgnoreCase("x")) {
            Stock.stock();
            return;
        }

        //CHECK IS START WITH 'F' AND IS 3 DIGIT
        if (enterAdd.matches("F\\d{2}")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(stockFlower))) {
                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    if (line.contains(enterAdd)) {
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
                    add.errorAdd();
                    return;
                }
            } catch (IOException e) {
                System.err.println("Erroor reading the stockFlower.txt file.");
                return;
            }

        } else {
            add.errorAdd();
        }

        add.addQty(enterAdd, ttlAdd);
    }

    public void addQty(String enterAdd, int ttlAdd) {
        Scanner scanner = new Scanner(System.in);
        int amount, reorder = 30, ttlQty = 0;
        char yesAdd;

        while (true) {
            System.out.print("Enter amount add stock (per 30 flowers): ");
            String input = scanner.nextLine();

            try {
                // Check is the user enter integer
                amount = Integer.parseInt(input);

                if (amount > 0 && amount <= 10) {
                    ttlAdd = amount * reorder;
                    System.out.println("Total add stock quantity: " + ttlAdd + " flowers\n");
                    break;
                } else {
                    System.out.println("Minimum 1 and Maximum 10!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integer only.\n");
            }
        }

        //check is the user enter Y or N only.
        while (true) {
            System.out.print("Confirm add stock? (Y-yes/N-no): ");
            String confirmInput = scanner.nextLine();

            if (confirmInput.length() == 1) {
                yesAdd = confirmInput.charAt(0);
                if (yesAdd == 'Y' || yesAdd == 'y' || yesAdd == 'N' || yesAdd == 'n') {
                    break;
                } else {
                    System.out.println("Please enter Y or N only!\n");
                }
            } else {
                System.out.println("Please enter Y or N only!\n");
            }
        }

        switch (yesAdd) {
            case 'Y':
            case 'y':
                addTxt(enterAdd, ttlAdd, ttlQty);
                break;

            default:
                Add();
                break;
        }

    }

    public static void addTxt(String enterAdd, int ttlAdd, int ttlQty) {
        String tempStock = "tempStock.txt";
        String stockFlower = "stockFlower.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(stockFlower));
            FileWriter writer = new FileWriter(tempStock, false);

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(enterAdd)) {
                    String[] parts = currentLine.split("\\|");
                    if (parts.length == 6) {
                        int quantity = Integer.parseInt(parts[3].trim());
                        quantity += ttlAdd;
                        ttlQty = quantity;

                        parts[3] = String.valueOf(quantity);

                        String updatedLine = String.join("|", parts);

                        writer.write(updatedLine + System.lineSeparator());
                    }
                } else {
                    writer.write(currentLine + System.lineSeparator());
                }
            }
            writer.close();
            reader.close();

            System.out.println("Add stock successfully.Current quantity is " + ttlQty + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        addReport(enterAdd, ttlAdd, ttlQty);
    }

    public static void addReport(String enterAdd, int ttlAdd, int ttlQty) {
        String tempReport = "tempReport.txt";
        String stockReport = "stockReport.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(stockReport));
            FileWriter writer = new FileWriter(tempReport, false);

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(enterAdd)) {
                    String[] parts = currentLine.split("\\|");
                    if (parts.length == 4) {
                        int totalQty = Integer.parseInt(parts[2].trim());
                        int totalAdd = Integer.parseInt(parts[3].trim());
                        totalQty = ttlQty;
                        totalAdd += ttlAdd;

                        parts[2] = String.valueOf(totalQty);
                        parts[3] = String.valueOf(totalAdd);

                        String updatedLine = String.join("|", parts);

                        writer.write(updatedLine + System.lineSeparator());
                    }
                } else {
                    writer.write(currentLine + System.lineSeparator());
                }
            }
            writer.close();
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        addCpy();
    }

//copy temp to ori file
    public static void addCpy() {
        AddStock a = new AddStock();
        
        String tempStock = "tempStock.txt";
        String stockFlower = "stockFlower.txt";

        String tempReport = "tempReport.txt";
        String stockReport = "stockReport.txt";

        try {
            FileReader readerStock = new FileReader(tempStock);
            FileWriter writerStock = new FileWriter(stockFlower);

            FileReader readerReport = new FileReader(tempReport);
            FileWriter writerReport = new FileWriter(stockReport);

            int character;

            // Copy from tempStock to stockFlower
            while ((character = readerStock.read()) != -1) {
                writerStock.write(character);
            }

            // Copy from tempReport to stockReport
            while ((character = readerReport.read()) != -1) {
                writerReport.write(character);
            }

            readerStock.close();
            writerStock.close();
            readerReport.close();
            writerReport.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        a.conAdd();

    }

    public void conAdd() {
        Scanner scanner = new Scanner(System.in);
        char conAdd;

        System.out.print("Continue add stock? (Y-yes/N-back) :  ");
        conAdd = scanner.nextLine().charAt(0);

        switch (conAdd) {
            case 'Y', 'y' ->
                Add();
            case 'N', 'n' ->
                Stock.stock();

            default -> {
                System.out.println("Please enter Y or N only!\n");
                conAdd();
            }
        }

    }

    public void errorAdd() {
        System.out.println("invalid input. Please enter again.\n");
        Add();

    }

}