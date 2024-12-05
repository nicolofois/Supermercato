package supermercato;

import java.util.LinkedList;

public class Corsia {
    private String name;
    private int capienza;
    private int capienzaOccupata;
    private LinkedList<ProdottoEsposto> prodottiEsposti;

    public Corsia(String name, int capienza) {
        this.name = name;
        this.capienza = capienza;
        this.capienzaOccupata = 0;
        this.prodottiEsposti = new LinkedList<>();
    }

    public String getName() {
        return name;
    }
    public int getCapienza() {
        return capienza;
    }
    public int getCapienzaOccupata() {
        return this.capienzaOccupata;
    }
    public void diminuisciCapienza(int capienzaProdotti) {
        this.capienzaOccupata += capienzaProdotti;
    }
    public void aumentaCapienza(int capienzaProdotti) {
        this.capienzaOccupata -= capienzaProdotti;
    }
    public LinkedList<ProdottoEsposto> getProdottiEsposti() {
        return this.prodottiEsposti;
    }
    public void addProdottoEsposto(ProdottoEsposto pe) {
        prodottiEsposti.add(pe);
    }
}
