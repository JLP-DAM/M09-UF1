import java.security.MessageDigest;
import java.util.HexFormat;

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
        PBEKeySpec spec = new PBEKeySpec(pw.toCharArray(), salt.getBytes(), 10000, salt.length());

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return HexFormat.of().formatHex(factory.generateSecret(spec).getEncoded());
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        char[] charset = "abcdefABCDEF123456789!".toCharArray();

        StringBuilder stringBuilder = new StringBuilder();

        npass = 0;

        for (int characterIndex = 0; characterIndex < 6; characterIndex++) {
            for (int characterIndex2 = 0; characterIndex2 < 6; characterIndex2++) {
                for (int index = 0; index < charset.length; index++) {
                    if (stringBuilder.length() > characterIndex) {
                        break;
                    }

                    if (stringBuilder.length() <= characterIndex2) {
                        stringBuilder.append(charset[index]);
                    } else {
                        stringBuilder.setCharAt(characterIndex2, charset[index]);
                    }

                    String stringHash = alg.equals("SHA-512") ? getSHA512AmbSalt(stringBuilder.toString(), salt) : getPBKDF2AmbSalt(stringBuilder.toString(), salt);

                    npass++;

                    if (hash.equals(stringHash)) {return stringBuilder.toString();};
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
