/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import javax.json.JsonArray;
import javax.json.JsonString;

/**
 *
 * @author ASUS ROG
 */
public class Manager extends Admin {

    public Manager() {
        super("employee.json");
        if (!Login()) {
            System.out.println("User Id is not found or wrong pass");
        }
    }

    @Override
    public boolean login() {

        System.out.println("Enter ID: ");
        String user = in.next();
        JsonArray array = jsonObject.getJsonArray(user);
        System.out.println(array);
        System.out.println("Enter Password: ");
        String pass = in.next();
        if (array == null) {
            return false;
        } else {
            try {
                if (((JsonString) array.get(1)).getString().equals(pass)) {
                    return true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;

    }

    public void Test() {
        for (String key : jsonObject.keySet()) {
            System.out.println(jsonObject.getJsonArray(key));
        }
    }

}
