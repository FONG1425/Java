/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;
import java.util.Scanner;

/**
 *
 * @author User
 */

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StaffFunc staff = new StaffFunc();
        MemFunc member = new MemFunc();

        staff.staffLogin();
    }
}