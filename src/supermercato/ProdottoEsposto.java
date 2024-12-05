package supermercato;

public class ProdottoEsposto {
    private Prodotto prodotto;
    private Corsia corsia;
    private int quantita;

    public ProdottoEsposto(Prodotto prodotto, Corsia corsia, int quantita) {
        this.prodotto = prodotto;
        this.corsia = corsia;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }
    public Corsia getCorsia() {
        return corsia;
    }
    public int getQuantita() {
        return quantita;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void setCorsia(Corsia corsia) {
        this.corsia = corsia;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
