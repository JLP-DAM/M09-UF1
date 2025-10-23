package iticbcn.xifratge;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class XifradorAES implements Xifrador {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    public static final int MIDA_IV = 16;
    public static byte[] iv = new byte[MIDA_IV];
    public static final String CLAU = "0901202109012021";

    public static final SecureRandom secureRandom = new SecureRandom();

    public TextXifrat xifra(String missatge, String clau) throws ClauNoSuportada {
        byte[] encriptat;

        try {
            byte[] missatgeBytes = missatge.getBytes();

            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(clau.getBytes(), ALGORISME_XIFRAT);
            
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            encriptat = cipher.doFinal(missatgeBytes);
        } catch (Exception exception) {
            throw new ClauNoSuportada(exception.getLocalizedMessage());
        }

        return new TextXifrat(encriptat);
    }

    public String desxifra(TextXifrat bIvIMissatgeXifratTextXifrat, String clau) throws ClauNoSuportada {
        byte[] decriptat;

        try {
            byte[] bIvIMissatgeXifrat = bIvIMissatgeXifratTextXifrat.getBytes();

            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(clau.getBytes(), ALGORISME_XIFRAT);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            decriptat = cipher.doFinal(bIvIMissatgeXifrat);
        } catch (Exception exception) {
            throw new ClauNoSuportada(exception.getLocalizedMessage());
        }

        return new String(decriptat);
    }
}
