import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Polialfabetic {
    public static String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÌÍÏÒÓÙÚÜÇÑ";
    public static HashMap<Character, Integer> indexes = new HashMap<Character, Integer>(); 

    public static String alfabetPermutat = new String(alfabet);
    public static HashMap<Character, Integer> indexesPermutats = new HashMap<Character, Integer>(); 

    public static Random random = new Random(2006);

    public static void generaNouRandom(int llavor) {
        random = new Random(llavor);
    }

    public static void generaNouAlfabet() {
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

    public static String xifraPoliAlfa(String missatge) {
         StringBuilder missatgeXifrat = new StringBuilder();

        for (int index = 0; index < missatge.length(); index++) {
            generaNouAlfabet();

            char caracter = missatge.charAt(index);

            boolean eraMinuscula = Character.isLowerCase(caracter);

            caracter = Character.toUpperCase(caracter);

            Integer charIndex = indexes.get(caracter);

            missatgeXifrat.append(charIndex != null ? (eraMinuscula ? Character.toLowerCase(alfabetPermutat.charAt(charIndex)) : alfabetPermutat.charAt(charIndex)) : (eraMinuscula ? Character.toLowerCase(caracter) : caracter));
        }

        return missatgeXifrat.toString();
    }

    public static String dexifraPoliAlfa(String missatgeXifrat) {
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

    public static void main(String[] args) {
        for (int index = 0; index < alfabet.length(); index++) {
            indexes.put(alfabet.charAt(index), index);
        }

        String[] missatges = {
            "Test 01 àrbitre, coixí, Perímetre",
            "Test 02 Taüll, DÍA, año",
            "Test 03 Peça, Òrrius, Bòliva"
        };

        String[] missatgesXifrats = new String[missatges.length];

        System.out.println("Xifratge:\n----------");

        for (int index = 0; index < missatges.length; index++) {
            generaNouRandom(2006);
            missatgesXifrats[index] = xifraPoliAlfa(missatges[index]);
            System.out.printf("%-34s -> %s%n", missatges[index], missatgesXifrats[index]);
        }

        System.out.println("Dexifratge:\n----------");

        for (int index = 0; index < missatgesXifrats.length; index++) {
            generaNouRandom(2006);
            String missatge = dexifraPoliAlfa(missatgesXifrats[index]);
            System.out.printf("%-34s -> %s%n", missatgesXifrats[index], missatge);
        }
    }
}
