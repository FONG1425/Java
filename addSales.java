/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class addSales {

    String menu = "stockFlower.txt";
    private static int lastSalesOrderNumber = 0;

    static {
        // Read the last stored sales order number from the sales.txt file
        try (BufferedReader br = new BufferedReader(new FileReader("sales.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.matches("S\\d{3}\\|.*")) {
                    String[] parts = line.split("\\|");
                    int orderNumber = Integer.parseInt(parts[0].substring(1));
                    if (orderNumber >= lastSalesOrderNumber) {
                        lastSalesOrderNumber = orderNumber + 1; // Increment the last stored sales order number
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addSales_1() {
        menu display = new menu();
        display.flowerMenu();
        Scanner scanner = new Scanner(System.in);
        String itemAdd;
        int leftQty;
        int qty = 0;

        while (true) {

            // Determine the last sales order number from the existing sales.txt file
            int lastSalesOrderNumberFromFile = getLastSalesOrderNumberFromFile();
            int newSalesOrderNumber = lastSalesOrderNumberFromFile + 1;

            String salesOrderNumber = String.format("S%03d", newSalesOrderNumber);
            System.out.println("Sales Order: " + salesOrderNumber);

            List<performSales> addOrders = new ArrayList<>();

            while (true) {
                performSales sales = new performSales();

                while (true) {
                    System.out.print("Enter Item Code ('X' to go back to the main menu & cancel order): ");
                    itemAdd = scanner.nextLine();
                    if (itemAdd.equalsIgnoreCase("x")) {
                        new mainSales().salesMainMenu();
                        return;
                    } else {

                        if (isValidItemCode(itemAdd)) {
                            sales.setItemAdd(itemAdd);
                            System.out.println("You have selected " + sales.getItemAdd());
                            break;
                        } else {
                            System.out.println("Please enter a valid Item Code ");
                        }
                    }
                }

                while (true) {
                    System.out.print("Enter Quantity of " + sales.getItemAdd() + " : ");

                    if (scanner.hasNextInt()) {
                        qty = scanner.nextInt();
                        scanner.nextLine();

                        int quantity = 0; // Initialize quantity outside the loop

                        try (BufferedReader reader = new BufferedReader(new FileReader(menu))) {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split("\\|");
                                if (parts.length == 6 && parts[0].equals(sales.getItemAdd())) {
                                    quantity = Integer.parseInt(parts[3].trim());
                                    break; // Stop reading the file once we find the item
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        leftQty = quantity - qty;

                        if (leftQty >= 30) {
                            if (isValidQuantity(qty)) {
                                sales.setQuantity(qty);
                                System.out.println("You have entered " + sales.getQuantity());
                                break;
                            } else {
                                System.out.println("Invalid Quantity");
                            }
                        } else {
                            System.out.println("Not enough stock. Current stock: " + quantity);
                            addSales_1();
                        }
                    } else {
                        scanner.nextLine();
                        System.out.println("Invalid input. Please enter a valid quantity.");
                    }
                }

                System.out.print("Remark : ");
                String remarkInput = scanner.nextLine();
                sales.setRemark(remarkInput);

                addOrders.add(sales);

                String ans;

                while (true) {
                    addStock(itemAdd, qty);
                    System.out.print("Add Another Order? (y/n) > ");
                    ans = scanner.next();
                    scanner.nextLine();

                    if (ans.charAt(0) != 'y' && ans.charAt(0) != 'n' && ans.charAt(0) != 'Y' && ans.charAt(0) != 'N') {
                        System.out.println("Please enter 'y' or 'n' only!");
                    } else {
                        break;
                    }
                }

                if (ans.charAt(0) == 'n' || ans.charAt(0) == 'N') {
                    break;
                } else {
                    System.out.println("Next Order Details");
                }

            }

            for (performSales order : addOrders) {
                System.out.println("\n============================");
                System.out.println("Sales Order Confirm ");
                System.out.println("============================");
                System.out.println("Sales Order : " + salesOrderNumber);
                System.out.println("Item Code : " + order.getItemAdd());
                System.out.println("Quantity : " + order.getQuantity());
                System.out.println("Remark : " + order.getRemark());
                System.out.println("============================");
                System.out.println();
            }

            writeToReceipt(addOrders);
            writeToTextFile(addOrders, salesOrderNumber);

            System.out.println("Press enter to go to payment ...");
            scanner.nextLine();
            Receipt.receipt();
        }
    }

    public static boolean isValidItemCode(String itemCode) {
        boolean isValidItemCode = true;

        if (itemCode.length() == 0) {
            System.out.println("Invalid Item Code, The Item Code can't be empty ! ");
            isValidItemCode = false;

        } else if (itemCode.length() != 3) {
            System.out.println("Invalid Item Code Length, Please enter again!");
            isValidItemCode = false;

        } else if (itemCode.charAt(0) != 'F') {
            System.out.println("Invalid Item Code , The first Letter must be 'F' ");
            isValidItemCode = false;

        } else {
            int itemNumber = Integer.parseInt(itemCode.substring(1));
            if (itemNumber < 1 || itemNumber > 30) {
                System.out.println("Invalid Item Code, The Item Code must be F01 - F30");
                isValidItemCode = false;

            } else {
                return isValidItemCode;
            }
        }

        return isValidItemCode;
    }

    private static boolean isValidQuantity(int qty) {
        return qty >= 1 && qty <= 500;
    }

    void writeToTextFile(List<performSales> addOrders, String salesOrderNumber) {
        try (FileWriter fileWriter = new FileWriter("sales.txt", true); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (performSales s : addOrders) {
                String formattedLine = String.format("%s|%s|%d|%s|",
                        salesOrderNumber, s.getItemAdd(), s.getQuantity(), s.getRemark());
                bufferedWriter.write(formattedLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeToReceipt(List<performSales> addOrders) {
        try {
            FileWriter fileWriter = new FileWriter("receipt.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (performSales s : addOrders) {
                String formattedLine = String.format("%s|%d|",
                        s.getItemAdd(), s.getQuantity());
                bufferedWriter.write(formattedLine);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salesMainMenu() throws IOException {
        mainSales.salesMainMenu();
    }

    // Helper method to determine the last sales order number from the existing sales.txt file
    private int getLastSalesOrderNumberFromFile() {
        int lastOrderNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("sales.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.matches("S\\d{3}\\|.*")) {
                    String[] parts = line.split("\\|");
                    int orderNumber = Integer.parseInt(parts[0].substring(1));
                    if (orderNumber > lastOrderNumber) {
                        lastOrderNumber = orderNumber;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastOrderNumber;
    }

    //update newest qty to stockFlowe.txt & stockReport.txt
    public static void addStock(String itemAdd, int qty) {
        String tempStock = "tempStock.txt";
        String stockFlower = "stockFlower.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(stockFlower));
            FileWriter writer = new FileWriter(tempStock, false);

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(itemAdd)) {
                    String[] parts = currentLine.split("\\|");
                    if (parts.length == 6) {
                        int quantity = Integer.parseInt(parts[3].trim());
                        quantity = quantity - qty;

                        parts[3] = String.valueOf(quantity);

                        String updatedLine = String.join("|", parts);

                        writer.write(updatedLine + System.lineSeparator()); // Ensure you add the delimiter and a new line
                    }
                } else {
                    writer.write(currentLine + System.lineSeparator()); // Ensure you add a new line
                }
            }

            writer.close();
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();

        }

        String tempReport = "tempReport.txt";
        String stockReport = "stockReport.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(stockReport));
            FileWriter writer = new FileWriter(tempReport, false);

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(itemAdd)) {
                    String[] parts = currentLine.split("\\|");
                    if (parts.length == 4) {
                        int totalQty = Integer.parseInt(parts[2].trim());
                        totalQty = totalQty - qty;

                        parts[2] = String.valueOf(totalQty);

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

        //copy temp to ori file
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
    }

}