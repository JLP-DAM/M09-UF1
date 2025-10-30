package iticbcn.xifratge;

import java.security.KeyPair;
import javax.xml.bind.DatatypeConverter;

public class Main {
    public static void main(String[] args) throws Exception {
        ClauPublica clauPublica = new ClauPublica();

        String missatge = "Missatge de prova per xifrar áéíóú àèìòù äëïöü";

        KeyPair parellClaus = clauPublica.generaParellClausRSA();

        byte[] misstageXifrat = clauPublica.xifraRSA(missatge, parellClaus.getPublic());

        System.out.println("=======================");
        System.out.println("Text xifrat: ");
        System.out.println(DatatypeConverter.printHexBinary(misstageXifrat));
        
        String missatgeDesxifrat = clauPublica.desxifraRSA(misstageXifrat, parellClaus.getPrivate());

        System.out.println("=======================");
        System.out.println("Text desxifrat: " + missatgeDesxifrat);

        String stringClauPublica = DatatypeConverter.printHexBinary(parellClaus.getPublic().getEncoded());
        String stringClauPrivada = DatatypeConverter.printHexBinary(parellClaus.getPrivate().getEncoded());

        System.out.println("=======================");
        System.out.println("Clau pública: " + stringClauPublica);
        System.out.println("=======================");
        System.out.println("Clau privada: " + stringClauPrivada);
    }
}