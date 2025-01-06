import java.util.Scanner;

public class HillCipher {
    private static int[][] keyMatrix, inverseKeyMatrix;

    public static void generateKeyMatrix(String key, int size) {
        keyMatrix = new int[size][size];
        for (int i = 0, k = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                keyMatrix[i][j] = (key.charAt(k++) - 'A') % 26;
    }

    public static int modInverse(int a, int m) {
        a %= m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1) return x;
        return 1;
    }

    public static int findDeterminant(int[][] matrix, int size) {
        return size == 2 ? (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) : 0;
    }

    public static void generateInverseKeyMatrix(int size) {
        int det = findDeterminant(keyMatrix, size) % 26;
        det = (det < 0) ? det + 26 : det;
        int detInverse = modInverse(det, 26);
        if (size == 2) {
            inverseKeyMatrix = new int[size][size];
            inverseKeyMatrix[0][0] = keyMatrix[1][1] * detInverse % 26;
            inverseKeyMatrix[1][1] = keyMatrix[0][0] * detInverse % 26;
            inverseKeyMatrix[0][1] = (-keyMatrix[0][1] * detInverse % 26 + 26) % 26;
            inverseKeyMatrix[1][0] = (-keyMatrix[1][0] * detInverse % 26 + 26) % 26;
        }
    }

    public static String encrypt(String plaintext, int size) {
        int[] vector = new int[size], cipherVector = new int[size];
        for (int i = 0; i < size; i++) vector[i] = plaintext.charAt(i) - 'A';
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                cipherVector[i] = (cipherVector[i] + keyMatrix[i][j] * vector[j]) % 26;
        StringBuilder ciphertext = new StringBuilder();
        for (int i : cipherVector) ciphertext.append((char) (i + 'A'));
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int size) {
        int[] vector = new int[size], plainVector = new int[size];
        for (int i = 0; i < size; i++) vector[i] = ciphertext.charAt(i) - 'A';
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                plainVector[i] = (plainVector[i] + inverseKeyMatrix[i][j] * vector[j]) % 26;
        StringBuilder plaintext = new StringBuilder();
        for (int i : plainVector) plaintext.append((char) ((i < 0 ? i + 26 : i) + 'A'));
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key (length must be a perfect square): ");
        String key = sc.nextLine().toUpperCase();
        int size = (int) Math.sqrt(key.length());
        if (size * size != key.length()) {
            System.out.println("Invalid key length!");
            return;
        }
        generateKeyMatrix(key, size);
        generateInverseKeyMatrix(size);
        System.out.print("Enter the plaintext: ");
        String plaintext = sc.nextLine().toUpperCase();
        while (plaintext.length() % size != 0) plaintext += "X";
        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i += size)
            ciphertext += encrypt(plaintext.substring(i, i + size), size);
        System.out.println("Ciphertext: " + ciphertext);
        String decryptedText = "";
        for (int i = 0; i < ciphertext.length(); i += size)
            decryptedText += decrypt(ciphertext.substring(i, i + size), size);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
