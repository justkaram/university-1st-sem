/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testfinal;

import java.util.Scanner;

/**
 *
 * @author ASUS ROG
 */
public class AuthSystem {

    private final String userName = "120220562";
    private final String passWord = "123456";

    public boolean Login() {
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


}
