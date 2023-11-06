/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

/**
 *
 * @author new asus
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeleteSalesOrder {

    public static void deleteSalesOrder() throws IOException {
        DeleteSalesOrder deleteSales = new DeleteSalesOrder();
        String salestxt = "sales.txt";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the sales order to delete ('X' to go back) : ");
        String salesOrderToDelete = scanner.nextLine();

        if (salesOrderToDelete.equalsIgnoreCase("x")) {
            mainSales.salesMainMenu();
            return;
        }

        List<String> updatedSalesOrders = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(salestxt))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                // Check if the line contains the sales order
                if (line.startsWith(salesOrderToDelete + "|")) {
                    if (!found) {
                        System.out.println("==================================================");
                        System.out.println("Sales Order " + salesOrderToDelete);
                        System.out.println("==================================================");
                        found = true;
                    }

                    String[] parts = line.split("\\|");
                    String itemCode = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    String remark = parts[3];

                    System.out.println("Item Code : " + itemCode);
                    System.out.println("Quantity  : " + quantity);
                    System.out.println("Remark    : " + remark);
                    System.out.println("--------------------------------------------------");

                    continue;
                }
                updatedSalesOrders.add(line);
            }

            if (!found) {
                System.out.println("Sales order not found.");
            } else {
                char yesno;

                System.out.print("Confirm delete?(y/n):");
                yesno = scanner.nextLine().charAt(0);
                if (yesno == 'Y' || yesno == 'y') {
                    // Write the updated sales orders back to the file
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(salestxt))) {
                        for (String updatedOrder : updatedSalesOrders) {
                            bw.write(updatedOrder);
                            bw.newLine();
                        }
                        System.out.println("Sales order deleted successfully.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Deletion canceled.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteSales.contDelete();
        scanner.close();
    }

    public void contDelete() throws IOException {
        Scanner scanner = new Scanner(System.in);
        char contDct;
        System.out.print("Do you want to continue delete ? (y/n) : ");
        contDct = scanner.nextLine().charAt(0);

        switch (contDct) {
            case 'Y':
            case 'y':
                deleteSalesOrder();
                break;

            case 'N':
            case 'n':
                mainSales.salesMainMenu();
                return;

            default:
                System.out.println("Invalid Input . Please enter again .\n");
                contDelete();

        }

    }

}