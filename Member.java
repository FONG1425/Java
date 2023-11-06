/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.util.ArrayList;

/**
 *
 * @author WH
 */
public class Member {
    private String memId;
    private String memName;
    private String mempw;
    private String memPhoneNo;
    private String memIc;
    private String memEmail;
    private ArrayList<Member> printmember;
    private int numberOfMember = 0;
    
    public Member(){}
    
    public Member(String memId, String memName, String memPhoneNo, String memIc, String memEmail, String mempw) {
        this.memId = memId;
        this.memName = memName;
        this.mempw = mempw;
        this.memEmail = memEmail;
        this.memPhoneNo = memPhoneNo;
        this.memIc = memIc;
        
    }

    public String getMemId() {
        return memId;
    }

    public String getMemName() {
        return memName;
    }

    public String getMempw() {
        return mempw;
    }

    public String getMemPhoneNo() {
        return memPhoneNo;
    }

    public String getMemIc() {
        return memIc;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public ArrayList<Member> getPrintmember() {
        return printmember;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public void setMempw(String mempw) {
        this.mempw = mempw;
    }

    public void setMemPhoneNo(String memPhoneNo) {
        this.memPhoneNo = memPhoneNo;
    }

    public void setMemIc(String memIc) {
        this.memIc = memIc;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public void setPrintmember(ArrayList<Member> printmember) {
        this.printmember = printmember;
    }
    
    public void displayMember() {
	for (int i = 0; i < printmember.size(); i++) {
            System.out.println(printmember.get(i));
	}   
    }
    
     public ArrayList<Member> returnMember(){
        return printmember;
    }
     
    public void passArrayList(ArrayList<Member> storemember){
        this.printmember = storemember;
    }
    public void incrementOfNumberOfMember(){
        numberOfMember++;
    }

    public String idGenerator(){ 
        if(numberOfMember == 0) {
            this.memId = "M0001" ;
        } else {
            String lastMemberId = printmember.get(numberOfMember - 1).getMemId();
            String[] separate = lastMemberId.split("M");
            int IdDigit = Integer.parseInt(separate[1]);
            this.memId = String.format("M%04d", ++IdDigit);
        }
        return memId;
    }
   

    @Override
       public String toString() {
        return "\n-------------------------" +
               "\nStaff ID : " + memId + 
               "\nStaff Name :" + memName + 
               "\nStaff Password :" + mempw + 
               "\nStaff Email :" + memEmail +
               "\nStaff Ic Number : " + memIc +
               "\nStaff PhoneNo :" + memPhoneNo;
    }
}

   