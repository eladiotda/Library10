package com.mycompany.library10;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final Scanner scanner;
    private final List<User> users;
    private final List<Material> materials;
    private final List<Loan> loans;

    public AdminMenu(Scanner scanner, List<User> users, List<Material> materials, List<Loan> loans) {
        this.scanner = scanner;
        this.users = users;
        this.materials = materials;
        this.loans = loans;
    }

    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View Materials");
            System.out.println("2. Add Material");
            System.out.println("3. Modify Material");
            System.out.println("4. Add User");
            System.out.println("5. Loan Material");
            System.out.println("6. Return Material");
            System.out.println("7. View Loans");
            System.out.println("8. View Users");
            System.out.println("9. Delete Material");
            System.out.println("10. Exit");

            int choice = getValidIntInput("Select an option: ");
            exit = handleChoice(choice);
        }
    }

    private boolean handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> viewMaterials();
                case 2 -> addMaterial();
                case 3 -> modifyMaterial();
                case 4 -> addUser();
                case 5 -> loanMaterial();
                case 6 -> returnMaterial();
                case 7 -> viewLoans();
                case 8 -> viewUsers();
                case 9 -> deleteMaterial();
                case 10 -> {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return false;
    }

    private int getValidIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void viewMaterials() {
        System.out.println("\n--- Materials ---");
        materials.forEach(System.out::println);
    }

    private void addMaterial() {
        System.out.print("Enter Material Code: ");
        String code = scanner.nextLine().toUpperCase();

        Material existingMaterial = findMaterialByCode(code);

        if (existingMaterial != null) {
            int quantity = getValidIntInput("Enter quantity to add: ");
            existingMaterial.quantity += quantity;
            System.out.println("Material quantity updated successfully.");
        } else {
            String type = selectMaterialType();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().toUpperCase();
            System.out.print("Enter Author: ");
            String author = scanner.nextLine().toUpperCase();
            int quantity = getValidIntInput("Enter Quantity: ");

            materials.add(new Material(code, type, name, author, quantity));
            System.out.println("New material added successfully.");
        }
        FileManager.saveMaterialsToFile(materials);
    }

    private String selectMaterialType() {
        while (true) {
            System.out.println("\nSelect Material Type:");
            System.out.println("1. Book");
            System.out.println("2. Magazine");
            System.out.println("3. Digital");
            System.out.println("4. Web");

            int typeChoice = getValidIntInput("Choose a type (1-4): ");
            switch (typeChoice) {
                case 1 -> { return "BOOK"; }
                case 2 -> { return "MAGAZINE"; }
                case 3 -> { return "DIGITAL"; }
                case 4 -> { return "WEB"; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void modifyMaterial() {
        System.out.print("Enter Material Code to Modify: ");
        String code = scanner.nextLine().toUpperCase();
        Material material = findMaterialByCode(code);

        if (material != null) {
            material.type = selectMaterialType();
            System.out.print("Enter New Name: ");
            material.name = scanner.nextLine().toUpperCase();
            System.out.print("Enter New Author: ");
            material.author = scanner.nextLine().toUpperCase();
            material.quantity = getValidIntInput("Enter New Quantity: ");
            FileManager.saveMaterialsToFile(materials);
            System.out.println("Material modified successfully.");
        } else {
            System.out.println("Material not found.");
        }
    }

    private void addUser() {
        String id;
        String docNum;
        while (true) {
            System.out.print("Enter User ID: ");
            id = scanner.nextLine().toUpperCase();
            if (findUserById(id) == null) break;
            System.out.println("ID already exists. Please enter a unique ID.");
        }

        while (true) {
            System.out.print("Enter Document Number: ");
            docNum = scanner.nextLine().toUpperCase();
            if (findUserByDocumentNumber(docNum) == null) break;
            System.out.println("Document number already exists. Please enter a unique document number.");
        }

        String docType = selectDocumentType();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().toUpperCase();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User newUser = selectUserRole(id, docNum, docType, name, password);

        users.add(newUser);
        FileManager.saveUsersToFile(users);
        System.out.println("User added successfully.");
    }

    private String selectDocumentType() {
        while (true) {
            System.out.println("Select Document Type: ");
            System.out.println("1. CC (Citizenship Card)");
            System.out.println("2. TI (Identity Card)");
            System.out.println("3. RC (Civil Registry)");
            int choice = getValidIntInput("Choose a type (1-3): ");
            switch (choice) {
                case 1 -> { return "CC"; }
                case 2 -> { return "TI"; }
                case 3 -> { return "RC"; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private User selectUserRole(String id, String docNum, String docType, String name, String password) {
        while (true) {
            System.out.println("Select Role: 1. Admin 2. Teacher 3. Student");
            int roleChoice = getValidIntInput("Select a role: ");

            switch (roleChoice) {
                case 1 -> { return new Admin(id, docNum, docType, name, password); }
                case 2 -> { return new Teacher(id, docNum, docType, name, password); }
                case 3 -> { return new Student(id, docNum, docType, name, password); }
                default -> System.out.println("Invalid role selection. Try again.");
            }
        }
    }

    private void loanMaterial() {
        System.out.print("Enter User Document: ");
        String doc = scanner.nextLine().toUpperCase();
        System.out.print("Enter Material Code: ");
        String code = scanner.nextLine().toUpperCase();

        User user = findUserByDocumentNumber(doc);
        Material material = findMaterialByCode(code);

        if (user != null && material != null && material.quantity > 0) {
            boolean hasOverdueLoan = loans.stream().anyMatch(loan ->
                loan.userId.equals(user.id) && LocalDate.parse(loan.returnDate).isBefore(LocalDate.now())
            );
            boolean hasSameMaterialLoan = loans.stream().anyMatch(loan ->
                loan.userId.equals(user.id) && loan.materialCode.equals(code)
            );

            if (hasOverdueLoan) {
                System.out.println("User has overdue loans and cannot borrow more materials.");
                return;
            }
            if (hasSameMaterialLoan) {
                System.out.println("User already has a loan for this material.");
                return;
            }

            loans.add(new Loan(user.id, code, LocalDate.now().toString(), LocalDate.now().plusDays(8).toString()));
            material.quantity--;
            FileManager.saveLoansToFile(loans);
            FileManager.saveMaterialsToFile(materials);
            System.out.println("Loan registered successfully.");
        } else {
            System.out.println("Loan failed. Check user, material, or availability.");
        }
    }

    private void returnMaterial() {
        System.out.print("Enter Material Code: ");
        String code = scanner.nextLine().toUpperCase();
        System.out.print("Enter User Document: ");
        String doc = scanner.nextLine().toUpperCase();

        User user = findUserByDocumentNumber(doc);
        if (user == null) {
            System.out.println("User not found. Try again.");
            return;
        }

        Loan loan = loans.stream()
                .filter(l -> l.materialCode.equals(code) && l.userId.equals(user.id))
                .findFirst()
                .orElse(null);

        if (loan != null) {
            loans.remove(loan);
            findMaterialByCode(code).quantity++;
            FileManager.saveLoansToFile(loans);
            FileManager.saveMaterialsToFile(materials);
            System.out.println("Material returned successfully.");
        } else {
            System.out.println("No such loan found.");
        }
    }

    private void viewLoans() {
        System.out.println("\n--- Loans ---");
        for (Loan loan : loans) {
            User user = findUserById(loan.userId);
            Material material = findMaterialByCode(loan.materialCode);
            if (user != null && material != null) {
                System.out.printf(
                    "Loan - User Document: %s, Material Name: %s, Material Code: %s, Loan Date: %s, Return Date: %s%n",
                    user.documentNumber, material.name, loan.materialCode, loan.loanDate, loan.returnDate
                );
            }
        }
    }

    private void viewUsers() {
        System.out.println("\n--- Users ---");
        for (User user : users) {
            String role = user.getClass().getSimpleName().toUpperCase();
            System.out.printf(
                "ID: %s, Document Number: %s, Document Type: %s, Name: %s, Password: %s, Role: %s%n",
                user.id, user.documentNumber, user.documentType, user.name, user.password, role
            );
        }
    }

    private void deleteMaterial() {
        System.out.print("Enter Material Code to Delete: ");
        String code = scanner.nextLine().toUpperCase();
        materials.removeIf(material -> material.code.equals(code));
        FileManager.saveMaterialsToFile(materials);
        System.out.println("Material deleted successfully.");
    }

    private User findUserById(String id) {
        return users.stream()
                .filter(user -> user.id.equals(id))
                .findFirst()
                .orElse(null);
    }

    private User findUserByDocumentNumber(String doc) {
        return users.stream()
                .filter(user -> user.documentNumber.equals(doc))
                .findFirst()
                .orElse(null);
    }

    private Material findMaterialByCode(String code) {
        return materials.stream()
                .filter(material -> material.code.equals(code))
                .findFirst()
                .orElse(null);
    }
}
