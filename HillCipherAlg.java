import java.io.*;
import java.util.*;

public class HillCipherAlg {
    static float[][] decrypt = new float[3][1];
    static float[][] a = new float[3][3];
    static float[][] b = new float[3][3];
    static float[][] mes = new float[3][1];
    static float[][] res = new float[3][1];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // Getting key matrix and message
        getkeymes();

        // Encryption process: multiplying key matrix with message matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                res[i][j] = 0; // Initialize the result matrix element
                for (int k = 0; k < 3; k++) {
                    res[i][j] += a[i][k] * mes[k][j]; // Matrix multiplication
                }
                res[i][j] = res[i][j] % 26; // Modulo 26 to keep results in the alphabet range
            }
        }

        // Output the encrypted string
        System.out.print("\nEncrypted string is : ");
        for (int i = 0; i < 3; i++) {
            System.out.print((char) (res[i][0] + 97)); // Convert numbers to lowercase letters
        }

        // Finding the inverse of the key matrix for decryption
        inverse();

        // Decryption process: multiplying inverse key matrix with the encrypted message matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                decrypt[i][j] = 0; // Initialize the result matrix for decryption
                for (int k = 0; k < 3; k++) {
                    decrypt[i][j] += b[i][k] * res[k][j]; // Matrix multiplication for decryption
                }
                decrypt[i][j] = decrypt[i][j] % 26; // Modulo 26 for decryption
            }
        }

        // Output the decrypted string
        System.out.print("\nDecrypted string is : ");
        for (int i = 0; i < 3; i++) {
            System.out.print((char) (decrypt[i][0] + 97)); // Convert numbers back to characters
        }
        System.out.print("\n");
    }

    // Method to get key matrix and message from the user
    public static void getkeymes() throws IOException {
        System.out.println("Enter 3x3 matrix for key (It should be invertible): ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = sc.nextFloat(); // Read the key matrix
            }
        }

        System.out.print("\nEnter a 3 letter string: ");
        String msg = br.readLine(); // Read the message string
        for (int i = 0; i < 3; i++) {
            mes[i][0] = msg.charAt(i) - 97; // Convert characters to numbers (a = 0, b = 1, ..., z = 25)
        }
    }

    // Method to compute the inverse of the key matrix
    public static void inverse() {
        float p, q;
        float[][] c = new float[3][3]; // Temporary matrix for manipulation

        // Initialize matrix 'b' as the identity matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    b[i][j] = 1; // Identity matrix
                } else {
                    b[i][j] = 0;
                }
            }
        }

        // Copy matrix 'a' into 'c'
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                c[i][j] = a[i][j];
            }
        }

        // Perform Gaussian elimination to compute the inverse
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 3; i++) {
                p = c[i][k];
                q = c[k][k];
                for (int j = 0; j < 3; j++) {
                    if (i != k) {
                        c[i][j] = c[i][j] * q - p * c[k][j];
                        b[i][j] = b[i][j] * q - p * b[k][j];
                    }
                }
            }
        }

        // Normalize the matrix by dividing by the diagonal elements
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                b[i][j] = b[i][j] / c[i][i];
            }
        }

        // Output the inverse matrix
        System.out.println("\nInverse Matrix is : ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(b[i][j] + " ");
            }
            System.out.println();
        }
    }
}