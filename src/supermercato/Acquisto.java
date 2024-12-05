package supermercato;

public class Acquisto {
    private Prodotto prodotto;
    private int quantita;

    public Acquisto(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }
}
