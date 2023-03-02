package lt.code.academy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.Scanner;

public class AppForBank {
    private final MongoCollection<User> userCollection;
    private User user;
    public AppForBank(MongoCollection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public static void main(String[] args) {


        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("LBA");
        AppForBank application = new AppForBank(database.getCollection("users", User.class));
        Scanner scanner = new Scanner(System.in);

        application.login(scanner);

        String action;
        do {
            application.menu();
            action = scanner.nextLine();
            application.userSelection(scanner, action);
        } while (!action.equals("7"));
    }

    private void login(Scanner sc) {
        do {
            System.out.println("Prasome prisijungti");

            System.out.println("Iveskite varda:");
            String name = sc.nextLine();

            System.out.println("Iveskite pavarde:");
            String surname = sc.nextLine();

            user = userCollection.find(and(eq("name", name), eq("surname", surname))).first();
            if (user == null) {
                System.out.println("Tokio userio nera.");
            }

        } while (user == null);
    }

    private void userSelection(Scanner scanner, String action) {
        switch (action) {
            case "1" -> System.out.println("1");
            case "2" -> System.out.println("2");
            case "3" -> System.out.println("3");
            case "4" -> System.out.println("Programa baigia darba");
            default -> System.out.println("Tokio veiksmo nera");
        }
    }


    }
    private void menu() {
        System.out.println("""
                1. Registracija
                2. Atlikti pervedima
                3. Perziureti saskaitos likuti
                4. Iseiti
                """);
    }

    private void createUsers() {
        List<User> users = List.of(new User(null, "Viktoras", "Cedovicius", 112233, 300),
               new User(null, "Petras", "Petraitis", 001122, 400),
                new User(null, "Jonas", "Jonaitis", 223344, 600),
                new User(null, "Ona", "Onutiene", 778899, 500),
                new User(null, "Angele", "Angelaite", 665544, 800));

        userCollection.insertMany(users);
    }
}

