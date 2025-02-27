package PageObjects;

public class EncryptText {
    public static void main(String[] args) {
        // Texto que quieres encriptar
        String texto = "";
        // Encriptar usando PasswordEncryptor
        String encryptedText = PasswordEncryptor.encrypt(texto);
        // Mostrar el resultado
        System.out.println("Texto encriptado: " + encryptedText);
    }
}
