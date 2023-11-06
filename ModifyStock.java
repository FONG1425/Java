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
public class ModifyStock {

    public static void ModifyS() {

        System.out.println("[Modify Stock Information]");
        Modify();
    }

    public static void Modify() {
        ModifyStock modi = new ModifyStock();
        Scanner scanner = new Scanner(System.in);
        String enterModify;

        String stockFlower = "stockFlower.txt";

        System.out.print("Enter Item Code(F00/X-back) : ");
        enterModify = scanner.nextLine();

        if (enterModify.equalsIgnoreCase("x")) {
            Stock.stock();
        } else {
        }

        //CHECK IS START WITH 'F' AND IS 3 DIGIT
        if (enterModify.matches("F\\d{2}")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(stockFlower))) {
                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    if (line.contains(enterModify)) {
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
                    modi.errorModify();
                }
            } catch (IOException e) {
                System.err.println("Erroor reading the stockFlower.txt file.");
            }

        } else {
            modi.errorModify();
        }

        modi.modiOpt(enterModify);
    }

    public void modiOpt(String enterModify) {
        Scanner scanner = new Scanner(System.in);
        char modiopt;

        System.out.println("--------------------");
        System.out.println("| 1. Flower Name   |");
        System.out.println("| 2. Price         |");
        System.out.println("| 3. Quantity      |");
        System.out.println("| 4. Back          |");
        System.out.println("--------------------");

        System.out.print("Select an option : ");

        modiopt = scanner.nextLine().charAt(0);

        switch (modiopt) {
            case '1' ->
                modiFN(enterModify);
            case '2' ->
                modiPrice(enterModify);
            case '3' ->
                modiQty(enterModify);
            case '4' ->
                Modify();

            default -> {
                System.out.println("Invalid input! Please enter again.");
                modiOpt(enterModify);
            }
        }

    }

    public void modiFN(String enterModify) {
        Scanner scanner = new Scanner(System.in);
        String newName;
        char yesmodi;

        System.out.print("New flower name: ");
        newName = scanner.nextLine();

        // Check if the user enters Y or N only.
        while (true) {
            System.out.print("Confirm modify? (Y-yes/N-no): ");
            String confirmInput = scanner.nextLine();

            if (confirmInput.length() == 1) {
                yesmodi = confirmInput.charAt(0);
                if (yesmodi == 'Y' || yesmodi == 'y' || yesmodi == 'N' || yesmodi == 'n') {
                    break;
                } else {
                    System.out.println("Please enter Y or N only!\n");
                }
            } else {
                System.out.println("Please enter Y or N only!\n");
            }
        }

        switch (yesmodi) {
            case 'Y':
            case 'y':
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

                // Modify stockFlower.txt
                while ((currentLine = readerStock.readLine()) != null) {
                    if (currentLine.contains(enterModify)) {
                        String[] parts = currentLine.split("\\|");
                        if (parts.length == 6) {
                            parts[1] = newName; 
                            currentLine = String.join("|", parts);
                        }
                    }
                    writerStock.write(currentLine + System.lineSeparator());
                }

                // Modify stockReport.txt
                while ((currentLine = readerReport.readLine()) != null) {
                    if (currentLine.contains(enterModify)) {
                        String[] parts = currentLine.split("\\|");
                        if (parts.length == 4) {
                            parts[1] = newName; 
                            currentLine = String.join("|", parts);
                        }
                    }
                    writerReport.write(currentLine + System.lineSeparator());
                }

                writerStock.close();
                readerStock.close();

                writerReport.close();
                readerReport.close();

                System.out.println("Modify successfully. New flower name is " + newName + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            break;

            default:
                Modify();
                break;
        }

        modiTxt();
    }

    public void modiPrice(String enterModify) {
        Scanner scanner = new Scanner(System.in);
        double newPrice = 0.0;
        char yesmodi;

        while (true) {
            System.out.print("New price: ");
            String input = scanner.nextLine();

            //check is the user enter double
            try {
                newPrice = Double.parseDouble(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }

        //check is the user enter Y or N only.
        while (true) {
            System.out.print("Confirm modify? (Y-yes/N-no): ");
            String confirmInput = scanner.nextLine();

            if (confirmInput.length() == 1) {
                yesmodi = confirmInput.charAt(0);
                if (yesmodi == 'Y' || yesmodi == 'y' || yesmodi == 'N' || yesmodi == 'n') {
                    break;
                } else {
                    System.out.println("Please enter Y or N only!\n");
                }
            } else {
                System.out.println("Please enter Y or N only!\n");
            }
        }

        switch (yesmodi) {
            case 'Y':
            case 'y':
                try {
                File stockFlower = new File("stockFlower.txt");
                File tempStock = new File("tempStock.txt");

                BufferedReader reader = new BufferedReader(new FileReader(stockFlower));
                FileWriter writer = new FileWriter(tempStock, false);

                String currentLine;

                // Modify stockFlower.txt
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(enterModify)) {
                        String[] parts = currentLine.split("\\|");
                        if (parts.length == 6) {
                            parts[2] = String.valueOf(newPrice);
                            currentLine = String.join("|", parts);
                        }
                    }
                    writer.write(currentLine + System.lineSeparator());
                }

                writer.close();
                reader.close();

                System.out.println("Modify successfully. New price is " + newPrice + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            break;

            default:
                Modify();
                break;
        }

        modiTxt();
    }

    public void modiQty(String enterModify) {
        Scanner scanner = new Scanner(System.in);
        int newQty = 0;
        char yesmodi;

        while (true) {
            System.out.print("New quantity: ");
            String input = scanner.nextLine();

            try {
                //check is the user enter int
                newQty = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integer only.");
            }
        }

        //check is the user enter Y or N only.
        while (true) {
            System.out.print("Confirm modify? (Y-yes/N-no): ");
            String confirmInput = scanner.nextLine();

            if (confirmInput.length() == 1) {
                yesmodi = confirmInput.charAt(0);
                if (yesmodi == 'Y' || yesmodi == 'y' || yesmodi == 'N' || yesmodi == 'n') {
                    break;
                } else {
                    System.out.println("Please enter Y or N only!\n");
                }
            } else {
                System.out.println("Please enter Y or N only!\n");
            }
        }

        switch (yesmodi) {
            case 'Y':
            case 'y':
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

                // Modify stockFlower.txt
                while ((currentLine = readerStock.readLine()) != null) {
                    if (currentLine.contains(enterModify)) {
                        String[] parts = currentLine.split("\\|");
                        if (parts.length == 6) {
                            parts[3] = String.valueOf(newQty);
                            currentLine = String.join("|", parts);
                        }
                    }
                    writerStock.write(currentLine + System.lineSeparator());
                }

                // Modify stockFlower.txt
                while ((currentLine = readerReport.readLine()) != null) {
                    if (currentLine.contains(enterModify)) {
                        String[] parts = currentLine.split("\\|");
                        if (parts.length == 4) {
                            parts[2] = String.valueOf(newQty);
                            currentLine = String.join("|", parts);
                        }
                    }
                    writerReport.write(currentLine + System.lineSeparator());
                }

                writerStock.close();
                readerStock.close();

                writerReport.close();
                readerReport.close();

                System.out.println("Modify successfully. New quantity is " + newQty + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            break;

            default:
                Modify();
                break;
        }

        modiTxt();
    }

    //copy temp to ori file
    public static void modiTxt() {
        ModifyStock m = new ModifyStock();

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
        m.conModify();
    }

    public void conModify() {
        Scanner scanner = new Scanner(System.in);
        char conModi;

        System.out.print("Continue modify info? (Y-yes/N-back) :  ");
        conModi = scanner.nextLine().charAt(0);

        switch (conModi) {
            case 'Y', 'y' ->
                Modify();
            case 'N', 'n' ->
                Stock.stock();

            default -> {
                System.out.println("Please enter Y or N only!\n");
                conModify();
            }
        }

    }

    public void errorModify() {
        System.out.println("invalid input. Please enter again.\n");
        Modify();

    }

}






