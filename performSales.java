/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;


/**
 *
 * @author ASUS
 */
public class performSales {

    private int salesOrder ;
    private String receiptNo;
    private String itemAdd;
    private int quantity = 0;
    private String remark;
    
    public int generateSalesOrder() {
        return this.salesOrder;
    }

    public void setItemAdd(String itemAdd) {
        this.itemAdd = itemAdd;
    }

    public String getItemAdd() {
        return itemAdd;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
    return String.format("Sales Order : %-5s | Item Code: %-5s | Quantity: %-5s | Remark: %-5s",
            generateSalesOrder(), getItemAdd(), getQuantity(), getRemark());
}

}

