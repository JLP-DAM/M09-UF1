package iticbcn.xifratge;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import java.util.HashMap;

public class XifradorMonoalfabetic implements Xifrador {
    public static String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÌÍÏÒÓÙÚÜÇÑ";
    public static HashMap<Character, Integer> indexes = new HashMap<Character, Integer>(); 

    public static char[] alfabetPermutat = alfabet.toCharArray();
    public static HashMap<Character, Integer> indexesPermutats = new HashMap<Character, Integer>(); 

    public static String[] provesXifrat = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

    public static char[] permutaAlfabet(String alfabet) {

        List<Character> alfabetList = alfabet.chars().mapToObj(caracter -> (char) caracter).collect(Collectors.toList());
        // abans de que m'executin per "haber utilitzat ia"
        // he buscat "cannot convert from List<char[]> to List<Character>" i he trobat això
        // https://www.geeksforgeeks.org/java/convert-a-string-to-a-list-of-characters-in-java/

        Collections.shuffle(alfabetList);

        StringBuilder stringBuilder = new StringBuilder();

        for (Character caracter: alfabetList) {
            stringBuilder.append(caracter);
        }

        //https://www.geeksforgeeks.org/java/convert-list-of-characters-to-string-in-java/

        return stringBuilder.toString().toCharArray();
    }

    public TextXifrat xifra(String cadena, String clau) throws ClauNoSuportada {

        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }

        StringBuilder stringXifrada = new StringBuilder();

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            boolean eraMinuscula = Character.isLowerCase(caracter);

            caracter = Character.toUpperCase(caracter);

            Integer charIndex = indexes.get(caracter);

            stringXifrada.append(charIndex != null ? (eraMinuscula ? Character.toLowerCase(alfabetPermutat[charIndex]) : alfabetPermutat[charIndex]) : (eraMinuscula ? Character.toLowerCase(caracter) : caracter));
        }

        return new TextXifrat(stringXifrada.toString().getBytes());
    }

    public String desxifra(TextXifrat cadenaTextXifrat, String clau) throws ClauNoSuportada {

        if (clau != null) {
            throw new ClauNoSuportada("Xifratxe monoalfabètic no suporta clau != null");
        }

        String cadena = cadenaTextXifrat.toString();

        StringBuilder stringXifrada = new StringBuilder();

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            boolean eraMinuscula = Character.isLowerCase(caracter);

            caracter = Character.toUpperCase(caracter);

            Integer charIndex = indexesPermutats.get(caracter);

            stringXifrada.append(charIndex != null ? (eraMinuscula ? Character.toLowerCase(alfabet.charAt(charIndex)) : alfabet.charAt(charIndex)) : (eraMinuscula ? Character.toLowerCase(caracter) : caracter));
        }

        return stringXifrada.toString();
    }
}
