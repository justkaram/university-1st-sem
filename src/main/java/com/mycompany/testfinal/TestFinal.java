/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.testfinal;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author ASUS ROG
 */
public class TestFinal {

    public static void main(String[] args) {
        go();
    }

    public static void go() {
        OUTER:
        while (true) {
            Scanner in = new Scanner(System.in);

            System.out.println("""
                                           >>>>> Login Screen <<<<<
                                           -1 Login as Admin
                                           -2 Login as Manager
                                           -3 Login as Employee
                                           -4 About System
                                           -5 Exit
                                           """);
            try {
                int choice = in.nextInt();
                switch (choice) {
                    case 5 -> {
                        break OUTER;
                    }
                    case 1 -> {
                        Admin admin = new Admin();
                        admin.loginAdmin();
                    }
                    case 2 -> {
                        Manager manager = new Manager();
                    }
                    case 3 -> {
                        Employee employee = new Employee();
                    }
                    case 4 -> {
                        System.out.println("""
                                           - This system has been developed by Karam Hilles & Mohammed shoblaq
                                           - This system still needs some improvements 
                                           - Repo Link: https://github.com/justkaram/university-1st-sem 
                                           """);
                        
                    }
                    default ->
                        System.out.println("Invalid Choice");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input Must be an integer");
            }
        }

    }

}
