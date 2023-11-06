/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.util.Scanner;
/**
 *
 * @author JiinWei
 */
public class Stock{
     public static void stock() {
         StaffFunc staff = new StaffFunc();
        char option;

        System.out.println("=================================================");
        System.out.println("|       WELCOME TO THE HEYYO STOCK SYSTEM       |");
        System.out.println("=================================================");
        System.out.println("| Please enter your option :                    |");
        System.out.println("|  1 - Search stock info                        |");
        System.out.println("|  2 - Add stock info                           |");
        System.out.println("|  3 - Modify stock info                        |");
        System.out.println("|  4 - Delete stock info                        |");
        System.out.println("|  5 - Display stock info                       |");
        System.out.println("|  6 - Stock report                             |");
        System.out.println("|  0 - Back to main page                        |");
        System.out.println("=================================================");

        System.out.print("Select an option:");
        Scanner scanner = new Scanner(System.in);
        option = scanner.nextLine().charAt(0);

        switch (option) {
            case '1' ->
                SearchInfo.Search();

            case '2' ->
                AddStock.Adds();

            case '3' ->
                ModifyStock.ModifyS();

            case '4' ->
                DeleteStock.DeletcS();

            case '5' ->
                DisplayStock.DisplayS();

            case '6' ->
                StockReport.Report();

            case '0' ->
                staff.mainMenu();

            default -> {
                System.out.println("invalid input. Please enter again.\n");
                stock();
            }

        }

    }

}