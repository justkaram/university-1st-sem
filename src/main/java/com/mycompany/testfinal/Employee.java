/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import static com.mycompany.testfinal.AuthSystem.jsonObject;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ASUS ROG
 */
public class Employee extends Manager {

    private String empId;

    public Employee() {
        super("employee.json");
        logEmp();

    }

    private void logEmp() {
        String logResult = super.loginManager();
        switch (logResult) {
            case "" ->
                System.out.println("Either id is not found or password is wrong. ");
            case "disabled" ->
                System.out.println("Account is Disabled.");
            default -> {
                empId = logResult;
                empInterface();
            }
        }

    }

    private void empInterface() {
        System.out.println(">>>>> Welcome " + empId + " >>>>>");
        OUTER:
        while (true) {
            switchFile("employee.json");
            System.out.println("""
                                           ----------------------------------------------------------------
                                                                  1- Change Password
                                                                  2- Attendance
                                                                  3- Holiday
                                                                  4- Exit
                                                                  """);
            Scanner in = new Scanner(System.in);
            int userChoice;
            userChoice = in.nextInt();
            switch (userChoice) {
                case 4 -> {
                    break OUTER;
                }
                case 1 -> {
                    updateEmpPass();
                }
                case 2 -> {
                    Attendance(empId, 5, 6);
                }
                case 3 -> {
                    for (String key : jsonObject.keySet()) {
                        if (key.equals(empId)) {
                            ArrayList<StringBuilder> list = holidaysViewer(key);
                            if (!(list == null)) {
                                for (int i = 0; i < list.size(); i++) {
                                    System.out.println(list.get(i));
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    private void updateEmpPass() {
        Scanner in = new Scanner(System.in);
        System.out.println(">>>>> Change Password <<<<<");
        System.out.println("Enter New Password: ");
        String newPass = in.next();
        super.updater(1, empId, newPass);
        System.out.println("Password has been updated successfully");
    }
}
