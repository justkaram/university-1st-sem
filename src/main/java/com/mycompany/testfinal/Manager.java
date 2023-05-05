/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import static com.mycompany.testfinal.AuthSystem.in;
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
        if (!loginManager()) {
            if (!checkStatus) {
                System.out.println("Your Account is disabled");
            } else {
                System.out.println("User id is not found or password is wrong.");
            }
        } else {
            switchFile("employee.json");
            managerInterface();
        }
    }

    public boolean loginManager() {
        System.out.println("Enter ID: ");
        String user = in.next();
        JsonArray array = jsonObject.getJsonArray(user);
        System.out.println("Enter Password: ");
        String pass = in.next();
        if (array == null) {
            return false;
        } else {
            try {
                boolean checkPass = ((JsonString) array.get(1)).getString().equals(pass);
                checkStatus = ((JsonNumber) array.get(array.size() - 1)).intValue() == 1;
                if (checkPass && checkStatus) {
                    managerId = user;
                    return true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;

    }

    public String fastCheckId() {
        System.out.println("Enter Id");
        String id = in.next();
        String idCheck = super.fastCheckId(id);
        if (idCheck.equals("")) {
            System.out.println("Employee id is not found :)");
        }
        return idCheck;
    }

    private void managerInterface() {
        Scanner input = new Scanner(System.in);
        System.out.println(">>>>> Welcome " + managerId + " >>>>>");
        OUTER:
        while (true) {
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

            int userChoice = 0;
            try {
                userChoice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input must be an Integer !!! ");
                managerInterface();

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
                    statusManager();
                }
                case 8 -> {
                    managerAttendance();
                    switchFile("employee.json");
                }
                default -> {
                    System.out.println("Wrong Input !");
                }
            }

            jsonReader();
        }
    }

    private void addEmployee() {
        /*
        Creats an ArrayList with manager info and adds it to managers.json
         */
        ArrayList employeeInfo = new ArrayList();
        System.out.println("Enter Id: ");
        long employeeId = in.nextLong();
        employeeInfo.add(employeeId);

        System.out.println("Enter Name: ");
        in.nextLine();
        String managerName = in.next();
        employeeInfo.add(managerName);

        System.out.println("Enter Password: ");
        in.nextLine();
        String managerPass = in.nextLine();
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
        try {
            for (String key : jsonObject.keySet()) {
                empReport(key);
            }
        } catch (NullPointerException e) {
            System.out.println("No Employees Yet :)");
        }
    }

    private void managerAttendance() {
        switchFile("managers.json");
        try {
            while (true) {
                System.out.println("""
                               >>>>> Attendance >>>>>
                               1- Time of attendance
                               2- Time of leaving
                               3- Exit                         
                               """);

                int choice;
                String timeMessage = "Enter the time ";
                choice = in.nextInt();
                String time;
                if (choice == 1) {
                    System.out.println(timeMessage);
                    time = in.next();
                    updater(4, managerId, time);
                    break;
                } else if (choice == 2) {
                    System.out.println(timeMessage);
                    time = in.next();
                    updater(5, managerId, time);
                    break;
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid Input");

                }

            }
        } catch (InputMismatchException | NullPointerException e) {
            System.out.println("Invalid Input");

        }

    }

}
