package assignmentbackup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SalesOrderSearch {

    public static void searchSalesOrder() {
        SalesOrderSearch searchSales = new SalesOrderSearch();
        // Define the file path
        String filePath = "sales.txt";

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the sales order to search for ('X' to go back) : ");
        String searchSalesOrder = scanner.nextLine();

        if (searchSalesOrder.equalsIgnoreCase("x")) {
            mainSales.salesMainMenu();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                // Check if the line contains the desired sales order
                if (line.startsWith(searchSalesOrder + "|")) {
                    if (!found) {
                        System.out.println("==================================================");
                        System.out.println("Sales Order " + searchSalesOrder);
                        System.out.println("==================================================");
                        found = true;
                    }

                    // Split the line into components
                    String[] parts = line.split("\\|");
                    String itemCode = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    String remark = parts[3];

                    System.out.println("Item Code : " + itemCode);
                    System.out.println("Quantity  : " + quantity);
                    System.out.println("Remark    : " + remark);
                    System.out.println("--------------------------------------------------");

                }
            }

            if (!found) {
                System.out.println("Sales order not found.");
                searchSalesOrder();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchSales.contSearch();

        // Close the scanner
        scanner.close();

    }

    public void contSearch() {
        Scanner scanner = new Scanner(System.in);
        char contSch;
        System.out.print("Do you want to continue search ? (y/n) : ");
        contSch = scanner.nextLine().charAt(0);

        switch (contSch) {
            case 'Y':
            case 'y':
                searchSalesOrder();
                break;

            case 'N':
            case 'n':
                mainSales.salesMainMenu();
                return;

            default:
                System.out.println("Invalid Input . Please enter again .\n");
                contSearch();

        }

    }

}


