

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;
/**
 *
 * @author qiaorou
 */
public class Transaction {
    private static int receiptNo = 1001;
    private int receiptOrder;
    private String code;
    private String flower;
    private int quantity = 0;
    private double price;
    private double subtotal;
    private double total = 0.00;
    private double discount = 0.00;
    private double payment;
    private double balance = 0.00;
    private double amount;
    
    public Transaction(String flower, double price) {
        this.flower = flower;
        this.price = price;
    }
    
    public int generateReceipt() {
        return this.receiptOrder;
    }

    public Transaction() {
        this.receiptOrder = receiptNo;
        receiptNo++;
        code = "";
        flower = "";
    }
    

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
    public void setFlower(String flower) {
        this.flower = flower;
    }

    public String getFlower() {
        return flower;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal() {
        subtotal = price*quantity;
        return subtotal;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount = total*discount;
    }
    
    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getPayment() {
        return payment;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance = payment - amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount = total - discount;
    }
    
    @Override
    public String toString() {
        return String.format("%-10s %-55s %5d %8.2f %8.2f\n",getCode(), getFlower(),
                         getQuantity(), getPrice(), getSubtotal());    
    }
}




