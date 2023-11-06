/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

/**
 *
 * @author ASUS
 */
public class StockObj {

    private String code;
    private String flower;
    private double price;
    private int quantity = 0;
    private int minQty = 0;
    private int reorderQty = 0;

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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setminQty(int minQty) {
        this.minQty = minQty;
    }

    public int getminQty() {
        return minQty;
    }

    public void setreorderQty(int reorderQty) {
        this.reorderQty = reorderQty;
    }

    public int getreorderQty() {
        return reorderQty;
    }

    public String toString() {
        return String.format("| %-10s | %-30s | %-8s | %-10s | %-10s | %-10s |\n", getCode(), getFlower(), getPrice(), getQuantity(), getminQty(), getreorderQty());
    }
}



