package com.mycompany.library10;

import java.util.List;
import java.util.Scanner;

public class Library10 {
    public static void main(String[] args) {
        //call TXT.
        Scanner scanner = new Scanner(System.in);
        List<User> users = FileManager.loadUsersFromFile();
        List<Material> materials = FileManager.loadMaterialsFromFile();
        List<Loan> loans = FileManager.loadLoansFromFile();

        // call menú pp.
        new Menu(scanner, users, materials, loans).start();
    }
}
