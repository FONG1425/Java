/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignmentbackup;
import java.util.ArrayList;

public class Staff {
    private String staffId;
    private String staffName;
    private String staffpw;
    private String staffEmail;
    private String staffPhoneNo;
    private int staffPosition;
    private String staffIc;
    private int numberOfStaff = 0;
    private ArrayList<Staff> printstaff = new ArrayList<Staff>();



public Staff(){}

    public Staff(String staffId, String staffName, String staffPhoneNo, String staffIc, String staffEmail, String staffpw, int staffPosition) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffpw = staffpw;
        this.staffEmail = staffEmail;
        this.staffPhoneNo = staffPhoneNo;
        this.staffPosition = staffPosition;
        this.staffIc = staffIc;
        
    }

    public void setStaffPosition(int staffPosition) {
        this.staffPosition = staffPosition;
    }

    public void setStaffIc(String staffIc) {
        this.staffIc = staffIc;
    }

    public void setPrintstaff(ArrayList<Staff> printstaff) {
        this.printstaff = printstaff;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getStaffPhoneNo() {
        return staffPhoneNo;
    }

    public int getStaffPosition() {
        return staffPosition;
    }

    public String getStaffIc() {
        return staffIc;
    }

    public ArrayList<Staff> getPrintstaff() {
        return printstaff;
    }

    public void setStaffPhoneNo(String staffPhoneNo) {
        this.staffPhoneNo = staffPhoneNo;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffpw() {
        return staffpw;
    }

    public void setStaffpw(String staffpw) {
        this.staffpw = staffpw;
    }
        
    public void passArrayList(ArrayList<Staff> storestaff){
        this.printstaff = storestaff;
    }
    
    public void displayStaff() {
	for (int i = 0; i < printstaff.size(); i++) {
            System.out.println(printstaff.get(i));
	}   
    }
    
    public ArrayList<Staff> returnStaff(){
        return printstaff;
    }

    public void incrementOfNumberOfStaff(){
        numberOfStaff++;
    }

    public String idGenerator(){ 
        if(numberOfStaff == 0) {
            this.staffId = "ST001" ;
        } else {
            String lastStaffId = printstaff.get(numberOfStaff - 1).getStaffId();
            String[] separate = lastStaffId.split("ST");
            int IdDigit = Integer.parseInt(separate[1]);
            this.staffId = String.format("ST%03d", ++IdDigit);
        }
        return staffId;
    }

    @Override
    public String toString() {
        return "\n-------------------------" +
               "\nStaff ID : " + staffId + 
               "\nStaff Name :" + staffName + 
               "\nStaff Password :" + staffpw + 
               "\nStaff Email :" + staffEmail +
               "\nStaff Ic Number : " + staffIc +
               "\nStaff PhoneNo :" + staffPhoneNo +
               "\nStaff Position :" + staffPosition;
    }

    
}
