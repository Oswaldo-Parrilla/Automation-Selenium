package PageObjects;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordEncryptor {

    //Clave secreta para encriptar o desencriptar
    private static final String Secret_Key = "mySuperSecretKey";
    private static final String SALT = "randomSaltValue"; // Puede ser generada dinámicamente

    // Generar una clave AES de 16 bytes basada en SECRET_KEY
    private static SecretKeySpec generateKey() throws Exception {
        byte[] saltBytes = SALT.getBytes();
        KeySpec spec = new PBEKeySpec(Secret_Key.toCharArray(), saltBytes, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Metodo para generar un IV (Vector de Inicialización) aleatorio
    private static String generateIV() {
        byte[] iv = new byte[16]; // AES usa bloques de 16 bytes
        new SecureRandom().nextBytes(iv);//Aquí se utiliza la clase SecureRandom para generar números aleatorios de forma segura y criptográficamente fuerte.
        return Base64.getEncoder().encodeToString(iv);//convertimos a una cadena de texto legible utilizando Base64.
    }

    // Metodo para Encriptar password
    public static String encrypt(String password) {
        try {
            SecretKeySpec secretKey = generateKey();
            String ivString = generateIV(); // Generamos un IV aleatorio
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(ivString));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return ivString + ":" + Base64.getEncoder().encodeToString(encryptedBytes); // Guardamos IV + Data
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    //Metodo para desencriptar el password
    public static String decrypt(String encryptedPassword) {
        try {
            SecretKeySpec secretKey = generateKey();

            // Extraer IV y datos encriptados
            String[] parts = encryptedPassword.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Formato de contraseña inválido");
            }
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[0]));
            byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar la contraseña", e);
        }
    }

}
