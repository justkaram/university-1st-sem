/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import static com.mycompany.testfinal.AuthSystem.jsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;

/**
 *
 * @author ASUS ROG
 */
public class Manager extends Admin {

    private boolean checkStatus = true;
    private String managerId;
    private String empId;

    public Manager() {
        if (loginManager().equals("")) {
            System.out.println("User id is not found or password is wrong.");
        } else {
            if (!checkStatus) {
                System.out.println("Your Account is disabled !!");
            } else {
                managerInterface();
            }
        }
    }

    public Manager(String fromChild) {
        switchFile(fromChild);
    }

    public String loginManager() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter ID: ");
        String user = in.next();
        JsonArray array = jsonObject.getJsonArray(user);
        System.out.println("Enter Password: ");
        String pass = in.next();
        if (array == null) {
            return "";
        } else {
            try {
                boolean checkPass = ((JsonString) array.get(1)).getString().equals(pass);
                checkStatus = ((JsonNumber) array.get(array.size() - 1)).intValue() == 1;
                if (checkPass && checkStatus) {
                    managerId = user;
                    return user;
                } else if (checkPass && (!checkStatus)) {
                    return "disabled";
                }
            } catch (NullPointerException e) {
                return "";
            }
        }
        return "";

    }

    public String fastCheckId() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Id");
        String id = in.next();
        String idCheck = super.fastCheckId(id);
        if (idCheck.equals("")) {
            System.out.println("Employee id is not found :)");
        }
        return idCheck;
    }

    private void managerInterface() {

        System.out.println(">>>>> Welcome " + managerId + " >>>>>");
        OUTER:
        while (true) {
            Scanner input = new Scanner(System.in);

            switchFile("employee.json");
            System.out.println("""
                                           ----------------------------------------------------------------
                                                                  1- Add Employee
                                                                  2- Update Employee
                                                                  3- Delete Employee
                                                                  4- Search About Employee
                                                                  5- Report About Employee
                                                                  6- Report About All Employees
                                                                  7- Deactivate And Activate Employee
                                                                  8- Attendance
                                                                  9- Holiday
                                                                  10- Exit
                                                                  """);

            Scanner in = new Scanner(System.in);
            int userChoice = 0;
            try {
                userChoice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input must be an Integer !!! ");

            }
            switch (userChoice) {
                case 10 -> {
                    break OUTER;
                }
                case 1 -> {
                    addEmployee();
                }
                case 2 -> {
                    empId = fastCheckId();
                    if (!(empId.equals(""))) {
                        System.out.println("Enter Password: ");
                        String password = in.next();
                        updater(1, empId, password);
                        System.out.println("Employee Password has been changed Successfully.");
                    }
                }
                case 3 -> {
                    empId = fastCheckId();
                    if (!(empId.equals(""))) {
                        deleteFromJson(empId);
                        switchFile("holidays.json");
                        deleteFromJson(empId);
                        System.out.println("Employee has been deleted.");
                    }
                }
                case 4 -> {
                    empId = fastCheckId();
                    if (!(empId.equals(""))) {
                        empReport(empId);
                    }
                }
                case 5 -> {
                    empId = fastCheckId();
                    if (!(empId.equals(""))) {
                        searchUser(empId);
                    }
                }
                case 6 -> {
                    allReport();
                }
                case 7 -> {
                    statusUpdater();
                }
                case 8 -> {
                    switchFile("managers.json");
                    Attendance(managerId, 4, 5);
                    switchFile("employee.json");
                }
                case 9 -> {
                    Holidays(managerId);
                }
                default -> {
                    System.out.println("Wrong Input !");
                }
            }

        }
    }

    private void addEmployee() {
        /*
        Creats an ArrayList with manager info and adds it to managers.json
         */

        try {
            Scanner in = new Scanner(System.in);

            ArrayList employeeInfo = new ArrayList();
            System.out.println("Enter Id: ");
            long employeeId = in.nextLong();
            employeeInfo.add(employeeId);

            System.out.println("Enter Name: ");
            in.nextLine();
            String managerName = in.nextLine();
            employeeInfo.add(managerName);

            System.out.println("Enter Password: ");
            String managerPass = in.next();
            employeeInfo.add(managerPass);

            System.out.println("Enter Email: ");
            String managerEmail = in.next();
            employeeInfo.add(managerEmail);

            System.out.println("Enter Phone: ");
            in.nextLine();
            String managerPhone = in.next();
            employeeInfo.add(managerPhone);

            System.out.println("Type of employee (1) <Full-Time> or (2) <Part-Time>");
            int employeeType = in.nextInt();
            employeeInfo.add(employeeType);

            employeeInfo.add("Not Signed yet");
            employeeInfo.add("Not Signed yet");

            System.out.println("Enter Status: ");
            int empStatus = in.nextInt();
            if (empStatus == 0 || empStatus == 1) {
                employeeInfo.add(empStatus);
            } else {
                System.out.println("employee Status must be either 0 or 1 !");
                return;
            }

            addNewToJson(String.valueOf(employeeId), employeeInfo);
            System.out.println("The employee has been added successfully");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input !");
        }

    }

    private void empReport(String empId) {
        String sep = " || ";
        JsonArray infoArray = jsonObject.getJsonArray(empId);
        JsonString name = (JsonString) infoArray.get(0);
        JsonString email = (JsonString) infoArray.get(2);
        JsonString phone = (JsonString) infoArray.get(3);
        JsonNumber empType = (JsonNumber) infoArray.get(4);
        JsonString checkIn = (JsonString) infoArray.get(5);
        JsonString checkOut = (JsonString) infoArray.get(6);
        JsonNumber status = (JsonNumber) infoArray.get(infoArray.size() - 1);
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ")
                .append(empId)
                .append(sep)
                .append("Name: ")
                .append(name.getString())
                .append(sep)
                .append("Email: ")
                .append(email.getString())
                .append(sep)
                .append("Phone: ")
                .append(phone.getString())
                .append(sep)
                .append("Employee Type: ")
                .append(empType)
                .append(sep)
                .append("Status: ")
                .append(status)
                .append(sep)
                .append("Check In: ")
                .append(checkIn.getString())
                .append(sep)
                .append("Check Out: ")
                .append(checkOut.getString());

        System.out.println(sb.toString());

    }

    private void allReport() {
        if (jsonObject.keySet().toArray().length < 1) {
            System.out.println("No Employees Yet :)");
        }

        for (String key : jsonObject.keySet()) {
            empReport(key);
        }

    }

    public void Attendance(String id, int index1, int index2) {
        try {
            OUTER:
            while (true) {
                System.out.println("""
                                                   >>>>> Attendance >>>>>
                                                   1- Time of attendance
                                                   2- Time of leaving
                                                   3- Exit
                                                   """);
                Scanner in = new Scanner(System.in);

                int choice;
                choice = in.nextInt();
                String time;
                String timeMessage = "Enter the time ";
                switch (choice) {
                    case 1 -> {
                        System.out.println(timeMessage);
                        time = in.next();
                        updater(index1, id, time);
                        System.out.println("Time of attendance has been updated. ");
                        break OUTER;
                    }
                    case 2 -> {
                        System.out.println(timeMessage);
                        time = in.next();
                        updater(index2, id, time);
                        System.out.println("Time of leaving has been updated. ");

                        break OUTER;
                    }
                    case 3 -> {
                        break OUTER;
                    }
                    default ->
                        System.out.println("Invalid Input");
                }
            }
        } catch (InputMismatchException | NullPointerException e) {
            System.out.println("Invalid Input");

        }

    }

    protected void Holidays(String id) {
        /*
        Manage Holidays
         */
        switchFile("holidays.json");
        JsonArray holidays = jsonObject.getJsonArray(id);
        if (holidays == null) {
            ArrayList emptyHoliday = new ArrayList();
            addNewToJson(id, emptyHoliday);
        }

        try {
            OUTER:
            while (true) {
                System.out.println("""
                                                   >>>>> Holidays >>>>>
                                                   1- My Holidays.
                                                   2- Create Holiday
                                                   3- Exit
                                                                            """);
                Scanner in = new Scanner(System.in);
                int choice;
                choice = in.nextInt();
                switch (choice) {
                    case 3 -> {
                        break OUTER;
                    }
                    case 2 -> {
                        try {
                            System.out.println(">>>>> Create Holidays <<<<<");
                            createHoliday(id);
                        } catch (IOException e) {
                            System.out.println(e.getClass());
                        }
                    }
                    case 1 -> {
                        System.out.println(">>>>> My Holidays <<<<<");
                        ArrayList<StringBuilder> list = holidaysViewer(id);
                        System.out.println(list);
                        if ((list == null) || list.isEmpty()) {
                            System.out.println("Id doesn't any holidays requests yet !");
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                System.out.println(list.get(i));
                            }
                        }
                    }
                    default -> {
                        System.out.println("Invalid Input");
                    }
                }
            }

        } catch (NullPointerException | InputMismatchException e) {
            System.out.println("Invalid Input !!");
        }

    }

    private void createHoliday(String id) throws IOException {
        /*
        Creates Holiday Request 
         */
        Scanner in = new Scanner(System.in);
        ArrayList holidayArray = new ArrayList();
        System.out.println("Enter Name: ");
        String name = in.nextLine();
        holidayArray.add(name);

        System.out.println("Enter Reason: ");
        String reason = in.nextLine();
        holidayArray.add(reason);

        System.out.println("Enter Details: ");
        String details = in.nextLine();
        holidayArray.add(details);

        System.out.println("Enter Date: ");
        String date = in.next();
        holidayArray.add(date);

        // holiday status , default value is 0, an admin must accept it.
        holidayArray.add(0);
        super.updaterHoliday(id, holidayArray);
    }

}
