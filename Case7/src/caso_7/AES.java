package caso_7;

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
    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    public static String encryptedString = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";
    public static String letras[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static ArrayList<Combinacion> combinaciones;//array global, el cual va a tener, en la primera iteracion, a todas las combinaciones
    public static ArrayList<Combinacion> Porcentajesmenores;//array que contiene a las combinaciones que tengan porcentajes menores a 1000/260 (3.846)
    public static ArrayList<Combinacion> Porcentajesmedianos;//array que contiene las combinaciones con porcentajes que van del 100/260 hasta el 10%
    public static ArrayList<Combinacion> Porcentajesgrandes;//array que contiene las combinaciones con porcentajes que van del 10% hacia adelante. Son considerados como el array respuesta
    public static void CrearCombinaciones() {//metodo que crea las combinaciones y les asigna una probabilidad default(1000/260), asignando a 1000 como el 100%
    	float probabilidad=1000/260; //la probabilidad que tienen las combinaciones de aparecer por default, en este caso se toma 1000 como 100 por ciento, para mejor facilidad de comprension
        combinaciones = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            for (int b = 0; b < 26; b++) {
                Combinacion nueva = new Combinacion(a, letras[b],probabilidad);
                combinaciones.add(nueva);// se anade en un array, de forma ordenada (a0...z9)

            }
        }
    }
    public static void Probar(int iteraciones) {
    	for(int a=0;a<iteraciones;a++) {
    		
            int numero = combinaciones.get(a).numero;
            String letra = combinaciones.get(a).letra;
            String key = "29dh120" + letra + "dk1" + numero + "3";
    	}
    }
    
    public static boolean encontrado = false;
    public static int iteraciones = 0;
    public static void main(String[] args) {
        CrearCombinaciones();
        //final String secretKey = "29dh120_dk1_3";
        //String decryptedString = decrypt(encryptedString, secretKey);
        //System.out.println(encryptedString);
        //System.out.println(decryptedString);
    }
}

