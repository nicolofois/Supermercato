package supermercato;

import java.util.LinkedList;

public class Scontrino {
    private int code;
    private LinkedList<Acquisto> acquisti;

    public Scontrino(int code) {
        this.code = code;
        this.acquisti = new LinkedList<>();
    }

    public int getCode() {
        return code;
    }

    public LinkedList<Acquisto> getAcquisti() {
        return this.acquisti;
    }

    public void aggiungiAcquisto(Acquisto a) {
        this.acquisti.add(a);
    }




}
