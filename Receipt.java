/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author qiaorou
 */
public class Receipt {

    public static void receipt()  {
        Scanner scanner = new Scanner(System.in);
        List<Transaction> transactions = new ArrayList<>();
        Map<String, Transaction> menuData = new HashMap<>();
        Transaction stransaction = new Transaction();
        String newestSalesNo = null;

        String menuFileName = "stockFlower.txt";

        try (BufferedReader menuReader = new BufferedReader(new FileReader(menuFileName))) {
            String menuLine;
            while ((menuLine = menuReader.readLine()) != null) {
                String[] menuParts = menuLine.split("\\|");
                if (menuParts.length >= 3) {
                    String itemCode = menuParts[0].trim();
                    String flower = menuParts[1].trim();
                    double price = Double.parseDouble(menuParts[2].trim());

                    Transaction menuItem = new Transaction(flower, price);
                    menuData.put(itemCode, menuItem);
                }
            }
        } catch (IOException e) {
        }

        String saleFileName = "receipt.txt";
        double total = 0;
        String salestxt = "sales.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(salestxt))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    newestSalesNo = parts[0].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use the newest salesNo to print the receiptNo
        System.out.println("===========================================================================================");
        System.out.println("Receipt No. : " + newestSalesNo);
        System.out.println("===========================================================================================");

        try (BufferedReader saleReader = new BufferedReader(new FileReader(saleFileName))) {
            String Line;
            while ((Line = saleReader.readLine()) != null) {
                String[] saleParts = Line.split("\\|");
                if (saleParts.length >= 2) {
                    String itemCode = saleParts[0].trim();
                    int quantity = Integer.parseInt(saleParts[1].trim());

                    Transaction transactionItem = menuData.get(itemCode);
                    transactionItem.setCode(itemCode);
                    transactionItem.setQuantity(quantity);

                    System.out.println(transactionItem.toString());

                    total += transactionItem.getSubtotal();

                    transactions.add(transactionItem);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===========================================================================================");
        System.out.printf("                                                                           Total : %.2f\n", total);
        System.out.println("===========================================================================================");
        stransaction.setTotal(total);

        boolean paymentMethod = true;
        do {

            System.out.print("Confirm the order ? (y = Yes / n = No) > ");
            String confirmation = scanner.next();

            if (confirmation.equalsIgnoreCase("n")) {
                clearSalesFile();
                cancel(newestSalesNo);
                mainSales.salesMainMenu();
                break;

            } else if (confirmation.equalsIgnoreCase("y")) {

                System.out.println("Payment methods : ");
                System.out.println("1. Cash ");
                System.out.println("2. E-wallet");
                System.out.print("Enter your choice > ");
                int selectPayment = scanner.nextInt();

                if (selectPayment == 1) {
                    System.out.print("Have membership ? (x-payment)");
                    String member = scanner.next();

                    if (membership(member)) {
                        stransaction.setDiscount(0.0);
                    } else {
                        String ans;
                        String memberId;

                        do {
                        
                            System.out.print("Enter Member ID > ");
                            scanner.nextLine();
                            memberId = scanner.nextLine();

                            if (checkMember(memberId)) {
                                System.out.println("The customer has a membership get 20% discount!");
                                stransaction.setDiscount(0.2);
                                break;
                            } else {
                                System.out.println("Wrong Member ID");
                                System.out.print("Try again? (y = Yes) >");
                                ans = scanner.next();
                            }

                        } while (ans.equalsIgnoreCase("y"));

                    }

                    System.out.print("Enter the payment : RM");
                    double payment = scanner.nextDouble();
                    stransaction.setPayment(payment);

                    while (stransaction.getTotal() > payment) {
                        System.out.println("Payment is less than the total. Please enter payment again.");
                        System.out.print("Enter the payment : RM");
                        payment = scanner.nextDouble();
                        stransaction.setPayment(payment);
                    }

                    System.out.println("Payment Successfully");

                    System.out.printf("%50s\n", "HEYYO FLOWER STORE");
                    System.out.printf("%45s\n", "Receipt");
                    System.out.println("===========================================================================================");
                    System.out.println("Receipt No. : " + newestSalesNo);
                    System.out.println("===========================================================================================");
                    System.out.printf("%-10s %-50s %10s %7s %10s\n", "Code No.", "Flower", "Quantity", "Price", "Subtotal");
                    System.out.println("===========================================================================================");

                    for (Transaction t : transactions) {
                        System.out.printf(t.toString());
                    }

                    System.out.println("===========================================================================================");
                    System.out.printf("                                                                           Total : %.2f\n", stransaction.getTotal());
                    System.out.printf("                                                                        Discount : %.2f\n", stransaction.getDiscount());
                    System.out.println("===========================================================================================");
                    System.out.printf("                                                                          Amount : %.2f\n", stransaction.getAmount());
                    System.out.printf("                                                                         Payment : %.2f\n", stransaction.getPayment());
                    System.out.printf("                                                                          Change : %.2f\n", stransaction.getBalance());
                    System.out.println("===========================================================================================");

                    writeTransactionReport(stransaction);
                    clearSalesFile();
                    paymentMethod = false;

                } else if (selectPayment == 2) {

                    boolean valid = true;
                    String numberPayment;
                    do {
                        try {
                            System.out.print("Enter the Phone Number : ");
                            numberPayment = scanner.nextLine();

                            if (numberPayment.length() != 11 || !numberPayment.matches("\\d{3}-\\d{7}")) {
                                System.out.println("Please enter a valid contact number with the format XXX-XXXXXXX.\n");
                                valid = false;
                            } else {
                                if (ewallet(numberPayment)) {
                                    stransaction.setDiscount(0.2);
                                    System.out.println("This phone number has been registered as a member to enjoy 20% discount");
                                } else {
                                    stransaction.setDiscount(0.0);
                                    System.out.println("Phone number has not been registered as member");
                                }
                                valid = true;
                            }
                        } catch (Exception ex) {
                            System.out.println("An error occurred: " + ex.getMessage());
                            valid = false;
                        }
                    } while (!valid);

                    System.out.println("Please wait a while...");
                    System.out.println("");

                    System.out.printf("%50s\n", "HEYYO FLOWER STORE");
                    System.out.printf("%45s\n", "Receipt");
                    System.out.println("================================================================================================");
                    System.out.println("Receipt No. : " + newestSalesNo);
                    System.out.println("================================================================================================");
                    System.out.printf("%-10s %-55s %10s %8s %9s\n", "Code No.", "Flower", "Quantity", "Price", "Subtotal");
                    System.out.println("================================================================================================");

                    for (Transaction t : transactions) {
                        System.out.printf(t.toString());
                    }

                    System.out.println("================================================================================================");
                    System.out.printf("                                                                            Total : %.2f\n", stransaction.getTotal());
                    System.out.printf("                                                                         Discount : %.2f\n", stransaction.getDiscount());
                    System.out.println("================================================================================================");
                    System.out.printf("                                                                           Amount : %.2f\n", stransaction.getAmount());
                    System.out.printf("                                                                 E-wallet Payment : %.2f\n", stransaction.getAmount());
                    System.out.println("================================================================================================");

                    writeTransactionReport(stransaction);
                    clearSalesFile();
                    paymentMethod = false;

                } else {
                    System.out.println("Please select '1' or '2' only! ");
                }

            } else {
                System.out.println("Invalid input. Please enter 'y' for Yes or 'n' for No.");
            }

        } while (paymentMethod);

    }

    public static boolean membership(String member) {
        boolean membership = false;

        if (member.charAt(0) == 'x' || member.charAt(0) == 'X') {
            membership = true;
        }

        return membership;
    }

    public static boolean checkMember(String memberId) {
        boolean memberExist = false;
        try (BufferedReader memberRead = new BufferedReader(new FileReader("Member.txt"))) {
            String line;
            while ((line = memberRead.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String memberID = parts[0];
                    String username = parts[1];
                    if (memberID.equals(memberId)) {
                        memberExist = true;
                        System.out.println("Username: " + username);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        return memberExist;
    }

    public static boolean ewallet(String numberPayment) {
        String memNo = "Member.txt";
        boolean phoneExist = false;
        try (BufferedReader phoneRead = new BufferedReader(new FileReader(memNo))) {
            String line;
            while ((line = phoneRead.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String phoneNo = parts[2].trim();
                    String username = parts[1].trim(); 

                    if (phoneNo.equals(numberPayment)) {
                        phoneExist = true;
                        System.out.println("Username: " + username);
                        break; 
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        return phoneExist;
    }

    private static void writeTransactionReport(Transaction stransaction) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String time = timeFormat.format(new Date());

            String transactionReport = "TransactionReport.txt";
            FileWriter fileWriter = new FileWriter(transactionReport, true);

            try (BufferedWriter Writer = new BufferedWriter(fileWriter)) {
                Writer.write(date);
                Writer.write("|");
                Writer.write(time);
                Writer.write("|");
                Writer.write(Double.toString(stransaction.getAmount()));
                Writer.write("|");
                Writer.newLine();
                System.out.println("Transaction data has been written to " + transactionReport);
            } catch (IOException e) {
            }

            fileWriter.close();
        } catch (IOException e) {
        }
    }

    private static void clearSalesFile() {
        try {
            String empty = "empty.txt";
            String sales = "receipt.txt";

            BufferedReader reader = new BufferedReader(new FileReader(empty));
            BufferedWriter writer = new BufferedWriter(new FileWriter(sales));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("###")) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            reader.close();
            writer.close();
            System.out.println("Order cleared.");
        } catch (IOException e) {
        }
    }

    public static void cancel(String newestSalesNo)   {
        String salestxt = "sales.txt";
        String stockFile = "stockFlower.txt";

        List<String> salesEntries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(salestxt))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Check if the line contains the desired sales order
                if (line.startsWith(newestSalesNo + "|")) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        String itemCode = parts[1].trim();
                        int quantity = Integer.parseInt(parts[2].trim());

                        // Add back the canceled stock to the stock file
                        updateStock(stockFile,itemCode, quantity);
                    }
                    continue;
                }

                // Add other sales entries to the list
                salesEntries.add(line);
            }
        }catch(FileNotFoundException e){
            
        }
        catch(IOException e){
            
        }

        // Write the updated sales entries back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(salestxt))) {
            for (String salesEntry : salesEntries) {
                bw.write(salesEntry);
                bw.newLine();
            }
        }catch(IOException e){
            
        }

        System.out.println("Order canceled.");
    }

    private static void updateStock(String stockFile, String itemCode, int quantityToAdd) throws IOException {
        List<String> stockLines = new ArrayList<>();
        List<String> stockReportLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(stockFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Check if the line contains the item code
                if (line.startsWith(itemCode + "|")) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        String flower = parts[1].trim();
                        double price = Double.parseDouble(parts[2].trim());
                        int currentQuantity = Integer.parseInt(parts[3].trim());
                        int minQuantity = Integer.parseInt(parts[4].trim());
                        int reorderQuantity = Integer.parseInt(parts[5].trim());

                        // Add back the canceled quantity to the current stock
                        currentQuantity += quantityToAdd;

                        // Update the stock line
                        line = itemCode + "|" + flower + "|" + price + "|" + currentQuantity + "|" + minQuantity + "|" + reorderQuantity + "|";
                    }
                }
                stockLines.add(line);
            }
        }

        // Write the updated stockFlower back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(stockFile))) {
            for (String stockLine : stockLines) {
                bw.write(stockLine);
                bw.newLine();
            }
        }

        String stockReportFile = "stockReport.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(stockReportFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Check if the line contains the item code
                if (line.startsWith(itemCode + "|")) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4) {
                        String flower = parts[1].trim();
                        int currentQuantity = Integer.parseInt(parts[2].trim());
                        int ttlAdd = Integer.parseInt(parts[3].trim());
                        
                        // Add back the canceled quantity to the current stock
                        currentQuantity += quantityToAdd;

                        // Update the stock report line with the new quantity
                        line = itemCode + "|" + flower + "|" + currentQuantity + "|" + ttlAdd + "|";
                    }
                }
                stockReportLines.add(line);
            }
        }

        // Write the updated stockReport back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(stockReportFile))) {
            for (String stockReportLine : stockReportLines) {
                bw.write(stockReportLine);
                bw.newLine();
            }
        }
    }

}