import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Class that provides methods for encrypting and decrypting text using Blowfish encryption algorithm
 */
public class EncrypterDecrypter {
    SecretKey secretKey;     // The secret key for encryption and decryption
    final int keySize = 384; // The size of the secret key


    /** 
     *  Default construcot that generates a random secret key for encryption and decryption
     */
    public EncrypterDecrypter(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("Blowfish");
            keyGen.init(keySize); // for example
            secretKey = keyGen.generateKey();
        } 
        catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    /**
     * Method that encrypts the given text using Blowfish encryption algorithm
     * @param text The text to be encrypted
     * @return The encrypted text in string format
     */
    public String encrypt(String text) {
        byte[] encrypted = new byte[] {};
        try {
            Cipher blowfish = Cipher.getInstance("Blowfish");
            blowfish.init(Cipher.ENCRYPT_MODE, secretKey);
            encrypted = blowfish.doFinal(text.getBytes());
        } 
        catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String output = Base64.getEncoder().encodeToString(encrypted);
        return output;
    }

    /**
     * Method that decrypts the given text using Blowfish encryption algorithm
     * @param text The text to be decrypted
     * @return The decrypted text in string format
     */
    public String decrypt(String text) {
                byte[] decrypted = new byte[] {};
                try {
                    Cipher blowfish = Cipher.getInstance("Blowfish");
                    blowfish.init(Cipher.DECRYPT_MODE, secretKey);
                    byte[] decodedText = Base64.getDecoder().decode(text);
                    decrypted = blowfish.doFinal(decodedText);
                } 
                catch (InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException
                        | BadPaddingException e) {
                    System.out.println("Invalid decryption key");
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                String output = new String(decrypted);
        return output;
    }

    /**
     * Method that returns the secret key in string format
     * @return The secret key in string format
     */
    public String getKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Method that sets the secret key from the given string
     * @param key The secret key in string format
     */
    public void setKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        secretKey = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "Blowfish");
    }
}