package supermercato;

public class ProdottoDaFrigo extends Prodotto{
	private double prezzoListino;
	private int percentualeSconto;
	private int temperaturaDiConservazione;

	public ProdottoDaFrigo(String codiceProdotto, String nome, int volume, boolean daFrigo) {
		super(codiceProdotto, nome, volume, daFrigo);
		this.prezzoListino = 0;
		this.percentualeSconto = 0;
		this.temperaturaDiConservazione = 0;
	}

	public int getTemperaturaDiConservazione(){
		return this.temperaturaDiConservazione;
	}

	public void setTemperaturaDiConservazione(int temperaturaDiConservazione){
		this.temperaturaDiConservazione = temperaturaDiConservazione;
	}

	@Override
	public int getPercentualeSconto() {
		return this.percentualeSconto;
	}

	@Override
	public double getPrezzoListino() {
		return this.prezzoListino;
	}

	@Override
	public void setPercentualeSconto(int percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	@Override
	public void setPrezzoListino(double prezzo) {
		this.prezzoListino = prezzo;
	}

	@Override
	public boolean isDaFrigo() {
		return true;
	}
}
