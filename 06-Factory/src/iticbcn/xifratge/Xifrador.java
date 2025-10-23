package iticbcn.xifratge;

public interface Xifrador {
    public TextXifrat xifra(String missatge, String clau) throws ClauNoSuportada;
    public String desxifra(TextXifrat missatgeXifrat, String clau) throws ClauNoSuportada;
}
