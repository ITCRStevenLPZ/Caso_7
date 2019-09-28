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
			// System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
	public static final int TOTAL=1000;
	
	public static String encryptedString = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";

	public static String letras[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	public static ArrayList<Combinacion> combinaciones;// array global, el cual va a tener, en la primera iteracion, a
														// todas las combinaciones

	public static ArrayList<Combinacion> Porcentajesmenores;// array que contiene a las combinaciones que tengan
															// porcentajes menores a 1000/260 (3.846)

	public static ArrayList<Combinacion> Porcentajesmedianos;// array que contiene las combinaciones con porcentajes que
																// van del 100/260 hasta el 30%

	public static ArrayList<Combinacion> Porcentajesgrandes;// array que contiene las combinaciones con porcentajes que
															// van del 10% hacia adelante. Son considerados como el
															// array respuesta

	public static void CrearCombinaciones() {// metodo que crea las combinaciones y les asigna una probabilidad
												// default(1000/260), asignando a 1000 como el 100%
		float probabilidad = (float)(3.846153846153); // la probabilidad que tienen las combinaciones de aparecer por default, en
											// este caso se toma 1000 como 100 por ciento, para mejor facilidad de
											// comprension
		combinaciones = new ArrayList<>();
		Porcentajesmenores = new ArrayList<>();
		Porcentajesmedianos = new ArrayList<>();
		Porcentajesgrandes = new ArrayList<>();

		for (int a = 0; a < 10; a++) {
			for (int b = 0; b < 26; b++) {
				Combinacion nueva = new Combinacion(a, letras[b], probabilidad);
				combinaciones.add(nueva);// se anade en un array, de forma ordenada (a0...z9)

			}
		}
	}

	public static void AjustarMayor(ArrayList<Combinacion> array, Combinacion constante) {
		float acambiar = constante.probabilidad;
		float total=0;
		//total+=constante.probabilidad;
		//System.out.println(constante.probabilidad);
		float nuevoCien = (float) (TOTAL - acambiar);
		//System.out.println(nuevoCien);
		for (int j = 0; j < array.size(); j++) {
			if (array.get(j) != constante) {
				//total+=array.get(j).probabilidad;
				array.get(j).probabilidad = (float) ((array.get(j).probabilidad * nuevoCien) / TOTAL);
				total+=array.get(j).probabilidad;
				//System.out.println(total);				
			}
		}
		total+=acambiar;
		//System.out.println(total);
		//System.out.println("Ajuste de probabilidades exitoso");
	}
	public static void Ajustar(ArrayList<Combinacion> array, Combinacion constante) {
		float acambiar = constante.probabilidad;
		float total=0;
		total+=constante.probabilidad;
		//System.out.println(constante.probabilidad);
		float nuevoCien = (float) (TOTAL + acambiar);
		//System.out.println(nuevoCien);
		for (int j = 0; j < array.size(); j++) {
			if (array.get(j) != constante) {
				array.get(j).probabilidad = (float) ((array.get(j).probabilidad * TOTAL) / nuevoCien);
				total+=array.get(j).probabilidad;
				
			}
		}
		total+=acambiar;
		//System.out.println(total);
		//System.out.println("Ajuste de probabilidades exitoso");
	}
	

	public static void Probar(ArrayList<Combinacion> array) {
		iteraciones++;
		for (int a = 0; a < 10; a++) {
			int b = (int) (Math.random() * ((259 - 0) + 1)) + 0;
			//System.out.println(b);
			int numero = array.get(b).numero;
			String letra = array.get(b).letra;
			String key = "29dh120" + letra + "dk1" + numero + "3";

			if (decrypt(encryptedString, key) == null) {
				//System.out.println(array.get(b).probabilidad);
				array.get(b).probabilidad -= 1;
				Ajustar(array, array.get(b));
				if (array.get(b).probabilidad < (TOTAL / 260)) {
					Porcentajesmenores.add(array.get(b));
					//System.out.println("Menores "+Porcentajesmenores.size());
					// array.remove(b);
				} else if (array.get(b).probabilidad >= (TOTAL / 260) && array.get(b).probabilidad >= 30) {
					Porcentajesmedianos.add(array.get(b));
					//System.out.println("Medianos"+Porcentajesmedianos.size());
					// array.remove(b);
				} else {
					Porcentajesgrandes.add(array.get(b));
					//System.out.println(Porcentajesgrandes.size());
					
					// array.remove(b);
				}
			} else {
				System.out.println("Se ha encontrado un posible\n");
				array.get(b).probabilidad += 30;
				AjustarMayor(array, array.get(b));
				if (array.get(b).probabilidad < (TOTAL / 260)) {
					Porcentajesmenores.add(array.get(b));
					//System.out.println("Menores "+Porcentajesmenores.size());
					// array.remove(b);
				} else if (array.get(b).probabilidad <= (TOTAL / 260) && array.get(b).probabilidad >= 30) {
					Porcentajesmedianos.add(array.get(b));
					//System.out.println("Medianos"+Porcentajesmedianos.size());
					// array.remove(b);
				} else {
					Porcentajesgrandes.add(array.get(b));
					//System.out.println(Porcentajesgrandes.size());
					// array.remove(b);
				}
			}
		}if(Porcentajesgrandes.size()==0) {
			Probar(combinaciones);
		}else {
			for(int i = 0;i<Porcentajesgrandes.size();i++) {
				int numero = Porcentajesgrandes.get(i).numero;
				String letra =Porcentajesgrandes.get(i).letra;
				System.out.println("Una combinacion probable es: "+letra+numero+"\n");
			}
		}

	}

	public static boolean encontrado = false;
	public static int iteraciones = 0;

	public static void main(String[] args) {
		CrearCombinaciones();
		Probar(combinaciones);
		System.out.println("Hay " +iteraciones+ " * 10 iteraciones en la solucion de este problema");

		 //final String secretKey = "29dh120bdk133";
		 //String decryptedString = decrypt(encryptedString, secretKey);
		 //System.out.println(encryptedString);
		//System.out.println(decryptedString);
	}
}
