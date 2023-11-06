/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentbackup;

import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author User
 */
public class StaffFunc {

    private static String loginStaffId;

    public void logo(){
       	System.out.println("\t\t\t .----------------. .------------------. .-----------------. .----------------.  .----------------.  ");
	System.out.println("\t\t\t| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |");
	System.out.println("\t\t\t| |  ____  ____  | || |  _________   | || |  ____  ____  | || |  ____  ____  | || |     ____     | |");
	System.out.println("\t\t\t| | |_   ||   _| | || | |_   ___  |  | || | |_  _||_  _| | || | |_  _||_  _| | || |   .'    `.   | |");
	System.out.println("\t\t\t| |   | |__| |   | || |   | |_  \\_|  | || |   \\ \\  / /   | || |   \\ \\  / /   | || |  /  .--.  \\  | |");
	System.out.println("\t\t\t| |   |  __  |   | || |   |  _ | _   | || |    \\ \\/ /    | || |    \\ \\/ /    | || |  | |    | |  | |");
	System.out.println("\t\t\t| |  _| |  | |_  | || |  _| |___/ |  | || |    _|  |_    | || |    _|  |_    | || |  \\  `--'  /  | |");
	System.out.println("\t\t\t| | |____||____| | || | |_________|  | || |   |______|   | || |   |______|   | || |   '.____.'   | |");
	System.out.println("\t\t\t| |              | || |              | || |              | || |              | || |              | |");
	System.out.println("\t\t\t| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |");
	System.out.println("\t\t\t '----------------'  '-----------------' '-----------------' '----------------'  '----------------' ");
    }
   
    
    public void addStaff(String staffId, String staffName, String staffPhoneNo, String staffEmail, String staffPw, int staffPosition, String staffIc) {
        try {
            FileWriter addStaff = new FileWriter("Staff.txt", true);
            addStaff.append(staffId + "|" + staffName + "|" + staffPhoneNo + "|" + staffIc + "|" + staffEmail + "|" + staffPw + "|" + staffPosition + "\n");
            addStaff.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found\n");
        } catch (IOException ex) {
        }
    }

    public void clearStaff() {
        try {
            FileWriter clearStaff = new FileWriter("Staff.txt");
            clearStaff.write("");
            clearStaff.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found\n");
        } catch (IOException ex) {
        }
    }

    public Staff readStaff() {
        ArrayList<Staff> storestaff = new ArrayList<Staff>();
        Staff passstaff = new Staff();
        try {
            FileReader readStaff = new FileReader("Staff.txt");
            BufferedReader es = new BufferedReader(readStaff);
            String existingStaff = es.readLine();
            do {
                StringTokenizer s = new StringTokenizer(existingStaff, "|");
                String staffId = s.nextToken();
                String staffName = s.nextToken();
                String staffPhoneNo = s.nextToken();
                String staffIc = s.nextToken();
                String staffEmail = s.nextToken();
                String staffPw = s.nextToken();
                int staffPosition = Integer.parseInt(s.nextToken());

                Staff staff = new Staff(staffId, staffName, staffPhoneNo, staffIc, staffEmail, staffPw, staffPosition);

                //store staff into arraylist
                storestaff.add(staff);

                //set arraylist into the medium
                passstaff.passArrayList(storestaff);

                //ready for idGenerator
                passstaff.incrementOfNumberOfStaff();

            } while ((existingStaff = es.readLine()) != null);
            readStaff.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println("IOException");
        } catch (NullPointerException ex) {
        }

        //return the object to maintain the data
        return passstaff;
    }

    public void displayStaff() {
        Staff readstaff = readStaff();
        ArrayList<Staff> staffs = readstaff.returnStaff();

        Scanner scanner = new Scanner(System.in);
        int length = 0;

        try {
            length = 0;
            for (int i = 0; i < staffs.size(); i++) {
                if (staffs.get(i).equals(null)) {
                    break;
                }
                length++;
            }
        } catch (NullPointerException ex) {
        }

        if (staffs.get(0).equals(null)) {
            System.err.println("No staff in database");
        } else {
            System.out.println("============================================================================================================================");
            System.out.printf("| %s| %-6s | %-20s | %-14s | %-14s | %-30s | %-15s |\n", "No", "Staff Id", "Name", "Ic", "Phone No", "Email", "Staff Position");
            System.out.println("============================================================================================================================");

            for (int i = 0; i < length; i++) {
                Staff staff = staffs.get(i);
                System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s | %-15d |\n",
                        i + 1, staff.getStaffId(), staff.getStaffName(), staff.getStaffIc(),
                        staff.getStaffPhoneNo(), staff.getStaffEmail(), staff.getStaffPosition());
            }
            System.out.println("============================================================================================================================");
            System.out.println("Note: ");
            System.out.println("Staff position 1 = Staff || 2 = Staff Admin");

            System.out.print("Press enter to continue...");
            scanner.nextLine();
            System.out.println("");
        }

    }

    public void staffLogin() {
        logo();
        Staff checkstaff = readStaff();
        ArrayList<Staff> compare = checkstaff.returnStaff();
        boolean valid = true;
        String staffpw = "";
        
        System.out.println("\n\nStaff Login");
        System.out.println("==================================");
        do {
            System.out.print("Staff Id : ");
            loginStaffId = new Scanner(System.in).next();
            loginStaffId = loginStaffId.toUpperCase();

            if (loginStaffId.length() != 5) {
                System.err.println("Must start with 'ST' and follow by 3 numbers, please enter again...\n");
                valid = false;
            } else if (loginStaffId.isEmpty()) {
                System.err.println("Staff ID should not be empty.\n");
                valid = false;
            } else {
                for (int i = 0; i < compare.size(); i++) {
                    if (loginStaffId.equals(compare.get(i).getStaffId())) {
                        System.out.println(compare.get(i).getStaffId());
                        valid = true;
                        break;
                    } else {
                        valid = false;
                    }
                }

                if (valid == false) {
                    System.err.println("Invalid staff ID.\n");
                }

            }
        } while (valid == false);

        do {
            System.out.print("Staff Password : ");
            staffpw = new Scanner(System.in).next();
            if (staffpw.length() != 8) {
                System.err.println("Password must be 8 characters\n");
                valid = false;
            } else if (staffpw.isEmpty()) {
                System.err.println("Password should not be empty.\n");
                valid = false;
            } else {
                for (int i = 0; i < compare.size(); i++) {
                    if (loginStaffId.equals(compare.get(i).getStaffId())) {
                        if (staffpw.equals(compare.get(i).getStaffpw())) {
                            compare.get(2).getStaffPosition();
                            valid = true;
                            break;
                        } else {
                            valid = false;
                        }
                    }
                }
                if (valid == false) {
                    System.err.println("Password not matched...\n");
                }

            }
        } while (valid == false);

        System.out.println("==================================");

        System.out.println("Hello " + loginStaffId);
        System.out.println("Good luck with your work and have a good day!");

        mainMenu();
    }

    public void mainMenu() {
        int staffvalid = 0;
        int staffPosition = 0;
        Staff checkstaff = readStaff();
        MemFunc member = new MemFunc();
        ArrayList<Staff> staffPo = checkstaff.returnStaff();
        TransacReport transacReport = new TransacReport();

        String staffId = loginStaffId;
        boolean valid = true;
        boolean flag = true;

        for (int i = 0; i < staffPo.size(); i++) {
            if (staffId.equals(staffPo.get(i).getStaffId())) {
                staffPosition = staffPo.get(i).getStaffPosition();
                valid = true;
                break;
            } else {
                valid = false;
            }
        }

        do {
            System.out.println("\n==========================================");
            System.out.println("|  WELCOME TO THE HEYYO FLOWER SHOP      |");
            System.out.println("==========================================");
            System.out.println("| Please enter your selection :          |");
            System.out.println("| 1. Staff                               |");
            System.out.println("| 2. Member                              |");
            System.out.println("| 3. Sales                               |");
            System.out.println("| 4. Stock                               |");
            System.out.println("| 5. Transaction Report                  |");
            System.out.println("| 6. Logout                              |");
            System.out.println("| 0. Exit                                |");
            System.out.println("==========================================");

            do {
                try {
                    System.out.print("Enter your selection : ");
                    staffvalid = new Scanner(System.in).nextInt();

                    switch (staffvalid) {
                        case 1:
                            if (staffPosition == 2) {
                                staffMenu();
                            } else {
                                flag = true;
                                System.err.println("Only staff admin can access this session !!!");
                            }
                            break;

                        case 2:
                            member.memMenu();
                            flag = true;
                            break;

                        case 3:
                            mainSales.salesMainMenu();
                            flag = true;
                            break;

                        case 4:
                            Stock.stock();
                            flag = true;
                            break;

                        case 5:
                            transacReport.transactionReport();
                            flag = true;
                            break;
                        case 6:
                            staffLogin();
                            staffMenu();
                            flag = true;
                            break;

                        case 0:
                            System.exit(0);
                            break;

                        default:
                            System.err.println("Wrong input ! Please choose 0 - 6 !!");
                            flag = true;
                    }
                } catch (Exception e) {
                    // accept integer only.
                    flag = true;
                    System.err.println("Wrong input ! Please only input INTEGER !!\n");
                }
            } while (staffvalid < 0 || staffvalid > 6);
        } while (flag == true);
    }

    public void staffMenu() {
        int choices = 0;
        boolean flag = true;

        do {
            System.out.println("\n\n==========================================");
            System.out.println("|  WELCOME TO THE HEYYO FLOWER SHOP      |");
            System.out.println("==========================================");
            System.out.println("| Please enter your selection :          |");
            System.out.println("| 1. Add Staff                           |");
            System.out.println("| 2. Display Staff                       |");
            System.out.println("| 3. Modify Staff                        |");
            System.out.println("| 4. Delete Staff                        |");
            System.out.println("| 5. Search Staff                        |");
            System.out.println("| 0. Back to Main Menu                   |");
            System.out.println("==========================================");

            do {
                try {
                    System.out.print("Enter your selection : ");
                    choices = new Scanner(System.in).nextInt();

                    switch (choices) {
                        case 1:
                            addStaffFunction();
                            flag = true;
                            break;

                        case 2:
                            displayStaff();
                            flag = true;
                            break;

                        case 3:
                            modifyStaff();
                            flag = true;
                            break;

                        case 4:
                            deleteStaff();
                            flag = true;
                            break;

                        case 5:
                            searchStaff();
                            flag = true;
                            break;

                        case 0:
                            mainMenu();
                            break;

                        default:
                            System.err.println("Wrong input ! Please choose 0 - 5 !!");
                            flag = true;
                    }

                } catch (InputMismatchException e) {
                    // accept integer only.
                    System.err.println("Wrong input ! Please only input INTEGER !!\n");
                    flag = true;
                }
            } while (choices < 0 || choices > 5);
        } while (flag == true);

    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(\\d{3}-\\d{7})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean validateICNumber(String icNumber) {
        // Define the regular expression pattern
        String pattern = "^\\d{6}-\\d{2}-\\d{4}$";

        // Create a Pattern object and match the input against the pattern
        Pattern icPattern = Pattern.compile(pattern);
        Matcher matcher = icPattern.matcher(icNumber);

        // Check if the input matches the pattern and has the correct length
        return matcher.matches() && icNumber.length() == 14;
    }

    public void addStaffFunction() {
        String staffId = "";
        String staffName = "";
        String staffPw = "";
        String staffPhoneNo = "";
        String staffEmail = "";
        int staffPosition = 0;
        String staffIc = "";
        boolean valid = true;
        String regex = "^([a-z]+)@([a-z]+)([\\.]{1})([a-z]+)$";
        char yesno;

        Staff checkStaffIc = readStaff();
        ArrayList<Staff> checkDuplicateIc = checkStaffIc.returnStaff();
        Staff checkPhoneNo = readStaff();
        ArrayList<Staff> checkDuplicatePhoneNo = checkPhoneNo.returnStaff();

        Scanner scanner = new Scanner(System.in);

        Staff generateId = readStaff();
        staffId = generateId.idGenerator();
        System.out.print("Staff ID :" + staffId + "\n");

        do {
            try {
                System.out.print("Enter staff name ( X - Exit ): ");
                staffName = new Scanner(System.in).nextLine();
                if (checkName(staffName) == true) {
                    System.out.println("\nPlease enter a valid name...\n");
                }
                if (staffName.isEmpty()) {
                    System.out.println("Name cannot be empty !\n");
                } else if (staffName.toUpperCase().equals("X")) {
                    staffMenu();
                    break;
                }
            } catch (NullPointerException ex) {
            }
        } while (staffName.isEmpty());

        if (!staffName.toUpperCase().equals("X")) {
            do {
                try {
                    System.out.print("Enter staff phone number : ");
                    staffPhoneNo = new Scanner(System.in).next();

                    if (!isValidPhoneNumber(staffPhoneNo)) {
                        System.out.println("Please enter a valid contact number with '-'..\n");

                        valid = false;
                    } else if (staffPhoneNo.isEmpty()) {
                        System.out.println("Phone number cannot be empty !\n");
                        valid = false;
                    } else {
                        for (int i = 0; i < checkDuplicatePhoneNo.size(); i++) {
                        if (staffPhoneNo.equals(checkDuplicatePhoneNo.get(i).getStaffPhoneNo())) {
                            System.out.println("This phone number [" + staffPhoneNo + "]have been registered before !\n");
                            valid = false;
                            break;
                        } else {
                            valid = true;
                        }
                    }
                    }
                } catch (NullPointerException ex) {
                } catch (StringIndexOutOfBoundsException ex) {

                }
            } while (valid == false);

            do {
                System.out.print("Enter staff email : ");
                staffEmail = new Scanner(System.in).next();
                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(staffEmail);
                if (!matcher.matches()) {
                    System.out.println("\nError: Invalid email format.\n");
                    valid = false;
                } else {
                    valid = true;
                }
            } while (valid == false);

            do {
                System.out.print("Enter password : ");
                staffPw = new Scanner(System.in).next();
                if (staffPw.isEmpty()) {
                    System.out.println("Password cannot be empty !");
                    valid = false;
                } else if (staffPw.length() != 8) {
                    System.out.println("Password should be 8 characters or numbers ! \n");
                    valid = false;
                } else {
                    valid = true;
                }
            } while (valid == false);

            do {
                System.out.print("Enter staff ic number (with '-'): ");
                staffIc = new Scanner(System.in).next();
                if (!validateICNumber(staffIc)) {
                    System.out.println("Ic number should be 14 numbers with '-' ! \n");
                    valid = false;
                } else if (staffIc.isEmpty()) {
                    System.out.println("Ic number cannot be empty !");
                    valid = false;
                } else {
                    for (int i = 0; i < checkDuplicateIc.size(); i++) {
                        if (staffIc.equals(checkDuplicateIc.get(i).getStaffIc())) {
                            System.out.println("This Ic number [" + staffIc + "]have been registered before !\n");
                            valid = false;
                            break;
                        } else {
                            valid = true;
                        }
                    }
                }
            } while (valid == false);

            do {
                System.out.println("==========================================");
                System.out.println("| Please enter the staff position :      |");
                System.out.println("| 1. Staff                               |");
                System.out.println("| 2. Staff Admin                         |");
                System.out.println("==========================================");

                boolean flag;
                do {
                    try {
                        System.out.print("Enter your selection : ");
                        staffPosition = new Scanner(System.in).nextInt();
                        flag = false;
                    } catch (InputMismatchException e) {
                        // accept integer only.
                        System.out.println("Wrong input ! Please only input INTEGER !!\n");
                        flag = true;
                        staffMenu();
                    }
                } while (flag == true);

                if (staffPosition < 1 || staffPosition > 2) {
                    System.out.println("Staff position selected is invalid !");
                }
                //} while (staffPosition != 0);

            } while (staffPosition < 1 || staffPosition > 2);

            addStaff(staffId, staffName, staffPhoneNo, staffEmail, staffPw, staffPosition, staffIc);

            System.out.println("\t\t\t\t -Staff successfully added!!-");
            System.out.println("Do you want to continue add staff? (Y/N)");
            yesno = scanner.next().toUpperCase().charAt(0);
            if (yesno == 'Y') {
                addStaffFunction();
            } else {
                staffMenu();
            }

        }

    }

    public void modifyStaff() {
        Staff checkstaff = readStaff();
        ArrayList<Staff> compare = checkstaff.returnStaff();
        String staffId = "";
        String newName, newPw, newEmail, newPhoneNo;
        String regex = "^([a-z]+)@([a-z]+)([\\.]{1})([a-z]+)$";
        int modifySelection = 0, newStaffPosition = 0;
        boolean valid = true;
        boolean loop = true;
        int count = 0, length = 0;
        char yesno;
        Scanner scanner = new Scanner(System.in);

        displayStaff();

        do {
            System.out.print("Enter Staff Id to Modify ( X - Exit ) : ");
            staffId = new Scanner(System.in).next();
            staffId = staffId.toUpperCase();
            for (int i = 0; i < compare.size(); i++) {
                if (staffId.equals(compare.get(i).getStaffId())) {
                    count = i;
                    valid = true;
                    break;
                } else if (staffId.equals("X")) {
                    valid = true;
                    break;
                } else {
                    valid = false;
                }
            }
            if (valid == false) {
                System.err.println("Invalid staff ID.\n");
            } else if (staffId.equals("X")) {
                break;
            } else {
                do {
                    System.out.println("\n==========================================");
                    System.out.println("| Modification of " + staffId + "                  |");
                    System.out.println("| 1. Staff Name                          |");
                    System.out.println("| 2. Staff Password                      |");
                    System.out.println("| 3. Staff Email                         |");
                    System.out.println("| 4. Staff Phone Number                  |");
                    System.out.println("| 5. Staff Position                      |");
                    System.out.println("| 0. Back to Staff Menu                  |");
                    System.out.println("==========================================");
                    do {
                        try {
                            System.out.printf("Enter your selection : ");
                            modifySelection = new Scanner(System.in).nextInt();
                            switch (modifySelection) {
                                case 1:
                                    do {
                                        loop = true;
                                        System.out.print("Enter staff new name :");
                                        newName = scanner.nextLine();

                                        if (newName.isEmpty()) {
                                            System.err.println("Name cannot be empty !\n");
                                        } else if (newName.toUpperCase().equals("X")) {
                                            valid = false;
                                        } else if (!newName.isEmpty()) {
                                            valid = true;
                                            compare.get(count).setStaffName(newName);
                                            clearStaff();
                                            for (int i = 0; i < compare.size(); i++) {
                                                addStaff(compare.get(i).getStaffId(), compare.get(i).getStaffName(), compare.get(i).getStaffPhoneNo(), compare.get(i).getStaffEmail(), compare.get(i).getStaffpw(), compare.get(i).getStaffPosition(), compare.get(i).getStaffIc());
                                            }
                                        }
                                    } while (checkName(newName) == true);

                                    System.out.println("\n\t\t Note : Staff name successfully modified!");
                                    break;

                                case 2:
                                    do {
                                        loop = true;
                                        System.out.print("Enter staff new password :");
                                        newPw = scanner.next();

                                        if (newPw.isEmpty()) {
                                            System.err.println("Password cannot be empty !");
                                            valid = false;
                                        } else if (newPw.length() != 8) {
                                            System.err.println("Password should be 8 characters or numbers ! \n");
                                            valid = false;
                                        } else {
                                            valid = true;
                                            compare.get(count).setStaffpw(newPw);
                                            System.out.println(compare.get(count).getStaffpw());
                                            clearStaff();
                                            for (int i = 0; i < compare.size(); i++) {
                                                addStaff(compare.get(i).getStaffId(), compare.get(i).getStaffName(), compare.get(i).getStaffPhoneNo(), compare.get(i).getStaffEmail(), compare.get(i).getStaffpw(), compare.get(i).getStaffPosition(), compare.get(i).getStaffIc());
                                            }
                                        }
                                    } while (valid == false);

                                    System.out.println("\n\t\t Note : Staff password successfully modified!");
                                    break;

                                case 3:
                                    do {
                                        loop = true;
                                        System.out.print("Enter staff new email : ");
                                        newEmail = new Scanner(System.in).next();
                                        Pattern pattern = Pattern.compile(regex);

                                        Matcher matcher = pattern.matcher(newEmail);
                                        if (!matcher.matches()) {
                                            System.err.println("\nError: Invalid email format.\n");
                                            valid = false;
                                        } else {
                                            valid = true;//setStaffEmail
                                            compare.get(count).setStaffEmail(newEmail);
                                            System.out.println(compare.get(count).getStaffEmail());
                                            clearStaff();
                                            for (int i = 0; i < compare.size(); i++) {
                                                addStaff(compare.get(i).getStaffId(), compare.get(i).getStaffName(), compare.get(i).getStaffPhoneNo(), compare.get(i).getStaffEmail(), compare.get(i).getStaffpw(), compare.get(i).getStaffPosition(), compare.get(i).getStaffIc());
                                            }
                                        }
                                    } while (valid == false);

                                    System.out.println("\n\t\t Note : Staff email successfully modified!\n");
                                    break;

                                case 4:
                                    do {
                                        loop = true;
                                        try {
                                            System.out.print("Enter staff new phone number : ");
                                            newPhoneNo = new Scanner(System.in).next();

                                            if (newPhoneNo.charAt(3) != '-' || newPhoneNo.length() > 11 || newPhoneNo.length() < 11) {
                                                System.err.println("Please enter a valid contact number with '-'..\n");

                                                valid = false;
                                            } else if (newPhoneNo.isEmpty()) {
                                                System.err.println("Phone number cannot be empty !\n");
                                                valid = false;
                                            } else {
                                                valid = true;//setStaffPhoneNo
                                                compare.get(count).setStaffPhoneNo(newPhoneNo);
                                                System.out.println(compare.get(count).getStaffPhoneNo());
                                                clearStaff();
                                                for (int i = 0; i < compare.size(); i++) {
                                                    addStaff(compare.get(i).getStaffId(), compare.get(i).getStaffName(), compare.get(i).getStaffPhoneNo(), compare.get(i).getStaffEmail(), compare.get(i).getStaffpw(), compare.get(i).getStaffPosition(), compare.get(i).getStaffIc());
                                                }
                                            }
                                        } catch (NullPointerException ex) {
                                        }
                                    } while (valid == false);

                                    System.out.println("\n\t\t Note : Staff phone number successfully modified!\n");
                                    break;

                                case 5:
                                    loop = true;
                                    do {
                                        System.out.println("==========================================");
                                        System.out.println("| Please enter the staff position :      |");
                                        System.out.println("| 1. Staff                               |");
                                        System.out.println("| 2. Staff Admin                         |");
                                        System.out.println("==========================================");

                                        boolean flag;
                                        do {
                                            try {
                                                System.out.print("Enter your selection : ");
                                                newStaffPosition = new Scanner(System.in).nextInt();

                                                flag = false;
                                            } catch (InputMismatchException e) {
                                                // accept integer only.
                                                System.err.println("Wrong input ! Please only input INTEGER !!\n");
                                                flag = true;
                                            }
                                        } while (flag == true);

                                        if (newStaffPosition < 1 || newStaffPosition > 2) {
                                            System.err.println("Staff position selected is invalid !\n");
                                        }
                                        compare.get(count).setStaffPosition(newStaffPosition);
                                        clearStaff();
                                        for (int i = 0; i < compare.size(); i++) {
                                            addStaff(compare.get(i).getStaffId(), compare.get(i).getStaffName(), compare.get(i).getStaffPhoneNo(), compare.get(i).getStaffEmail(), compare.get(i).getStaffpw(), compare.get(i).getStaffPosition(), compare.get(i).getStaffIc());
                                        }

                                    } while (newStaffPosition < 1 || newStaffPosition > 2);
                                    break;
                                case 0:
                                    staffMenu();
                                    break;

                                default:
                                    System.err.println("Invalid seletion! Please enter again...\n");
                                    loop = true;
                            }

                        } catch (InputMismatchException e) {
                            // accept integer only.
                            System.err.println("Wrong input ! Please only input INTEGER !!\n");
                            valid = false;
                        }
                    } while (modifySelection < 0 || modifySelection > 5);

                } while (loop == true);
            }
            valid = true;
        } while (valid == true);

        do {
            System.out.print("Do you want to continue modify other staff ? (Y/N)");
            yesno = scanner.next().toUpperCase().charAt(0);
            if (yesno == 'Y') {
                valid = true;
                modifyStaff();
            } else if (yesno == 'N') {
                valid = true;
                staffMenu();
            } else {
                System.err.println("Invalid input ! Please enter y or n !!\n");
                valid = false;
            }
        } while (valid == false);
    }

    public boolean checkName(String staffName) {
        int letter = 0, space = 0;
        boolean check = true;
        boolean ltr = false;
        boolean digit = true;
        int len = staffName.length();

        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(staffName.charAt(i))) {
                space++;

            }

            if (Character.isDigit(staffName.charAt(i))) {
                digit = false;
            }

            if (Character.isLetter(staffName.charAt(i))) {
                letter++;
                ltr = true;
            }
        }

        if (digit) {
            if (ltr) {
                if ((space + letter) == len) {
                    check = false;

                }
            }
        }
        return check;
    }

    public void deleteStaff() {

        String deleteId;
        Scanner scanner = new Scanner(System.in);
        Staff checkstaff = readStaff();
        ArrayList<Staff> compare = checkstaff.returnStaff();
        char confirm;
        boolean valid = true;

        displayStaff();

        do {
            System.out.print("Enter staff Id to delete ( X - Exit ) : ");
            deleteId = scanner.next().toUpperCase();
            for (int i = 0; i < compare.size(); i++) {
                if (deleteId.equals(compare.get(i).getStaffId())) {
                    valid = true;
                    break;
                } else if (deleteId.equals("X")) {
                    valid = true;
                    break;
                } else {
                    valid = false;
                }
            }
            if (valid == false) {
                System.err.println("Invalid staff ID !");
            }
        } while (valid == false);

        if (valid == true && !deleteId.equals("X")) {
            do {
                System.out.print("Are you confirm to delete this staff ? [Y - Yes / N - No] :");
                confirm = scanner.next().toUpperCase().charAt(0);
                scanner.nextLine();
                if (confirm == 'Y') {
                    clearStaff();
                    for (int i = 0; i < compare.size(); i++) {
                        if (deleteId.equals(compare.get(i).getStaffId())) {
                            continue;
                        } else {
                            addStaff(compare.get(i).getStaffId(), compare.get(i).getStaffName(), compare.get(i).getStaffPhoneNo(), compare.get(i).getStaffEmail(), compare.get(i).getStaffpw(), compare.get(i).getStaffPosition(), compare.get(i).getStaffIc());
                        }
                    }

                    System.out.printf("\n      -Information of staff %s is deleted!-\n", deleteId);
                } else if (confirm == 'N') {
                    System.err.println("\n     -Staff is not deleted!-\n");
                } else {
                    System.err.println("Please enter a 'Y' or 'N' only...\n");
                }
            } while (confirm != 'Y' && confirm != 'N');
        }

        do {
            System.out.print("Do you want to delete other staff  (Y/N)? ");
            confirm = scanner.next().toUpperCase().charAt(0);
            if (confirm == 'Y') {
                deleteStaff();
                valid = true;
            } else if (confirm == 'N') {
                staffMenu();
                valid = true;
            } else {
                System.err.println("Please enter a 'Y' or 'N' only...\n");
                valid = false;
            }
        } while (valid == false);
    }

    public void searchStaff() {
        int searchstaffchoice = 0;
        String searchId, searchName, searchPhoneNo;
        Scanner scanner = new Scanner(System.in);
        Staff checkstaff = readStaff();
        ArrayList<Staff> compare = checkstaff.returnStaff();

        ArrayList<Staff> staffs = checkstaff.returnStaff();
        boolean valid = true;

        do {
            System.out.println("\n\t\t  Search Staff");
            System.out.println("\t\t================");
            System.out.println("1. Staff Id");
            System.out.println("2. Staff Name");
            System.out.println("3. Staff Phone Number");
            System.out.println("0. Back to staff menu");

            do {
                try {
                    System.out.print("Enter to search staff by : ");
                    searchstaffchoice = new Scanner(System.in).nextInt();
                    switch (searchstaffchoice) {
                        case 1:
                             valid = false;
                            do {
                                System.out.print("Enter staff Id to search ( X - Exit ) :");
                                searchId = scanner.next();
                                searchId = searchId.toUpperCase();
                                for (int i = 0; i < compare.size(); i++) {
                                    if (searchId.equals(compare.get(i).getStaffId())) {
                                        Staff staff = staffs.get(i);
                                        System.out.println("\nStaff founded");
                                        System.out.println("============================================================================================================================");
                                        System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s | %-15d |\n", i + 1, staff.getStaffId(), staff.getStaffName(), staff.getStaffIc(),
                                                staff.getStaffPhoneNo(), staff.getStaffEmail(), staff.getStaffPosition());
                                        System.out.println("============================================================================================================================");
                                        System.out.println("Note: ");
                                        System.out.println("Staff position 1 = Staff || 2 = Staff Admin");
                                        valid = true;

                                    } else if (searchId.equals("X")) {
                                        valid = true;
                                        break;
                                    } else {
                                        continue;
                                    }
                                }
                                if (valid == false) {
                                    System.err.println("Invalid staff ID.\n");
                                }
                            } while (valid == false);
                            System.out.print("Press enter to continue...");
                            scanner.nextLine();
                            System.out.println("");
                            break;

                        case 2:
                            valid = false;
                            do {
                                System.out.print("Enter staff name to search ( X - Exit ) :");
                                searchName = scanner.nextLine();
                                for (int i = 0; i < compare.size(); i++) {
                                    if (searchName.equals(compare.get(i).getStaffName())) {
                                        Staff staff = staffs.get(i);
                                        System.out.println("\nStaff related");
                                        System.out.println("============================================================================================================================");
                                        System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s | %-15d |\n", i + 1, staff.getStaffId(), staff.getStaffName(), staff.getStaffIc(),
                                                staff.getStaffPhoneNo(), staff.getStaffEmail(), staff.getStaffPosition());
                                        System.out.println("============================================================================================================================");
                                        valid = true;

                                    } else if (searchName.equals("X")) {
                                        valid = true;
                                        break;
                                    } else {
                                        continue;
                                    }
                                }
                                if (valid == false) {
                                    System.err.println("Invalid staff name.\n");
                                }
                            } while (valid == false);

                            System.out.print("Press enter to continue...");
                            scanner.nextLine();
                            System.out.println("");

                            break;

                        case 3:
                            do {
                                System.out.print("Enter staff phone number to search ( X - Exit ) :");
                                searchPhoneNo = scanner.nextLine();
                                for (int i = 0; i < compare.size(); i++) {
                                    if (searchPhoneNo.equals(compare.get(i).getStaffPhoneNo())) {
                                        Staff staff = staffs.get(i);
                                        System.out.println("Staff related");
                                        System.out.println("============================================================================================================================");
                                        System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s | %-15d |\n", i + 1, staff.getStaffId(), staff.getStaffName(), staff.getStaffIc(),
                                                staff.getStaffPhoneNo(), staff.getStaffEmail(), staff.getStaffPosition());
                                        System.out.println("============================================================================================================================");
                                        System.out.print("Press enter to continue...");
                                        scanner.nextLine();
                                        System.out.println("");
                                        valid = true;
                                        break;

                                    } else if (searchPhoneNo.equals("X")) {
                                        valid = true;
                                        break;
                                    } else {
                                        valid = false;
                                    }
                                }
                                if (valid == false) {
                                    System.err.println("Invalid staff phone number.\n");
                                }
                            } while (valid == false);
                            break;

                        case 0:
                            staffMenu();
                            break;

                        default:
                            System.err.println("Invalid choices entered! Please try again...\n");
                            valid = false;
                    }
                } catch (InputMismatchException e) {
                    // accept integer only.
                    System.err.println("Wrong input ! Please only input INTEGER !!\n");
                    valid = false;
                }
            } while ((searchstaffchoice < 0 || searchstaffchoice > 3) || valid == false);
        } while (valid == true);

    }
}
