package supermercato;

public class Prodotto{
	protected String codice;
	protected String nome;
	protected int volume;
	protected boolean daFrigo;
	protected double prezzoListino;
	protected int percentualeSconto;

	public Prodotto(String codice, String nome, int volume, boolean daFrigo) {
		this.codice = codice;
		this.nome = nome;
		this.volume = volume;
		this.daFrigo = daFrigo;
		this.prezzoListino = 0;
		this.percentualeSconto = 0;
	}

	public String getCodice(){
		return this.codice;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public int getVolume(){
		return this.volume;
	}

	public void setPrezzoListino(double prezzo) {
		this.prezzoListino = prezzo;
	}

	public double getPrezzoListino() {
		return this.prezzoListino;
	}

	public void setPercentualeSconto(int percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	public int getPercentualeSconto() {
		return this.percentualeSconto;
	}

	public boolean isDaFrigo(){
		return false;
	}
	
}
