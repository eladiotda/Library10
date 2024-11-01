package com.mycompany.library10;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final List<User> users;
    private final List<Material> materials;
    private final List<Loan> loans;

    public Menu(Scanner scanner, List<User> users, List<Material> materials, List<Loan> loans) {
        this.scanner = scanner;
        this.users = users;
        this.materials = materials;
        this.loans = loans;
    }

    public void start() {
        boolean exitProgram = false;
        while (!exitProgram) {
            User currentUser = login();
            if (currentUser != null) {
                exitProgram = delegateMenu(currentUser);
            }
        }
        System.out.println("Program has ended.");
    }

    private User login() {
        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter your document number: ");
            String documentNumber = scanner.nextLine().toUpperCase();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            for (User user : users) {
                if (user.documentNumber.equals(documentNumber) && user.password.equals(password)) {
                    System.out.println("Login successful! Welcome, " + user.name);
                    return user;
                }
            }
            attempts--;
            System.out.println("Invalid credentials. Attempts left: " + attempts);
        }
        System.out.println("Maximum login attempts reached. Exiting program.");
        System.exit(0);
        return null;
    }

    private boolean delegateMenu(User user) {
        switch (user.getClass().getSimpleName()) {
            case "Admin" -> {
                AdminMenu adminMenu = new AdminMenu(scanner, users, materials, loans);
                adminMenu.showMenu();
            }
            case "Teacher" -> {
                TeacherMenu teacherMenu = new TeacherMenu(scanner, materials, loans, users, user);
                teacherMenu.showMenu();
            }
            case "Student" -> {
                StudentMenu studentMenu = new StudentMenu(scanner, materials, loans, user);
                studentMenu.showMenu();
            }
            default -> {
                System.out.println("Unknown user role. Exiting.");
                return true;
            }
        }
        return false;
    }
}
