/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import javax.json.JsonArrayBuilder;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParsingException;

/**
 *
 * @author ASUS ROG
 */
public class Admin extends AuthSystem {

    private static Scanner in;
    private static int managerCount;
    private static int employeeCount;
    private static String jsonData = null;
    private static JsonObject jsonObject = null;

    public Admin() {
        in = new Scanner(System.in);
        this.jsonReader("managers.json");

    }

    private void jsonReader(String filePath) {
        try {
            jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Error occurred while trying to read managers.json");
        }
        try {
            jsonObject = Json.createReader(new StringReader(jsonData)).readObject();
        } catch (JsonParsingException e) {
        }

    }

    public void LoginAdmin() {
        if (Login()) {
            AdminInterFace();
        }

    }

    private void AdminInterFace() {
        Scanner input = new Scanner(System.in);
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
            int userChoice = input.nextInt();
            switch (userChoice) {
                case 10 -> {
                    break OUTER;
                }
                case 1 ->
                    AddManager();
                case 2, 3, 4, 5 -> {
                    if (!managerOperations(userChoice)) {
                        System.out.println("Manager Id not found, check Id and try again");
                    }
                }
                case 6 -> {
                    allReport();
                }
                case 8 -> {
                    statusManager();
                }
                default -> {
                }
            }

            jsonReader("managers.json");
        }
    }

    private boolean managerOperations(int choice) {
        try {
            String managerId = String.valueOf(fastCheckId());
            if (choice == 2) {
                UpdatePassword(managerId);
                System.out.println("Password Updated Successfully");
            } else if (choice == 3) {
                deleteFromJson(managerId);
                System.out.println("Manager has been deleted successfully");
            } else if (choice == 4) {
                searchManager(managerId);

            } else if (choice == 5) {
                managerReport(managerId);
            }
            return true;

        } catch (NullPointerException e) {
            return false;

        }

    }

    private void searchManager(String managerId) {
        JsonArray infoArray = jsonObject.getJsonArray(managerId);
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
        try {
            for (String key : jsonObject.keySet()) {
                managerReport(key);
            }
        } catch (NullPointerException e) {
            System.out.println("No managers yet :) ");
        }

    }

    private String fastCheckId() {
        /*
        Checks if manager id exists in managers.json
         */
        System.out.println("Enter Manager Id: ");
        long managerId = in.nextLong();
        for (String key : jsonObject.keySet()) {
            if (key.equals(String.valueOf(managerId))) {
                return key;
            }
        }
        return null;
    }

    private void AddManager() {
        /*
        Creats an ArrayList with manager info and adds it to managers.json
         */

        managerCount += 1;

        ArrayList managerInfo = new ArrayList();
        System.out.println("Enter Id: ");
        long managerId = in.nextLong();
        managerInfo.add(managerId);

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

    private void deleteFromJson(String managerId) {
        JsonObjectBuilder objBuilder = Json.createObjectBuilder(jsonObject);
        objBuilder.remove(managerId);
        JsonObject updateJsonObject = objBuilder.build();
        jsonWriter(updateJsonObject);
    }

    private void addNewToJson(String managerId, ArrayList someList) {
        JsonArrayBuilder jsonArray = jsonArrayCreator(someList);
        if (jsonData != null && !jsonData.isEmpty()) {
            JsonObject mergedJsonObject = Json.createObjectBuilder(jsonObject)
                    .add(managerId, jsonArray)
                    .build();
            jsonObject = mergedJsonObject;
//            System.out.println("Merged 8213721");
//            System.out.println(jsonObject);
        } else {
            jsonObject = Json.createObjectBuilder()
                    .add(managerId, jsonArray)
                    .build();
        }

        jsonWriter(jsonObject);

    }

    private void jsonWriter(JsonObject obj) {
        try {
            Map<String, Boolean> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(config);
            FileWriter fileWriter = new FileWriter("managers.json");
            JsonWriter jsonWriter = writerFactory.createWriter(fileWriter);
            jsonWriter.writeObject(obj);
            jsonWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred while trying to write object");
        }
    }

    private JsonArrayBuilder jsonArrayCreator(ArrayList someList) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (Object o : someList.subList(1, someList.size())) {
            if (o instanceof Long) {
                jsonArray.add((Long) o);
            } else if (o instanceof String) {
                jsonArray.add((String) o);
            } else if (o instanceof Integer) {
                jsonArray.add((Integer) o);
            } else if (o instanceof Double) {
                jsonArray.add((Double) o);
            }
        }

        return jsonArray;
    }

    private void statusManager() {
        while (true) {
            System.out.println("""
            >>>>> Activate & Deactivate >>>>>
            -1 Activate
            -2 Deactivate
            -3 Exit
                               """);

            int choice = in.nextInt();
            if (choice == 1 || choice == 2) {
                int proccess = choice == 2 ? 0 : 1;
                String message = proccess == 1 ? ">>>>> Activate Manager >>>>>" : ">>>>> Deactivate Manager >>>>>";
                System.out.println(message);
                String managerId = String.valueOf(fastCheckId());
                JsonObject mergedObj = null;
                JsonArrayBuilder newArray = Json.createArrayBuilder();
                JsonArray array = jsonObject.getJsonArray(managerId);
                for (int i = 0; i < array.size(); i++) {
                    if (!(i == array.size() - 1)) {
                        newArray.add(array.get(i));
                    } else {
                        newArray.add(Json.createValue(proccess));
                    }
                }
                mergedObj = Json.createObjectBuilder(jsonObject)
                        .add(managerId, newArray)
                        .build();

                jsonWriter(mergedObj);
                String result = proccess == 1 ? "The Account has been Activated" : "The Account has been disabled";
                System.out.println(result);
                break;

            } else {
                break;
            }
        }

    }

    private void UpdatePassword(String managerId) {
        System.out.println("Enter Password: ");
        String password = in.next();
        JsonObject mergedJsonObject = null;

        JsonArrayBuilder newJsonArray = Json.createArrayBuilder();
        JsonArray array = jsonObject.getJsonArray(managerId);
        for (int i = 0; i < array.size(); i++) {
            if (i == 1) {
                newJsonArray.add(password);
            } else {
                newJsonArray.add(array.get(i));
            }
        }
        mergedJsonObject = Json.createObjectBuilder(jsonObject)
                .add(managerId, newJsonArray)
                .build();

        jsonWriter(mergedJsonObject);

    }

}
