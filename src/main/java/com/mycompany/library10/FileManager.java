package com.mycompany.library10;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // Cargar usuarios desde el archivo "usuarios.txt"
    public static List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {  // Verifica que el formato sea correcto
                    String id = parts[0];
                    String docNumber = parts[1];
                    String docType = parts[2];
                    String name = parts[3];
                    String password = parts[4];
                    String role = parts[5].toUpperCase();

                    User user;
                    switch (role) {
                        case "ADMIN" -> user = new Admin(id, docNumber, docType, name, password);
                        case "TEACHER" -> user = new Teacher(id, docNumber, docType, name, password);
                        case "STUDENT" -> user = new Student(id, docNumber, docType, name, password);
                        default -> throw new IllegalArgumentException("Invalid role: " + role);
                    }
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Guardar usuarios en el archivo "usuarios.txt"
    public static void saveUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (User user : users) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s", 
                    user.id, user.documentNumber, user.documentType, user.name, 
                    user.password, user.getClass().getSimpleName().toUpperCase()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Cargar materiales desde el archivo "materiales.txt"
    public static List<Material> loadMaterialsFromFile() {
        List<Material> materials = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("materiales.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {  // Verifica que el formato sea correcto
                    String code = parts[0];
                    String type = parts[1];
                    String name = parts[2];
                    String author = parts[3];
                    int quantity = Integer.parseInt(parts[4]);
                    materials.add(new Material(code, type, name, author, quantity));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading materials: " + e.getMessage());
        }
        return materials;
    }

    // Guardar materiales en el archivo "materiales.txt"
    public static void saveMaterialsToFile(List<Material> materials) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("materiales.txt"))) {
            for (Material material : materials) {
                writer.write(String.format("%s,%s,%s,%s,%d", 
                    material.code, material.type, material.name, 
                    material.author, material.quantity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving materials: " + e.getMessage());
        }
    }

    // Cargar préstamos desde el archivo "prestamos.txt"
    public static List<Loan> loadLoansFromFile() {
        List<Loan> loans = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("prestamos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {  // Verifica que el formato sea correcto
                    String userId = parts[0];
                    String materialCode = parts[1];
                    String loanDate = parts[2];
                    String returnDate = parts[3];
                    loans.add(new Loan(userId, materialCode, loanDate, returnDate));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading loans: " + e.getMessage());
        }
        return loans;
    }

    // Guardar préstamos en el archivo "prestamos.txt"
    public static void saveLoansToFile(List<Loan> loans) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prestamos.txt"))) {
            for (Loan loan : loans) {
                writer.write(String.format("%s,%s,%s,%s", 
                    loan.userId, loan.materialCode, loan.loanDate, loan.returnDate));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving loans: " + e.getMessage());
        }
    }
}
