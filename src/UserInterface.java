/**
 * Class that provides methods for taking user input
 */
public class UserInterface {
    public void displayMenu() {
        System.err.println(new String("*").repeat(80));
        System.err.println("Password Manager");
        System.out.println("1. Add a password");
        System.out.println("2. Get a password");
        System.out.println("3. Set encryption/decryption key");
        System.out.println("4. Delete a password");
        System.out.println("5. Exit");
    }

    public int getChoice() {
        System.out.println("Enter your choice: ");
        return Integer.parseInt(System.console().readLine());
    }

    public String getName() {
        System.out.println("Enter the name: ");
        return System.console().readLine();
    }

    public String getPassword() {
        System.out.println("Enter the password: ");
        return new String(System.console().readPassword());
    }

    public String getKey() {
        System.out.println("Enter the key: ");
        return System.console().readLine();
    }
}
