public class Rot13 {
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

    public static String xifraRot13(String cadena) {
        String cadenaFinal = "";

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            Integer indexMajuscules = trobaAMajuscules(caracter);
            Integer indexMinuscules = trobaAMinuscules(caracter);

            indexMajuscules = indexMajuscules != null ? ((indexMajuscules + 13) % majuscules.length) : null;
            indexMinuscules = indexMinuscules != null ? ((indexMinuscules + 13) % minuscules.length) : null;

            cadenaFinal = cadenaFinal + (indexMajuscules != null ? majuscules[indexMajuscules] : indexMinuscules != null ? minuscules[indexMinuscules] : caracter);
        }

        return cadenaFinal;
    }

    public static String dexifraRot13(String cadena) {
        String cadenaFinal = "";

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            Integer indexMajuscules = trobaAMajuscules(caracter);
            Integer indexMinuscules = trobaAMinuscules(caracter);


            indexMajuscules = indexMajuscules != null ? (indexMajuscules - 13) < 0 ? ((majuscules.length) - (13 - indexMajuscules)) : (indexMajuscules - 13) : null;
            indexMinuscules = indexMinuscules != null ? (indexMinuscules - 13) < 0 ? ((minuscules.length) - (13 - indexMinuscules)) : (indexMinuscules - 13) : null;

            cadenaFinal = cadenaFinal + (indexMajuscules != null ? majuscules[indexMajuscules] : indexMinuscules != null ? minuscules[indexMinuscules] : caracter);
        }

        return cadenaFinal;
    }

    public static void main(String[] args) {        
        for (int index = 0; index < provesXifrat.length; index++) {
            System.out.println(
                    String.format("%s\n-------------\n%s -> %s\n%s -> %s\n", 
                    provesXifrat[index],
                    provesXifrat[index], 
                    xifraRot13(provesXifrat[index]), 
                    xifraRot13(provesXifrat[index]), 
                    dexifraRot13(xifraRot13(provesXifrat[index])
                )));
        }   
    }
}