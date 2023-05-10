/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParsingException;

/**
 *
 * @author ASUS ROG
 */
public class AuthSystem {

    private final String userName = "120220562";
    private final String passWord = "123456";
    public static Scanner in = new Scanner(System.in);
    public static String jsonData = null;
    public static JsonObject jsonObject = null;
    protected static String filePath;

    public AuthSystem(String filePath) {
        AuthSystem.filePath = filePath;
        jsonReader();
    }

    public boolean login() {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter ID: ");
            String user = in.nextLine();
            System.out.println("Enter Password: ");
            String pass = in.nextLine();

            boolean check = this.userName.equals(user) && this.passWord.equals(pass);
            if (check) {
                System.out.println("Logged In Successfully");
                return true;
            } else {
                System.out.println("Failed To Login");
                return false;
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void jsonReader() {
        try {
            jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println(e.getClass());
            System.out.println("Error occurred while trying to read managers.json");
        }

        try {
            jsonObject = Json.createReader(new StringReader(jsonData)).readObject();
        } catch (JsonParsingException e) {
            jsonObject = Json.createObjectBuilder().build();
        }

    }

    public void switchFile(String fileName) {
        filePath = fileName;
        jsonReader();
    }

    public String fastCheckId(String Id) {
        /*
        Checks if manager id exists in managers.json
         */
        try {
            for (String key : jsonObject.keySet()) {
                if (key.equals(Id)) {
                    return key;
                }
            }
            return "";
        } catch (NullPointerException e) {
            return "";
        }

    }

    public void addNewToJson(String Id, ArrayList someList) {
        JsonArray jsonArray = jsonArrayCreator(someList);
        if (jsonData != null && !jsonData.isEmpty()) {
            JsonObject mergedJsonObject = Json.createObjectBuilder(jsonObject)
                    .add(Id, jsonArray)
                    .build();
            jsonObject = mergedJsonObject;
        } else {
            jsonObject = Json.createObjectBuilder()
                    .add(Id, jsonArray)
                    .build();
        }

        jsonWriter(jsonObject);

    }

    public void deleteFromJson(String Id) {
        JsonObjectBuilder objBuilder = Json.createObjectBuilder(jsonObject);
        objBuilder.remove(Id);
        JsonObject updateJsonObject = objBuilder.build();
        jsonWriter(updateJsonObject);
    }

    public void jsonWriter(JsonObject obj) {
        try {
            Map<String, Boolean> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(config);
            FileWriter fileWriter = new FileWriter(filePath);
            JsonWriter jsonWriter = writerFactory.createWriter(fileWriter);
            jsonWriter.writeObject(obj);
            jsonWriter.close();
        } catch (IOException e) {
            System.out.println("Error Occurred while trying to write object");
        }
    }

    protected void updaterHoliday(String id, ArrayList newList) {
        JsonObject mergedJsonObject = null;
        JsonArrayBuilder allJsonArrays = Json.createArrayBuilder();
        JsonArray arrayAll = jsonObject.getJsonArray(id);
        for (int i = 0; i < arrayAll.size(); i++) {
            JsonArray singleArray = arrayAll.getJsonArray(i);
            JsonArrayBuilder newJsonArray = Json.createArrayBuilder();
            for (int j = 0; j < singleArray.size(); j++) {
                newJsonArray.add(singleArray.get(j));
            }
            allJsonArrays.add(newJsonArray);
        }

        allJsonArrays.add(jsonArrayCreator(newList));
        mergedJsonObject = Json.createObjectBuilder(jsonObject)
                .add(id, allJsonArrays)
                .build();

        jsonObject = mergedJsonObject;
        jsonWriter(mergedJsonObject);

    }

    public JsonArray jsonArrayCreator(ArrayList someList) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        for (Object o : someList) {
            if (o instanceof String) {
                jsonArray.add((String) o);
            } else if (o instanceof Integer) {
                jsonArray.add((Integer) o);
            }
        }
        JsonArray check = jsonArray.build();
        return check;
    }

    protected void statusUpdater() {
        while (true) {
            System.out.println("""
            >>>>> Activate & Deactivate >>>>>
            -1 Activate
            -2 Deactivate
            -3 Exit
                               """);

            int choice = in.nextInt();
            if (choice == 3) {
                break;
            } else if (choice == 1 || choice == 2) {
                String user = "";
                int proccess = choice == 2 ? 0 : 1;
                if (filePath.equals("managers.json")) {
                    user = "Manager";
                } else if (filePath.equals("employee.json")) {
                    user = "Employee";
                }
                String message = proccess == 1 ? ">>>>> Activate " + user + " >>>>>" : ">>>>> Deactivate " + user + " >>>>>";
                System.out.println(message);
                System.out.println("Enter Id: ");
                String id = fastCheckId(in.next());
                if (id.equals("")) {
                    System.out.println(user + " is Not Found !!");
                    return;
                }
                JsonObject mergedObj = null;
                JsonArray array = jsonObject.getJsonArray(id);
                if (array == null) {
                    System.out.println("array is empty");

                }
                int status = 0;
                try {
                    status = ((JsonNumber) array.get(array.size() - 1)).intValue();
                } catch (NullPointerException e) {
                    System.out.println("No Users Yet !");
                }

                if (status == 1 && proccess == 1) {
                    System.out.println("Account is already activated.");
                    return;
                } else if (status == 0 && proccess == 0) {
                    System.out.println("Account is already deactivated.");
                    return;
                }
                JsonArrayBuilder newArray = Json.createArrayBuilder();
                for (int i = 0; i < array.size(); i++) {
                    if (!(i == array.size() - 1)) {
                        newArray.add(array.get(i));
                    } else {
                        newArray.add(Json.createValue(proccess));
                    }
                }
                mergedObj = Json.createObjectBuilder(jsonObject)
                        .add(id, newArray)
                        .build();

                jsonWriter(mergedObj);
                String result = proccess == 1 ? "The Account has been Activated" : "The Account has been disabled";
                System.out.println(result);
                break;

            } else {
                System.out.println("Wrong Input !!");
                statusUpdater();
            }
        }

    }

    public void holidaysViewer(String managerId) {
        System.out.println(">>>>> My Holidays <<<<<");
        JsonArray holidays = jsonObject.getJsonArray(managerId);
        if (holidays.isEmpty()) {
            System.out.println("You don't have any holidays requests yet !");
            return;
        }
        for (Object o : holidays) {
            JsonArray singleHoliday = (JsonArray) o;
            String name = ((JsonString) singleHoliday.get(0)).getString();
            String reason = ((JsonString) singleHoliday.get(1)).getString();
            String details = ((JsonString) singleHoliday.get(2)).getString();
            String date = ((JsonString) singleHoliday.get(3)).getString();
            int checked = ((JsonNumber) singleHoliday.get(4)).intValue();
            String sep = " || ";
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ")
                    .append(name)
                    .append(sep)
                    .append("Reason: ")
                    .append(reason)
                    .append(sep)
                    .append("Details: ")
                    .append(details)
                    .append(sep)
                    .append("Date: ")
                    .append(date)
                    .append(sep)
                    .append("Checked: ")
                    .append(checked);
            System.out.println(sb);
        }

    }

}
