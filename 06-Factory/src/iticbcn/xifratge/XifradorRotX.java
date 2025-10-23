package iticbcn.xifratge;


public class XifradorRotX implements Xifrador {
    public static String lletres = "ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÈÉÌÍÏÒÓÙÚÜÇÑ";
    public static String[] provesXifrat = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};

    public static char[] majuscules = lletres.toCharArray();
    public static char[] minuscules = lletres.toLowerCase().toCharArray();

    public Integer trobaAMajuscules(char caracter) {
        for (int index = 0; index < majuscules.length; index++) {
            if (majuscules[index] != caracter) {continue;}

            return index;
        }

        return null;
    }

    public Integer trobaAMinuscules(char caracter) {
        for (int index = 0; index < minuscules.length; index++) {
            if (minuscules[index] != caracter) {continue;}

            return index;
        }

        return null;
    }

    public TextXifrat xifra(String cadena, String desplacamentString) throws ClauNoSuportada {
        int desplacament;

        try {
            desplacament = Integer.parseInt(desplacamentString);
        } catch(Exception exception) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a int");
        }

        if (desplacament < 0 || desplacament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        
        String cadenaFinal = "";

        for (int index = 0; index < cadena.length(); index++) {
            char caracter = cadena.charAt(index);

            Integer indexMajuscules = trobaAMajuscules(caracter);
            Integer indexMinuscules = trobaAMinuscules(caracter);

            indexMajuscules = indexMajuscules != null ? ((indexMajuscules + desplacament) % majuscules.length) : null;
            indexMinuscules = indexMinuscules != null ? ((indexMinuscules + desplacament) % minuscules.length) : null;

            cadenaFinal = cadenaFinal + (indexMajuscules != null ? majuscules[indexMajuscules] : indexMinuscules != null ? minuscules[indexMinuscules] : caracter);
        }

        return new TextXifrat(cadenaFinal.getBytes());
    }

    public String desxifra(TextXifrat cadenaTextXifrat,  String desplacamentString) throws ClauNoSuportada {
        int desplacament;

        try {
            desplacament = Integer.parseInt(desplacamentString);
        } catch(Exception exception) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplacament < 0 || desplacament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        String cadena = cadenaTextXifrat.toString();
        
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
}