package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    public static String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÌÍÏÒÓÙÚÜÇÑ";
    public static HashMap<Character, Integer> indexes = new HashMap<Character, Integer>(); 

    public static String alfabetPermutat = new String(alfabet);
    public static HashMap<Character, Integer> indexesPermutats = new HashMap<Character, Integer>(); 

    public static Random random = new Random(2006);

    public static void generaNouRandom(long llavor) {
        random = new Random(llavor);
    }

    public void generaNouAlfabet() {
        indexesPermutats = new HashMap<Character, Integer>(); 

        List<Character> caracters = new ArrayList<Character>();
        for(char caracter: alfabet.toCharArray()) {
            caracters.add(caracter);
        }
        StringBuilder alfabetPermutatBuilder = new StringBuilder(alfabet.length());

        while (caracters.size() != 0) {
            int caracterIndex = random.nextInt(caracters.size());
            alfabetPermutatBuilder.append(caracters.remove(caracterIndex));
        }

        alfabetPermutat = alfabetPermutatBuilder.toString();

        for (int index = 0; index < alfabetPermutat.length(); index++) {
            indexesPermutats.put(alfabetPermutat.charAt(index), index);
        }
    }

    public TextXifrat xifra(String missatge, String clau) throws ClauNoSuportada {
        long llavor;

        try {
            llavor = Long.parseLong(clau);
        } catch(Exception exception) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }

        generaNouRandom(llavor);
        
        StringBuilder missatgeXifrat = new StringBuilder();

        for (int index = 0; index < missatge.length(); index++) {
            generaNouAlfabet();

            char caracter = missatge.charAt(index);

            boolean eraMinuscula = Character.isLowerCase(caracter);

            caracter = Character.toUpperCase(caracter);

            Integer charIndex = indexes.get(caracter);

            missatgeXifrat.append(charIndex != null ? (eraMinuscula ? Character.toLowerCase(alfabetPermutat.charAt(charIndex)) : alfabetPermutat.charAt(charIndex)) : (eraMinuscula ? Character.toLowerCase(caracter) : caracter));
        }

        return new TextXifrat(missatgeXifrat.toString().getBytes());
    }

    public String desxifra(TextXifrat missatgeXifratTextXifrat, String clau) throws ClauNoSuportada {

        long llavor;

        try {
            llavor = Long.parseLong(clau);
        } catch(Exception exception) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }

        generaNouRandom(llavor);

        String missatgeXifrat = missatgeXifratTextXifrat.toString();

        StringBuilder missatgeDexifrat = new StringBuilder();

        for (int index = 0; index < missatgeXifrat.length(); index++) {
            generaNouAlfabet();
            
            char caracter = missatgeXifrat.charAt(index);

            boolean eraMinuscula = Character.isLowerCase(caracter);

            caracter = Character.toUpperCase(caracter);

            Integer charIndex = indexesPermutats.get(caracter);

            missatgeDexifrat.append(charIndex != null ? (eraMinuscula ? Character.toLowerCase(alfabet.charAt(charIndex)) : alfabet.charAt(charIndex)) : (eraMinuscula ? Character.toLowerCase(caracter) : caracter));
        }

        return missatgeDexifrat.toString();
    }
}
