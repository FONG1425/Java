/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author JiinWei
 */
public class DeleteStock {

    public static void DeletcS() {

        System.out.println("[Delete Stock]");
        Delete();
    }

    public static void Delete() {
        DeleteStock dlt = new DeleteStock();
        Scanner scanner = new Scanner(System.in);

        String stockFlower = "stockFlower.txt";

        System.out.print("Enter Item Code(F00/X-back) : ");
        String enterDlt = scanner.nextLine();

        if (enterDlt.equalsIgnoreCase("x")) {
            Stock.stock();
        }

        //CHECK IS START WITH 'F' AND IS 3 DIGIT
        if (enterDlt.matches("F\\d{2}")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(stockFlower))) {
                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    if (line.contains(enterDlt)) {
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
                    dlt.errorDlt();
                }
            } catch (IOException e) {
                System.err.println("Erroor reading the stockFlower.txt file.");
            }

        } else {
            dlt.errorDlt();
        }

        dlt.confIDlt(enterDlt);
    }

    public void confIDlt(String enterDlt) {
        Scanner scanner = new Scanner(System.in);
        char confItem;

        System.out.print("Confirm Delete?(Y-yes/N-no)");
        confItem = scanner.nextLine().charAt(0);

        switch (confItem) {
            case 'Y', 'y' ->
                dltTxt(enterDlt);
            case 'N', 'n' ->
                Delete();

            default -> {
                System.out.println("Please enter Y or N only!");
                confIDlt(enterDlt);
            }
        }
    }

    public void dltTxt(String enterDlt) {
        try {
            File stockFlower = new File("stockFlower.txt");
            File tempStock = new File("tempStock.txt");

            File stockReport = new File("stockReport.txt");
            File tempReport = new File("tempReport.txt");

            BufferedReader readerStock = new BufferedReader(new FileReader(stockFlower));
            FileWriter writerStock = new FileWriter(tempStock, false);

            BufferedReader readerReport = new BufferedReader(new FileReader(stockReport));
            FileWriter writerReport = new FileWriter(tempReport, false);

            String currentLine;

            while ((currentLine = readerStock.readLine()) != null) {
                if (currentLine.contains(enterDlt)) {
                    continue;
                }
                writerStock.write(currentLine + System.lineSeparator());
            }

            while ((currentLine = readerReport.readLine()) != null) {
                if (currentLine.contains(enterDlt)) {
                    continue;
                }
                writerReport.write(currentLine + System.lineSeparator());
            }

            writerStock.close();
            readerStock.close();

            writerReport.close();
            readerReport.close();

            System.out.println("Stock info deleted successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        copyTxt();
    }

    public void conDlt() {
        Scanner scanner = new Scanner(System.in);
        char conAdd;

        System.out.print("Continue delete? (Y-yes/N-back) :  ");
        conAdd = scanner.nextLine().charAt(0);

        switch (conAdd) {
            case 'Y', 'y' ->
                Delete();
            case 'N', 'n' ->
                Stock.stock();

            default -> {
                System.out.println("Please enter Y or N only!\n");
                conDlt();
            }
        }

    }

    public void errorDlt() {
        System.out.println("invalid input. Please enter again.\n");
        Delete();

    }

    //copy temp to ori file
    public static void copyTxt() {
        DeleteStock d = new DeleteStock();

        String tempStock = "tempStock.txt";
        String stockFlower = "stockFlower.txt";

        String tempReport = "tempReport.txt";
        String stockReport = "stockReport.txt";

        try {
            // Copy from tempStock to stockFlower
            FileReader readerStock = new FileReader(tempStock);
            FileWriter writerStock = new FileWriter(stockFlower);

            int character;

            while ((character = readerStock.read()) != -1) {
                writerStock.write(character);
            }

            readerStock.close();
            writerStock.close();

            // Copy from tempReport to stockReport
            FileReader readerReport = new FileReader(tempReport);
            FileWriter writerReport = new FileWriter(stockReport);

            while ((character = readerReport.read()) != -1) {
                writerReport.write(character);
            }

            readerReport.close();
            writerReport.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        d.conDlt();
    }

}
