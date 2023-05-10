/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.json.JsonArrayBuilder;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonString;

/**
 *
 * @author ASUS ROG
 */
public class Admin extends AuthSystem {

    private static int managerCount;
    private static int employeeCount;

    public Admin() {
        super("managers.json");
    }

    protected void managerCounter() {
        try {
            switchFile("managers.json");
            System.out.println(jsonObject.keySet().toString());
            managerCount = jsonObject.keySet().toArray().length;
            System.out.println(managerCount);
        } catch (NullPointerException e) {
            System.out.println("No Managers Yet :)");
        }

    }

    protected void employeeCounter() {
        Admin.employeeCount += 1;
    }

    public void LoginAdmin() {
        if (login()) {
            AdminInterFace();
        }

    }

    private void AdminInterFace() {
        switchFile("managers.json");
        System.out.println(">>>>> Welcome " + super.getUserName() + " >>>>>");
        OUTER:
        while (true) {
            System.out.println("""
                                           ----------------------------------------------------------------
                                                                  1- Add Manager
                                                                  2- Update Manager
                                                                  3- Delete Manager
                                                                  4- Search About Manager
                                                                  5- Report About Manager
                                                                  6- Report About All Managers
                                                                  7- Hoilday Requests
                                                                  8- Deactivate And Activate Manager
                                                                  9- Get Manager Count and Employee Count
                                                                  10- Exit
                                                                  """);

            Scanner input = new Scanner(System.in);
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
                case 1 ->
                    addManager();
                case 2, 3, 4, 5 -> {
                    if (!managerOperations(userChoice)) {
                        System.out.println("Manager Id not found, check Id and try again");
                    }
                }
                case 6 -> {
                    allReport();
                }
                case 8 -> {
                    statusUpdater();
                }
                case 9 -> {
                    managerCounter();
                }
                default -> {
                    System.out.println("Wrong Input !");
                }
            }

            jsonReader();
        }
    }
    


    private boolean managerOperations(int choice) {
        try {
            System.out.println("Enter Manager Id: ");
            String managerId = fastCheckId(in.next());
            if (managerId.equals("")) {
                return false;
            } else if (choice == 2) {
                System.out.println("Enter Password: ");
                String password = in.next();
                updater(1, managerId, password);
                System.out.println("Password Updated Successfully");
            } else if (choice == 3) {
                deleteFromJson(managerId);
                System.out.println("Manager has been deleted successfully");
            } else if (choice == 4) {
                searchUser(managerId);

            } else if (choice == 5) {
                managerReport(managerId);
            }
            return true;

        } catch (NullPointerException e) {
            return false;

        }

    }

    protected void searchUser(String id) {
        JsonArray infoArray = jsonObject.getJsonArray(id);
        JsonString name = (JsonString) infoArray.get(0);
        JsonNumber status = (JsonNumber) infoArray.get(infoArray.size() - 1);
        System.out.print("Name: " + name.getString());
        System.out.println(" || Status: " + status);

    }

    private void managerReport(String managerId) {
        String sep = " || ";
        JsonArray infoArray = jsonObject.getJsonArray(managerId);
        JsonString name = (JsonString) infoArray.get(0);
        JsonString email = (JsonString) infoArray.get(2);
        JsonString phone = (JsonString) infoArray.get(3);
        JsonString checkIn = (JsonString) infoArray.get(4);
        JsonString checkOut = (JsonString) infoArray.get(5);
        JsonNumber status = (JsonNumber) infoArray.get(infoArray.size() - 1);
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ")
                .append(managerId)
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
                .append("Status: ")
                .append(status)
                .append(sep)
                .append("Check In: ")
                .append(checkIn)
                .append(sep)
                .append("Check Out: ")
                .append(checkOut);

        System.out.println(sb.toString());

    }

    private void allReport() {
        if (jsonObject.keySet().toArray().length < 1) {
            System.out.println("No Managers Yet :)");
            return;
        }

        for (String key : jsonObject.keySet()) {
            managerReport(key);
        }

    }

    private void addManager() {
        /*
        Creats an ArrayList with manager info and adds it to managers.json
         */

        Admin.managerCount += 1;

        ArrayList managerInfo = new ArrayList();
        System.out.println("Enter Id: ");
        long managerId = in.nextLong();
        System.out.println("Enter Name: ");
        in.nextLine();
        String managerName = in.next();
        managerInfo.add(managerName);

        System.out.println("Enter Password: ");
        in.nextLine();
        String managerPass = in.nextLine();
        managerInfo.add(managerPass);

        System.out.println("Enter Email: ");
        String managerEmail = in.next();
        managerInfo.add(managerEmail);

        System.out.println("Enter Phone: ");
        in.nextLine();
        String managerPhone = in.next();
        managerInfo.add(managerPhone);

        managerInfo.add("Not Signed yet");
        managerInfo.add("Not Signed yet");

        System.out.println("Enter Status: ");
        int managerStatus = in.nextInt();
        if (managerStatus == 0 || managerStatus == 1) {
            managerInfo.add(managerStatus);
        } else {
            System.out.println("Manager Status must be either 0 or 1 !");
            return;
        }

        addNewToJson(String.valueOf(managerId), managerInfo);
        System.out.println("The manager has been added successfully");
    }

    

    protected void updater(int index, String id, String value) {
        JsonObject mergedJsonObject = null;
        JsonArrayBuilder newJsonArray = Json.createArrayBuilder();
        JsonArray array = jsonObject.getJsonArray(id);
        for (int i = 0; i < array.size(); i++) {
            if (i == index) {
                newJsonArray.add(value);
            } else {
                newJsonArray.add(array.get(i));
            }
        }
        mergedJsonObject = Json.createObjectBuilder(jsonObject)
                .add(id, newJsonArray)
                .build();

        jsonWriter(mergedJsonObject);

    }

}
