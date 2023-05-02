/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import javax.json.JsonObject;

/**
 *
 * @author ASUS ROG
 */
public class Manager extends Admin {

    private static String jsonData = null;
    private static JsonObject jsonObject = null;

    public Manager() {

    }
    
    public void Test(){
        for (String key : jsonObject.keySet()){
            System.out.println(jsonObject.getJsonArray(key));
        }
    }

}
