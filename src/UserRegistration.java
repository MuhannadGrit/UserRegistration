import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Paths;

public class UserRegistration {

    private static final String BASE_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/src/Files";

    // Metod för att hasha lösenord med SHA-256
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Kontrollera om filen ligger inom den tillåtna katalogen
    private File getSecureFile(String fileName) throws IOException {
        File file = new File(BASE_DIRECTORY, fileName);
        String canonicalBase = new File(BASE_DIRECTORY).getCanonicalPath();
        String canonicalFile = file.getCanonicalPath();

        // Kontrollera att filen ligger inom den tillåtna katalogen
        if (!canonicalFile.startsWith(canonicalBase)) {
            throw new SecurityException("Otillåten filåtkomst.");
        }
        return file;
    }

    // Metod för att spara användardata till en säker fil
    public void saveToFile(String name, String password, String email, String phone) {
        String hashedPassword = hashPassword(password);  // Hasha lösenordet
        try {
            // Använd säker filåtkomst
            File file = getSecureFile("users.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write("Namn: " + name);
                writer.newLine();
                writer.write("Hashat lösenord: " + hashedPassword);
                writer.newLine();
                writer.write("E-post: " + email);
                writer.newLine();
                writer.write("Telefonnummer: " + phone);
                writer.newLine();
                writer.write("----------------------------");
                writer.newLine();
                System.out.println("Användardata har sparats.");
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    // Metod för att läsa användardata från en säker fil
    public void readFromFile(String fileName) {
        try {
            // Använd säker filåtkomst
            File file = getSecureFile(fileName);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                System.out.println("\nAnvändarinformation sparad i filen:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException | SecurityException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }
}
