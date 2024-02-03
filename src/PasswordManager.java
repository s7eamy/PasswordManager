import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class PasswordManager {
    static String key = "";
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        String name;
        String password;
        while(true){
            userInterface.displayMenu();
            switch(userInterface.getChoice()) {
                case 1:
                    name = userInterface.getName();
                    password = userInterface.getPassword();
                    addPassword(name, password);
                    break;
                case 2:
                    name = userInterface.getName();
                    System.out.println(getPassword(name));
                    break;
                case 3:
                    key = userInterface.getKey();
                    break;
                case 4:
                    name = userInterface.getName();
                    deletePassword(name);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            
        }
    }

    
    /**
     * Method that deletes the password with the given name
     * @param name Name of the password to be deleted
     */
    private static void deletePassword(String name) {
        try {
            Scanner scanner = new Scanner(Paths.get("passwords.csv"));
            String fileContent = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (!parts[0].equals(name)) { // skip the line that needs to be deleted
                    fileContent += line + "\n";
                }
            }
            scanner.close();
            FileWriter writer = new FileWriter("passwords.csv");
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void addPassword(String name, String password) {
        EncrypterDecrypter encrypterDecrypter = new EncrypterDecrypter();
        if(!key.equals("")) { // if key was defined previously
            encrypterDecrypter.setKey(key);
        }
        String encryptedPassword = encrypterDecrypter.encrypt(password);
        try {
            FileWriter writer = new FileWriter("passwords.csv", true);
            writer.write(name + ";" + encryptedPassword + "\n");
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getPassword(String name) {
        Scanner scanner;
        String encryptedPassword = "";
        try {
            scanner = new Scanner(Paths.get("passwords.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                if (parts[0].equals(name)) {
                    encryptedPassword = parts[1];
                }
            }
            scanner.close();
            if(encryptedPassword.equals("")){
                return "Password not found";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        EncrypterDecrypter encrypterDecrypter = new EncrypterDecrypter();
        if(!key.equals("")) {
            encrypterDecrypter.setKey(key);
        }
        return encrypterDecrypter.decrypt(encryptedPassword);
    }
}
