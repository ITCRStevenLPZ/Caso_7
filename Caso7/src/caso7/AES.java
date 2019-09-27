package caso7;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }*/
    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            //System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    public static String encryptedString = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";
    public static String letras[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static ArrayList<Combinacion> combinaciones;

    public static void CrearCombinaciones() {
        combinaciones = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 26; b++) {
                Combinacion nueva = new Combinacion(a, letras[b]);
                combinaciones.add(nueva);

            }
        }
    }
    public static boolean esnull;
    public static int iteraciones = 0;
    
    /*public static void ProbarAUX(int maximo, int minimo){
        
    }*/
    public static void Probar(int maximo, int minimo) {
        esnull = true;
        float porcent = (float) (0.5);
        int maximum = (int) (maximo * porcent);
        for (int a = minimo; a < maximum; a++) {
            iteraciones++;
            int numero = combinaciones.get(a).numero;
            String letra = combinaciones.get(a).letra;
            String llave = "29dh120" + letra + "dk1" + numero + "3";
            String decryptedString = decrypt(encryptedString, llave);
            esnull = decryptedString==null;
            System.out.println(maximum +"y"+ iteraciones);
        }
        //System.out.println(maximum);
            if (esnull == false) {
            if (maximo - minimo == 4) {
                for (int i = minimo; i < maximo; i++) {
                    int numero = combinaciones.get(i).numero;
                    String letra = combinaciones.get(i).letra;
                    System.out.println(letra + numero);

                }
            } else {
                Probar(maximum, minimo);
            }
        } else {
            if (4 >= maximo - minimo) {

                System.out.println("No hay posibles respuestas\n");

            } else {
                Probar(260+maximum, maximum);
            }

        }

    }

    public static void main(String[] args) {
        CrearCombinaciones();
        Probar(260, 0);

        //final String secretKey = "29dh120_dk1_3";
        //String decryptedString = decrypt(encryptedString, secretKey);
        //System.out.println(encryptedString);
        //System.out.println(decryptedString);
    }
}
