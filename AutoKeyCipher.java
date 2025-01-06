import java.util.Scanner;

public class AutoKeyCipher {
    public static String encrypt(String plaintext, String keyword) {
        StringBuilder key = new StringBuilder(keyword); 
        key.append(plaintext); 
        key.setLength(plaintext.length());

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = key.charAt(i);

            
            char cipherChar = (char) (((plainChar - 'A' + keyChar - 'A') % 26) + 'A');
            ciphertext.append(cipherChar);
        }
        return ciphertext.toString();
    }

    
    public static String decrypt(String ciphertext, String keyword) {
        StringBuilder key = new StringBuilder(keyword); 
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i);

           
            char plainChar = (char) (((cipherChar - keyChar + 26) % 26) + 'A');
            plaintext.append(plainChar);

            
            key.append(plainChar);
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("AutoKey Cipher Implementation");
        System.out.print("Enter the plaintext (uppercase A-Z): ");
        String plaintext = scanner.nextLine().toUpperCase();

        System.out.print("Enter the keyword (uppercase A-Z): ");
        String keyword = scanner.nextLine().toUpperCase();

        String ciphertext = encrypt(plaintext, keyword);
        System.out.println("Encrypted Ciphertext: " + ciphertext);

        
        String decryptedText = decrypt(ciphertext, keyword);
        System.out.println("Decrypted Plaintext: " + decryptedText);

        scanner.close();
    }
}
