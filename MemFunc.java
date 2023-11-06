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
import java.util.InputMismatchException;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author User
 */
public class MemFunc {

    public void memMenu() {
        int memChoice = 0;
        boolean flag;
        StaffFunc staff = new StaffFunc();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("| WELCOME TO THE HEYYO MEMBERSHIP SYSTEM |");
        System.out.println("==========================================");
        System.out.println("| Please enter your selection :          |");
        System.out.println("| 1. Add Member                          |");
        System.out.println("| 2. Display Member                      |");
        System.out.println("| 3. Modify Member                       |"); //email,pn,password
        System.out.println("| 4. Delete Member                       |");
        System.out.println("| 5. Search Member                       |");
        System.out.println("| 0. Back to Main Menu                   |");
        System.out.println("==========================================");
        do {
            try {
                System.out.print("Enter your selection : ");
                memChoice = scanner.nextInt();
                flag = false;
            } catch (InputMismatchException e) {

                // accept integer only.
                System.out.println("Wrong input ! Please only input INTEGER !!\n");
                flag = true;
            }

            switch (memChoice) {

                case 1:
                    addMemberFunction();
                    break;

                case 2:
                    displayMember();
                    break;

                case 3:
                    modifyMember();
                    break;

                case 4:
                    deleteMember();
                    break;

                case 5:
                    searchMember();
                    break;

                case 0:
                    staff.mainMenu();
                    break;
//                
            }
        } while (flag == true);
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

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(\\d{3}-\\d{7})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public void addMemberFunction() {
        String memId;
        String memName = "";
        String memPw = "";
        String memPhoneNo = "";
        String memEmail = "";
        String memIc = "";
        boolean valid = true;
        String regex = "^([a-z]+)@([a-z]+)([\\.]{1})([a-z]+)$";
        char yesno;

        Member checkmemIc = readMember();
        ArrayList<Member> checkDuplicateIc = checkmemIc.returnMember();
        Member checkPhoneNo = readMember();
        ArrayList<Member> checkDuplicatePhoneNo = checkPhoneNo.returnMember();
        Scanner scanner = new Scanner(System.in);

        Member generateId = readMember();
        memId = generateId.idGenerator();
        System.out.print("Member ID :" + memId + "\n");

        do {
            try {
                System.out.print("Enter member name ( X - Exit ): ");
                memName = new Scanner(System.in).nextLine();
                if (checkName(memName) == true) {
                    System.out.println("Please enter a valid name...\n");
                }
                if (memName.isEmpty()) {
                    System.out.println("Name cannot be empty !\n");
                } else if (memName.toUpperCase().equals("X")) {
                    memMenu();
                    break;
                }
            } catch (NullPointerException ex) {
            }
        } while (memName.isEmpty());

        if (!memName.toUpperCase().equals("X")) {
            do {
                try {
                    System.out.print("Enter member phone number : ");
                    memPhoneNo = new Scanner(System.in).next();

                    if (!isValidPhoneNumber(memPhoneNo)) {
                        System.out.println("Please enter a valid contact number with '-'..\n");

                        valid = false;
                    } else if (memPhoneNo.isEmpty()) {
                        System.out.println("Phone number cannot be empty !\n");
                        valid = false;
                    } else {
                        for (int i = 0; i < checkDuplicatePhoneNo.size(); i++) {
                            if (memPhoneNo.equals(checkDuplicatePhoneNo.get(i).getMemPhoneNo())) {
                                System.out.println("This phone number [" + memPhoneNo + "]have been registered before !\n");
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
                System.out.print("Enter member email : ");
                memEmail = new Scanner(System.in).next();
                Pattern pattern = Pattern.compile(regex);

                Matcher matcher = pattern.matcher(memEmail);
                if (!matcher.matches()) {
                    System.out.println("\nError: Invalid email format.\n");
                    valid = false;
                } else {
                    valid = true;
                }
            } while (valid == false);

            do {
                System.out.print("Enter password : ");
                memPw = new Scanner(System.in).next();
                if (memPw.isEmpty()) {
                    System.out.println("Password cannot be empty !");
                    valid = false;
                } else if (memPw.length() != 8) {
                    System.out.println("Password should be 8 characters or numbers ! \n");
                    valid = false;
                } else {
                    valid = true;
                }
            } while (valid == false);

            do {
                System.out.print("Enter member ic number (with '-'): ");
                memIc = new Scanner(System.in).next();
                if (!validateICNumber(memIc)) {
                    System.out.println("Ic number should be 14 numbers with '-' ! \n");
                    valid = false;
                } else if (memIc.isEmpty()) {
                    System.out.println("Ic number cannot be empty !");
                    valid = false;
                } else {
                    for (int i = 0; i < checkDuplicateIc.size(); i++) {
                        if (memIc.equals(checkDuplicateIc.get(i).getMemIc())) {
                            System.out.println("This Ic number [" + memIc + "]have been registered before !\n");
                            valid = false;
                            break;
                        } else {
                            valid = true;
                        }
                    }
                }
            } while (valid == false);

            addMember(memId, memName, memPhoneNo, memIc, memEmail, memPw);

            System.out.println("Do you want to continue add member? (Y/N)");
            yesno = scanner.next().toUpperCase().charAt(0);
            if (yesno == 'Y') {
                addMemberFunction();
            } else {
                memMenu();
            }
        }
    }

    public void addMember(String memId, String memName, String memPhoneNo, String memIc, String memEmail, String memPw) {
        try {
            FileWriter addMember = new FileWriter("member.txt", true);
            addMember.append(memId + "|" + memName + "|" + memPhoneNo + "|" + memIc + "|" + memEmail + "|" + memPw + "\n");
            addMember.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found\n");
        } catch (IOException ex) {
        }
    }

    public void clearMember() {
        try {
            FileWriter clearMember = new FileWriter("member.txt");
            clearMember.write("");
            clearMember.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found\n");
        } catch (IOException ex) {
        }
    }

    public Member readMember() {
        ArrayList<Member> storemember = new ArrayList<Member>();
        Member passmember = new Member();
        try {
            FileReader readMember = new FileReader("member.txt");
            BufferedReader es = new BufferedReader(readMember);
            String existingMember = es.readLine();
            do {
                StringTokenizer stn = new StringTokenizer(existingMember, "|");
                String memid = stn.nextToken();
                String memname = stn.nextToken();
                String memphone = stn.nextToken();
                String memic = stn.nextToken();
                String mememail = stn.nextToken();

                String mempassword = stn.nextToken();

                Member member = new Member(memid, memname, memphone, memic, mememail, mempassword);

                storemember.add(member);

                passmember.passArrayList(storemember);

                passmember.incrementOfNumberOfMember();

            } while ((existingMember = es.readLine()) != null);
            readMember.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (NullPointerException ex) {
        }

        return passmember;

    }

    public List myRead() {
        ArrayList<Member> storemember = new ArrayList<Member>();

        int i = 0;

        try {
            FileReader memberfile = new FileReader("member.txt");
            BufferedReader rm = new BufferedReader(memberfile);
            String members = rm.readLine();

            do {
                StringTokenizer stn = new StringTokenizer(members, "|");
                String memid = stn.nextToken();
                String memname = stn.nextToken();
                String memphone = stn.nextToken();
                String memic = stn.nextToken();
                String mememail = stn.nextToken();

                String mempassword = stn.nextToken();

                Member read = new Member();
                read.setMemId(memid);
                read.setMemName(memname);
                read.setMemPhoneNo(memphone);
                read.setMemIc(memic);
                read.setMemEmail(mememail);
                read.setMempw(mempassword);

                storemember.add(read);

            } while ((members = rm.readLine()) != null);
            memberfile.close();
        } catch (IOException e) {
            System.err.println("File not found");
        }
        return storemember;
    }

    public void displayMember() {

        List<Member> members = new ArrayList<>();
        members = myRead();
        Scanner scanner = new Scanner(System.in);
        int length = 0;

        try {
            length = 0;
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).equals(null)) {
                    break;
                }
                length++;
            }
        } catch (NullPointerException ex) {
        }

        if (members.get(0).equals(null)) {
            System.out.println("No member in database");
        } else {
            System.out.println("===========================================================================================================");
            System.out.printf("| %s| %-6s | %-20s | %-14s | %-14s | %-30s |\n", "No", "Member Id", "Name", "Phone No", "Ic", "Email");
            System.out.println("===========================================================================================================");

            for (int i = 0; i < length; i++) {
                Member member = members.get(i);
                System.out.printf("| %d | %-8s  | %-20s | %-14s | %-14s | %-30s |\n",
                        i + 1, member.getMemId(), member.getMemName(), member.getMemPhoneNo(), member.getMemIc(), member.getMemEmail());
            }
            System.out.println("===========================================================================================================");
        }
        System.out.print("Press enter to continue...");
        scanner.nextLine();
        System.out.println("");
        memMenu();
    }

    public void displayAtModify() {
        List<Member> members = new ArrayList<>();
        members = myRead();
        Scanner scanner = new Scanner(System.in);
        int length = 0;

        try {
            length = 0;
            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).equals(null)) {
                    break;
                }
                length++;
            }
        } catch (NullPointerException ex) {
        }

        if (members.get(0).equals(null)) {
            System.out.println("No member in database");
        } else {
            System.out.println("===========================================================================================================");
            System.out.printf("| %s| %-6s | %-20s | %-14s | %-14s | %-30s |\n", "No", "Member Id", "Name", "Ic", "Phone No", "Email");
            System.out.println("===========================================================================================================");

            for (int i = 0; i < length; i++) {
                Member member = members.get(i);
                System.out.printf("| %d | %-8s  | %-20s | %-14s | %-14s | %-30s |\n",
                        i + 1, member.getMemId(), member.getMemName(), member.getMemIc(), member.getMemPhoneNo(), member.getMemEmail());
            }
            System.out.println("===========================================================================================================");
        }

        System.out.println("");

    }

    public void modifyMember() {
        Member checkmember = readMember();
        ArrayList<Member> compare = checkmember.returnMember();
        String memId = "";
        String newMemName, newMemPw, newMemEmail, newMemPhoneNo;
        String regex = "^([a-z]+)@([a-z]+)([\\.]{1})([a-z]+)$";
        boolean valid = true;
        boolean loop = true;
        int modifySelection = 0, count = 0, length = 0;
        char yesno;
        Scanner scanner = new Scanner(System.in);

        displayAtModify();

        do {

            System.out.print("Enter Member Id to Modify :  ");
            memId = new Scanner(System.in).next();
            memId = memId.toUpperCase();
            for (int i = 0; i < compare.size(); i++) {
                if (memId.equals(compare.get(i).getMemId())) {
                    count = i;
                    valid = true;
                    break;
                } else if (memId.equals("X")) {
                    valid = true;
                    break;
                } else {
                    valid = false;
                }
            }
            if (valid == false) {
                System.err.println("Invalid member ID.\n");
            } else if (memId.equals("X")) {
                break;
            } else {
                do {
                    System.out.println("\n==========================================");
                    System.out.println("| Modification of " + memId + "                  |");
                    System.out.println("| 1. Member Name                         |");
                    System.out.println("| 2. Member Password                     |");
                    System.out.println("| 3. Member Email                        |");
                    System.out.println("| 4. Member Phone Number                 |");
                    System.out.println("| 0. Back to Member Menu                 |");
                    System.out.println("==========================================");
                    do {
                        try {
                            System.out.printf("Enter your selection : ");
                            modifySelection = new Scanner(System.in).nextInt();
                            switch (modifySelection) {
                                case 1:
                                    do {
                                        loop = true;
                                        System.out.print("Enter member new name :");
                                        newMemName = scanner.nextLine();

                                        if (newMemName.isEmpty()) {
                                            System.err.println("Name cannot be empty !\n");
                                        } else if (newMemName.toUpperCase().equals("X")) {
                                            valid = false;
                                        } else if (!newMemName.isEmpty()) {
                                            valid = true;
                                            compare.get(count).setMemName(newMemName);
                                            clearMember();
                                            for (int i = 0; i < compare.size(); i++) {
                                                addMember(compare.get(i).getMemId(), compare.get(i).getMemName(), compare.get(i).getMemPhoneNo(), compare.get(i).getMemIc(), compare.get(i).getMemEmail(), compare.get(i).getMempw());
                                            }
                                        }
                                    } while (checkName(newMemName) == true);

                                    System.out.println("\n\t\t Note : Member name successfully modified!");
                                    break;

                                case 2:
                                    do {
                                        loop = true;
                                        System.out.print("Enter member new password :");
                                        newMemPw = scanner.next();

                                        if (newMemPw.isEmpty()) {
                                            System.err.println("Password cannot be empty !");
                                            valid = false;
                                        } else if (newMemPw.length() != 8) {
                                            System.err.println("Password should be 8 characters or numbers ! \n");
                                            valid = false;
                                        } else {
                                            valid = true;
                                            compare.get(count).setMempw(newMemPw);
                                            clearMember();
                                            for (int i = 0; i < compare.size(); i++) {

                                                addMember(compare.get(i).getMemId(), compare.get(i).getMemName(), compare.get(i).getMemPhoneNo(), compare.get(i).getMemIc(), compare.get(i).getMemEmail(), compare.get(i).getMempw());
                                            }
                                        }
                                    } while (valid == false);

                                    System.out.println("\n\t\t Note : Member password successfully modified!");
                                    break;

                                case 3:
                                    do {
                                        loop = true;
                                        System.out.print("Enter member new email : ");
                                        newMemEmail = new Scanner(System.in).next();
                                        Pattern pattern = Pattern.compile(regex);

                                        Matcher matcher = pattern.matcher(newMemEmail);
                                        if (!matcher.matches()) {
                                            System.err.println("\nError: Invalid email format.\n");
                                            valid = false;
                                        } else {
                                            valid = true;//setStaffEmail
                                            compare.get(count).setMemEmail(newMemEmail);
                                            clearMember();
                                            for (int i = 0; i < compare.size(); i++) {

                                                addMember(compare.get(i).getMemId(), compare.get(i).getMemName(), compare.get(i).getMemPhoneNo(), compare.get(i).getMemIc(), compare.get(i).getMemEmail(), compare.get(i).getMempw());
                                            }
                                        }
                                    } while (valid == false);

                                    System.out.println("\n\t\t Note : Member email successfully modified!\n");
                                    break;

                                case 4:
                                    do {
                                        loop = true;
                                        try {
                                            System.out.print("Enter member new phone number : ");
                                            newMemPhoneNo = new Scanner(System.in).next();

                                            if (!isValidPhoneNumber(newMemPhoneNo)) {
                                                System.err.println("Please enter a valid contact number with '-'..\n");

                                                valid = false;
                                            } else if (newMemPhoneNo.isEmpty()) {
                                                System.err.println("Phone number cannot be empty !\n");
                                                valid = false;
                                            
                                            } else {
                                                valid = true;//setStaffPhoneNo
                                                compare.get(count).setMemPhoneNo(newMemPhoneNo);
                                                clearMember();
                                                for (int i = 0; i < compare.size(); i++) {

                                                    addMember(compare.get(i).getMemId(), compare.get(i).getMemName(), compare.get(i).getMemPhoneNo(), compare.get(i).getMemIc(), compare.get(i).getMemEmail(), compare.get(i).getMempw());
                                                }
                                            }
                                        } catch (NullPointerException ex) {
                                        }
                                    } while (valid == false);

                                    System.out.println("\n\t\t Note : Member phone number successfully modified!\n");
                                    break;

                                case 0:
                                    memMenu();
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
            System.out.print("Do you want to continue modify other member ? (Y/N)");
            yesno = scanner.next().toUpperCase().charAt(0);
            if (yesno == 'Y') {
                valid = true;
                modifyMember();
            } else if (yesno == 'N') {
                valid = true;
                memMenu();
            } else {
                System.err.println("Invalid input ! Please enter y or n !!\n");
                valid = false;
            }
        } while (valid == false);
    }

    public boolean checkName(String memName) {
        int letter = 0, space = 0;
        boolean check = true;

        boolean ltr = false;
        boolean digit = true;
        int len = memName.length();

        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(memName.charAt(i))) {
                space++;
            }
            if (Character.isDigit(memName.charAt(i))) {
                digit = false;
            }

            if (Character.isLetter(memName.charAt(i))) {
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

    public void deleteMember() {

        String deleteId;
        Scanner scanner = new Scanner(System.in);
        Member checkmember = readMember();
        ArrayList<Member> compare = checkmember.returnMember();
        char confirm;

        clearMember();
        System.out.println("Enter member Id to delete :");
        deleteId = scanner.next().toUpperCase();
        for (int i = 0; i < compare.size(); i++) {
            if (deleteId.equals(compare.get(i).getMemId())) {
                continue;
            } else {
                addMember(compare.get(i).getMemId(), compare.get(i).getMemName(), compare.get(i).getMemPhoneNo(), compare.get(i).getMemIc(), compare.get(i).getMemEmail(), compare.get(i).getMempw());
            }
        }
        do {
            System.out.print("Are you confirm to delete this member ? [Y - Yes / N - No] :");
            confirm = scanner.next().toUpperCase().charAt(0);
            scanner.nextLine();
            if (confirm != 'Y' && confirm != 'N') {
                System.out.println("Please enter a 'Y' or 'N' only...\n");
            }
        } while (confirm != 'Y' && confirm != 'N');
        if (confirm == 'Y') {
            System.out.printf("      -Information of member %s is deleted!-\n", deleteId);
        } else {
            System.out.println("     -Member is not deleted!-\n");
        }
        System.out.println("Do you want to delete other member  (Y/N)? ");
        confirm = scanner.next().toUpperCase().charAt(0);
        if (confirm != 'Y' && confirm != 'N') {
            deleteMember();
        } else {
            memMenu();
        }
    }

    public void searchMember() {

        int searchmemberchoice = 0;
        String searchId, searchName, searchPhoneNo;
        Scanner scanner = new Scanner(System.in);
        Member checkmember = readMember();
        ArrayList<Member> compare = checkmember.returnMember();

        ArrayList<Member> members = checkmember.returnMember();
        boolean valid = true;

        do {
            System.out.println("\n\t\t  Search Member");
            System.out.println("\t\t================");
            System.out.println("1. Member Id");
            System.out.println("2. Member Name");
            System.out.println("3. Member Phone Number");
            System.out.println("0. Back to member menu");

            do {
                try {
                    System.out.print("Enter to search member by : ");
                    searchmemberchoice = new Scanner(System.in).nextInt();
                    switch (searchmemberchoice) {
                        case 1:
                            do {
                                System.out.print("Enter member Id to search ( X - Exit ) :");
                                searchId = scanner.next();
                                searchId = searchId.toUpperCase();
                                for (int i = 0; i < compare.size(); i++) {
                                    if (searchId.equals(compare.get(i).getMemId())) {
                                        Member member = members.get(i);
                                        System.out.println("Member founded");
                                        System.out.println("============================================================================================================================");
                                        System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s |\n", i + 1, members.get(i).getMemId(), members.get(i).getMemName(), members.get(i).getMemIc(),
                                                members.get(i).getMemPhoneNo(), members.get(i).getMemEmail());
                                        System.out.println("============================================================================================================================");
                                        System.out.print("Press enter to continue...");
                                        scanner.nextLine();
                                        System.out.println("");
                                        valid = true;

                                    } else if (searchId.equals("X")) {
                                        valid = true;
                                        break;
                                    } else {
                                        continue;
                                    }
                                }
                                if (valid == false) {
                                    System.err.println("Invalid member ID.\n");
                                }
                            } while (valid == false);

                            break;

                        case 2:
                            valid = false;
                            do {
                                System.out.print("Enter member name to search ( X - Exit ) :");
                                searchName = scanner.nextLine();
                                for (int i = 0; i < compare.size(); i++) {
                                    if (searchName.equals(compare.get(i).getMemName())) {
                                        Member member = members.get(i);
                                        System.out.println("Member founded");
                                        System.out.println("============================================================================================================================");
                                        System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s |\n", i + 1, members.get(i).getMemId(), members.get(i).getMemName(), members.get(i).getMemIc(),
                                                members.get(i).getMemPhoneNo(), members.get(i).getMemEmail());
                                        System.out.println("============================================================================================================================");
                                        System.out.print("Press enter to continue...");
                                        scanner.nextLine();
                                        System.out.println("");
                                        valid = true;
                                        break;

                                    } else if (searchName.equals("X")) {
                                        valid = true;
                                        break;
                                    } else {
                                        continue;
                                    }
                                }
                                if (valid == false) {
                                    System.err.println("Invalid member name.\n");
                                }
                            } while (valid == false);

                            break;

                        case 3:
                            do {
                                System.out.print("Enter member phone number to search ( X - Exit ) :");
                                searchPhoneNo = scanner.nextLine();
                                for (int i = 0; i < compare.size(); i++) {
                                    if (searchPhoneNo.equals(compare.get(i).getMemPhoneNo())) {
                                        Member member = members.get(i);
                                        System.out.println("Member founded");
                                        System.out.println("============================================================================================================================");
                                        System.out.printf("| %d | %-8s | %-20s | %-14s | %-14s | %-30s |\n", i + 1, members.get(i).getMemId(), members.get(i).getMemName(), members.get(i).getMemIc(),
                                                members.get(i).getMemPhoneNo(), members.get(i).getMemEmail());
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
                                        continue;
                                    }
                                }
                                if (valid == false) {
                                    System.err.println("Invalid member phone number.\n");
                                }
                            } while (valid == false);
                            break;

                        case 0:
                            memMenu();
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
            } while ((searchmemberchoice < 0 || searchmemberchoice > 3) || valid == false);
        } while (valid == true);

    }

}
