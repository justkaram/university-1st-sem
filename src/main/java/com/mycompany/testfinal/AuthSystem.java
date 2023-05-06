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
            System.out.println("Error occurred while trying to read managers.json");
        }
        try {
            jsonObject = Json.createReader(new StringReader(jsonData)).readObject();
        } catch (JsonParsingException e) {
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
        JsonArrayBuilder jsonArray = jsonArrayCreator(someList);
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

    public JsonArrayBuilder jsonArrayCreator(ArrayList someList) {
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

}
