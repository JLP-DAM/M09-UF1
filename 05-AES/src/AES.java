import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    public static final int MIDA_IV = 16;
    public static byte[] iv = new byte[MIDA_IV];
    public static final String CLAU = "0901202109012021";

    public static final SecureRandom secureRandom = new SecureRandom();

    public static byte[] xifraAES(String missatge, String clau) throws Exception {
        byte[] missatgeBytes = missatge.getBytes();

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(clau.getBytes(), ALGORISME_XIFRAT);
        
        Cipher cipher = Cipher.getInstance(FORMAT_AES);            
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            
        byte[] encriptat = cipher.doFinal(missatgeBytes);

        return encriptat;
    }

    public static String desxifraAES(byte[] bIvIMissatgeXifrat, String clau) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(clau.getBytes(), ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);

        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decriptat = cipher.doFinal(bIvIMissatgeXifrat);
        
        return new String(decriptat);
    }
    public static void main(String[] args) {
        String[] missatges = {
            "Lorem ipsum dicet",
            "Hola Andrés cómo está tu cuñado",
            "Àgora Ïlla Ôtto"
        };

        secureRandom.nextBytes(iv);

        for (int index = 0; index < missatges.length; index++) {
            String missatge = missatges[index];

            byte[] bXifrats = null;
            String desxifrat = "";

            try {
                bXifrats = xifraAES(missatge, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch(Exception exception) {
                System.err.println("Error de xifrat: " + exception.getLocalizedMessage());
            }

            System.out.println("--------------------");
            System.out.println("Missatge: " + missatge);
            System.out.println("Encriptat: " + (new String(bXifrats)));
            System.out.println("Decriptat: " + desxifrat);
        }
    }    
}
