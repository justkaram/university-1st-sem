/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import java.io.FileWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author ASUS ROG
 */
public class TestJson {

    public static void main(String args[]) {
        try {
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("name", "John")
                    .add("age", 30)
                    .build();
            FileWriter fileWriter = new FileWriter("file.json");
            JsonWriter jsonWriter = Json.createWriter(fileWriter);
            jsonWriter.writeObject(jsonObject);
            jsonWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
