package com.mycompany.library10;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private final Scanner scanner;
    private final List<Material> materials;
    private final List<Loan> loans;
    private final User user;

    public StudentMenu(Scanner scanner, List<Material> materials, List<Loan> loans, User user) {
        this.scanner = scanner;
        this.materials = materials;
        this.loans = loans;
        this.user = user;
    }

    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View Materials");
            System.out.println("2. Loan Material");
            System.out.println("3. View Your Loans");
            System.out.println("4. Exit");

            int choice = getValidIntInput("Select an option: ");
            exit = handleChoice(choice);
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1 -> viewMaterials();
            case 2 -> loanMaterial();
            case 3 -> viewUserLoans();
            case 4 -> {
                System.out.println("Exiting program...");
                System.exit(0);  // Termina el programa
            }
            default -> System.out.println("Invalid option. Try again.");
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
        System.out.println("\n--- Available Materials ---");
        materials.forEach(System.out::println);
    }

    private void loanMaterial() {
        System.out.print("Enter Material Code: ");
        String code = scanner.nextLine().toUpperCase();

        Material material = findMaterialByCode(code);
        if (material == null || material.quantity <= 0) {
            System.out.println("Material not available.");
            return;
        }

        boolean hasOverdueLoan = loans.stream()
                .anyMatch(loan -> loan.userId.equals(user.id) && LocalDate.parse(loan.returnDate).isBefore(LocalDate.now()));
        boolean hasSameMaterialLoan = loans.stream()
                .anyMatch(loan -> loan.userId.equals(user.id) && loan.materialCode.equals(code));

        if (hasOverdueLoan) {
            System.out.println("You have overdue loans and cannot borrow more materials.");
            return;
        }
        if (hasSameMaterialLoan) {
            System.out.println("You already have a loan for this material.");
            return;
        }

        loans.add(new Loan(user.id, code, LocalDate.now().toString(), LocalDate.now().plusDays(8).toString()));
        material.quantity--;  // Ensure the material quantity is reduced
        FileManager.saveLoansToFile(loans);
        FileManager.saveMaterialsToFile(materials);
        System.out.println("Loan registered successfully.");
    }

    private void viewUserLoans() {
        System.out.println("\n--- Your Loans ---");
        loans.stream()
             .filter(loan -> loan.userId.equals(user.id))
             .forEach(loan -> {
                 Material material = findMaterialByCode(loan.materialCode);
                 if (material != null) {
                     System.out.printf("Material Name: %s, Material Code: %s, Loan Date: %s, Return Date: %s%n",
                             material.name, loan.materialCode, loan.loanDate, loan.returnDate);
                 }
             });
    }

    private Material findMaterialByCode(String code) {
        return materials.stream()
                .filter(material -> material.code.equals(code))
                .findFirst()
                .orElse(null);
    }
}
