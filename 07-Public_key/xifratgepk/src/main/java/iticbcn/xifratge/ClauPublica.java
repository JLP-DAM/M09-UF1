package iticbcn.xifratge;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;

import javax.crypto.Cipher;

public class ClauPublica {
    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        return keyPairGenerator.generateKeyPair();
    }

    public byte[] xifraRSA(String missatge, PublicKey clauPublica) throws Exception {
        Cipher encriptador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        encriptador.init(Cipher.ENCRYPT_MODE, clauPublica);

        return encriptador.doFinal(missatge.getBytes());
    }

    public String desxifraRSA(byte[] missatgeXifrat, PrivateKey clauPrivada) throws Exception {
        Cipher desencriptador = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        desencriptador.init(Cipher.DECRYPT_MODE, clauPrivada);

        return new String(desencriptador.doFinal(missatgeXifrat));
    }
}
