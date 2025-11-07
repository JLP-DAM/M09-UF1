import java.security.MessageDigest;
import java.util.HexFormat;

import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

        messageDigest.update(salt.getBytes());

        return HexFormat.of().formatHex(messageDigest.digest(pw.getBytes()));
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 65536, 128);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return HexFormat.of().formatHex(factory.generateSecret(spec).getEncoded());
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        char[] charset = "abcdefABCDEF123456789!".toCharArray();

        int longitutCharset = charset.length;

        npass = 0;

        for (int index = 1; index <= 6; index++) {
            
            int[] indexes = new int[index];
            Arrays.fill(indexes, 0);

            while (true) {
                
                StringBuilder stringBuilder = new StringBuilder(index);

                for (int index2 = 0; index2 < index; index2++) {
                    stringBuilder.append(charset[indexes[index2]]);
                }

                String candidat = stringBuilder.toString();

                String stringHash = alg.equals("SHA-512") ? getSHA512AmbSalt(candidat, salt) : getPBKDF2AmbSalt(candidat, salt);

                npass++;

                if (hash.equals(stringHash)) {
                    return candidat;
                }

                
                int posicio = index - 1;

                while (posicio >= 0) {

                    if (indexes[posicio] + 1 < longitutCharset) {
                        indexes[posicio]++;
                        break;

                    } else {
                        indexes[posicio] = 0;
                        posicio--;

                    }
                }
                
                if (posicio < 0) {
                    break;
                }
            }
        }

        return null;
    }

    public String getInterval(long t1, long t2) {
        return "" + (t2 - t1);
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };

        String pwTrobat = null;
        String[] algorismes = { "SHA-512", "PBKDF2" };

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("--------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass:%s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps: %s\n", h.getInterval(t1, t2));
            System.out.printf("-----\n\n");
        }
    }
}
