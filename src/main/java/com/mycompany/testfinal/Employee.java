/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import static com.mycompany.testfinal.AuthSystem.in;
import static com.mycompany.testfinal.AuthSystem.jsonObject;
import java.util.Scanner;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;

/**
 *
 * @author ASUS ROG
 */
public class Employee extends Manager {

    private Scanner input = new Scanner(System.in);
    private boolean empStatus = true;
    private String empId;
    private String empName;

    public Employee() {
        super("employee.json");
        logEmp();

    }

    private void logEmp() {
        String logResult = super.login();
        if (logResult.equals("")) {
            System.out.println("Either id is not found or password is wrong. ");
        }else if (logResult.equals("disabled")){
            System.out.println("Account is Disabled.");
        }else{
            empId = logResult;
            empInterface();
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

            int userChoice;
            userChoice = input.nextInt();
            switch (userChoice) {
                case 4 -> {
                    break OUTER;
                }
                case 1 -> {
                    updateEmpPass();
                }
                case 2 -> {
                    Attendance(empId,5,6);
                }
                case 3 -> {
                    Holidays(empId);
                }
            }
        }
    }

    private void updateEmpPass() {
        System.out.println(">>>>> Change Password <<<<<");
        System.out.println("Enter New Password: ");
        String newPass = input.next();
        super.updater(1, empId, newPass);
        System.out.println("Password has been updated successfully");
    }
}
