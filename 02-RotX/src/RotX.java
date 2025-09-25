public class RotX {
    public static String lletres = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÌÍÏÒÓÙÚÜÇÑ";
    public static String[] provesXifrat = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

    public static char[] majuscules = lletres.toCharArray();
    public static char[] minuscules = lletres.toLowerCase().toCharArray();

    public static Integer trobaAMajuscules(char caracter) {
        for (int index = 0; index < majuscules.length; index++) {
            if (majuscules[index] != caracter) {continue;}

            return index;
        }

        return null;
    }

    public static Integer trobaAMinuscules(char caracter) {
        for (int index = 0; index < minuscules.length; index++) {
            if (minuscules[index] != caracter) {continue;}

            return index;
        }

        return null;
    }

    public static String xifraRotX(String cadena, int desplacament) {
        String cadenaFinal = "";

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            Integer indexMajuscules = trobaAMajuscules(caracter);
            Integer indexMinuscules = trobaAMinuscules(caracter);

            indexMajuscules = indexMajuscules != null ? ((indexMajuscules + desplacament) % majuscules.length) : null;
            indexMinuscules = indexMinuscules != null ? ((indexMinuscules + desplacament) % minuscules.length) : null;

            cadenaFinal = cadenaFinal + (indexMajuscules != null ? majuscules[indexMajuscules] : indexMinuscules != null ? minuscules[indexMinuscules] : caracter);
        }

        return cadenaFinal;
    }

    public static String dexifraRotX(String cadena, int desplacament) {
        String cadenaFinal = "";

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            Integer indexMajuscules = trobaAMajuscules(caracter);
            Integer indexMinuscules = trobaAMinuscules(caracter);


            indexMajuscules = indexMajuscules != null ? ((indexMajuscules - desplacament) % majuscules.length + majuscules.length) % majuscules.length : null;
            indexMinuscules = indexMinuscules != null ? ((indexMinuscules - desplacament) % minuscules.length + minuscules.length) % minuscules.length : null;

            cadenaFinal = cadenaFinal + (indexMajuscules != null ? majuscules[indexMajuscules] : indexMinuscules != null ? minuscules[indexMinuscules] : caracter);
        }

        return cadenaFinal;
    }

    public static void forcaBrutaRotX(String cadenaXifrada) {
        System.out.println(String.format("Missatge xifrat %s:\n-------------", cadenaXifrada));
        
        for (int desplacament = 0; desplacament < lletres.length(); desplacament++) {
            System.out.println(String.format("(%s) %s -> %s", desplacament, cadenaXifrada, dexifraRotX(cadenaXifrada, desplacament)));
        }

        System.out.println("\n\n");
    }

    public static void main(String[] args) {        
        for (int index = 0; index < provesXifrat.length; index++) {
            int desplacament = (index * 2);

            System.out.println(
                    String.format("%s (%s de desplaçament)\n-------------\n%s -> %s\n%s -> %s\n", 
                    provesXifrat[index],
                    desplacament,
                    provesXifrat[index], 
                    xifraRotX(provesXifrat[index], desplacament), 
                    xifraRotX(provesXifrat[index], desplacament), 
                    dexifraRotX(xifraRotX(provesXifrat[index], desplacament), desplacament)
                ));

            forcaBrutaRotX(xifraRotX(provesXifrat[index], desplacament));
        }
    }   
}