/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.testfinal;

import java.util.Scanner;

/**
 *
 * @author ASUS ROG
 */
public class TestFinal {

    public static void main(String[] args) {
        OUTER:
        while (true) {
            int loginChoice = Login();
            switch (loginChoice) { 
                case 5 -> {
                    break OUTER;
                }
                case 1 -> {
                    Admin admin = new Admin();
                    admin.LoginAdmin();
                }
                case 2 -> {
                    Manager manager = new Manager();
                }
                case 3 ->{
                    Employee emp = new Employee();
                }
                default -> {
                }
            }
        }
    }

    public static int Login() {
        Scanner in = new Scanner(System.in);
        System.out.println("""
                           >>>>> Login Screen <<<<<
                           -1 Login as Admin
                           -2 Login as Manager
                           -3 Login as Employee
                           -4 About System
                           -5 Exit
                           """);
        int choice = in.nextInt();
        return choice;

    }
}
