import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ta in användarinmatning
        System.out.print("Ange namn: ");
        String name = scanner.nextLine();

        System.out.print("Ange lösenord: ");
        String password = scanner.nextLine();

        System.out.print("Ange e-post: ");
        String email = scanner.nextLine();

        System.out.print("Ange telefonnummer: ");
        String phone = scanner.nextLine();

        // Skapa ett UserRegistration-objekt och spara användarinformationen
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.saveToFile(name, password, email, phone);

        // Försök att läsa från en tillåten fil
        System.out.print("Ange filnamn att läsa från (prova att ange 'users.txt' eller en annan fil): ");
        String fileName = scanner.nextLine();
        userRegistration.readFromFile(fileName);
    }
}
