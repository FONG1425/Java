/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import static assignmentbackup.Stock.stock;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class mainSales {

    public static void salesMainMenu() {

        Scanner scanner = new Scanner(System.in);
        menu mainMenu = new menu();
        mainMenu.main_Menu();
        boolean isValidChoice = true;
        int choice = 0;

        do {
            try {
                System.out.print("Enter your choice : ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 7) {
                    isValidChoice = true;
                } else {
                    System.out.println("Invalid Choice. Please enter a valid choice");
                    isValidChoice = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Choice. Please enter a valid choice");
                isValidChoice = false;
            }
            switch (choice) {
                case 1:
                    addSales add = new addSales();
                    add.addSales_1();
                    break;
                case 2:
                    SalesOrderSearch.searchSalesOrder();
                    break;
                case 3:
                    DeleteSalesOrder.deleteSalesOrder();
                    break;
                case 4:
                    reportGenerate.printSalesReport();
                    break;
                case 0:
                    StaffFunc staff = new StaffFunc();
                    staff.mainMenu();
                    break;
                }

            }
            while (!isValidChoice);

        }

    public static void exitProgram() {
        System.out.println("Exiting the program. Goodbye!");
        System.exit(0); // Terminate the program with a status code of 0 (indicating successful termination).
    }

}
