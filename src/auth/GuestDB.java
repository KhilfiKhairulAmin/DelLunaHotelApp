package auth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GuestDB {
    private static final String FILE_PATH = "src/data/guests.txt";
    private static final String ID_PREFIX = "G";
    private static int nextId = 1;

    static {
        initializeFile();
        loadLastId();
    }

    private static void initializeFile() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Error initializing guests file: " + e.getMessage());
        }
    }

    private static void loadLastId() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            if (!lines.isEmpty()) {
                String lastLine = lines.get(lines.size() - 1);
                if (!lastLine.trim().isEmpty()) {
                    String lastId = lastLine.split(",")[0];
                    nextId = Integer.parseInt(lastId.substring(ID_PREFIX.length())) + 1;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading last ID: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Error parsing ID: " + e.getMessage());
        }
    }

    public static String generateNextId() {
        String newId = ID_PREFIX + String.format("%03d", nextId);
        nextId++;
        return newId;
    }

    // Add new guest with password hashing
    public static void addGuest(Guest guest, String password) throws IOException {
        if (guest.getGuestId() == null || guest.getGuestId().isEmpty()) {
            guest.setGuestId(generateNextId());
        }
        
        String passwordHash = hashPassword(password);
        
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(FILE_PATH), 
                StandardOpenOption.APPEND, 
                StandardOpenOption.CREATE)) {
            writer.write(guestToFileString(guest, passwordHash));
            writer.newLine();
        }
    }

    // Password hashing utility
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing algorithm not available", e);
        }
    }

    // Get all guests (without password hashes)
    public static List<Guest> getAllGuests() throws IOException {
        List<Guest> guests = new ArrayList<>();
        
        if (Files.notExists(Paths.get(FILE_PATH)) || Files.size(Paths.get(FILE_PATH)) == 0) {
            return guests;
        }

        for (String line : Files.readAllLines(Paths.get(FILE_PATH))) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Now expecting 5 parts with password hash
                    guests.add(new Guest(
                        parts[0].trim(), 
                        parts[1].trim(), 
                        parts[2].trim(), 
                        parts[3].trim()));
                }
            }
        }
        return guests;
    }

    // Verify guest credentials
    public static boolean verifyGuest(String email, String password) throws IOException {
        if (Files.notExists(Paths.get(FILE_PATH))) {
            return false;
        }

        String passwordHash = hashPassword(password);
        
        for (String line : Files.readAllLines(Paths.get(FILE_PATH))) {
            String[] parts = line.split(",");
            if (parts.length >= 5 && 
                parts[2].trim().equalsIgnoreCase(email) && 
                parts[4].trim().equals(passwordHash)) {
                return true;
            }
        }
        return false;
    }

    // Update guest password
    public static void updatePassword(String guestId, String newPassword) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        boolean updated = false;
        
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length >= 5 && parts[0].trim().equals(guestId)) {
                String newHash = hashPassword(newPassword);
                parts[4] = newHash;
                lines.set(i, String.join(",", parts));
                updated = true;
                break;
            }
        }
        
        if (updated) {
            Files.write(Paths.get(FILE_PATH), lines);
        } else {
            throw new IOException("Guest not found with ID: " + guestId);
        }
    }

    // [Rest of the methods remain similar but updated for password field...]
    private static String guestToFileString(Guest guest, String passwordHash) {
        return String.join(",",
                guest.getGuestId(),
                guest.getName(),
                guest.getEmail(),
                guest.getPhone(),
                passwordHash);
    }

    private static Guest parseGuestFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid guest data format: " + line);
        }
        return new Guest(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
    }
}