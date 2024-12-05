package supermercato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Supermercato {
	TreeMap<String,Corsia> corsie = new TreeMap<>();
	TreeMap<String,Prodotto> prodotti = new TreeMap<>();
	LinkedList<ProdottoEsposto> prodottiEsposti = new LinkedList<>();
	TreeMap<Integer,Scontrino> scontrini = new TreeMap<>();
	int numeroScontrino = 1000;

	public void aggiungiCorsia(String nome, int capienzaMassima){
		corsie.put(nome, new Corsia(nome, capienzaMassima));
	}
	
	public Prodotto catalogaProdotto(String codiceProdotto, String nomeProdotto, int volume, boolean daFrigo){
		if (prodotti.containsKey(codiceProdotto)) {
			return prodotti.get(codiceProdotto);
		} 
		if (daFrigo == true) {
			ProdottoDaFrigo p = new ProdottoDaFrigo(codiceProdotto, nomeProdotto, volume, daFrigo);
			prodotti.put(codiceProdotto, p);
			return p;
		}
		Prodotto p = new Prodotto(codiceProdotto, nomeProdotto, volume, daFrigo);
		prodotti.put(codiceProdotto, p);
		return p;
	}	
	
	public Collection<Prodotto> elencoProdotti(){
		return prodotti.values().stream()
								.sorted(Comparator.comparing(Prodotto::getPrezzoListino).reversed())
								.toList(); 
	}
	
	public Prodotto cercaProdotto(String codiceProdotto) throws ProdottoInesistenteException{
		if (!prodotti.containsKey(codiceProdotto)) {
			throw new ProdottoInesistenteException();
		}
		return prodotti.get(codiceProdotto);
	}
	
	public void esponiProdotto(String nomeCorsia, Prodotto prodotto, int quantita){
		Corsia c = corsie.get(nomeCorsia);
		if (c.getCapienzaOccupata() + quantita > c.getCapienza()) {
			int resto = c.getCapienzaOccupata() + prodotto.getVolume()*quantita - c.getCapienza();
			int quantitaEffettiva = prodotto.getVolume()*quantita - resto;
			c.diminuisciCapienza(quantitaEffettiva);
			ProdottoEsposto pe = new ProdottoEsposto(prodotto, c, quantitaEffettiva);
			prodottiEsposti.add(pe);
			c.addProdottoEsposto(pe);
		} 
		else {
			c.diminuisciCapienza(prodotto.getVolume()*quantita);
			ProdottoEsposto pe = new ProdottoEsposto(prodotto, c, prodotto.getVolume()*quantita);
			prodottiEsposti.add(pe);
			c.addProdottoEsposto(pe);
		}
	}

	public int calcolaPercentualeDiOccupazione(String nomeCorsia) {
		Corsia corsia = corsie.get(nomeCorsia);
		int capienzaOccupata = corsia.getCapienzaOccupata();
		int capienzaTotale = corsia.getCapienza();
		if (capienzaTotale == 0) return 0;
		return (int) ((double) capienzaOccupata / capienzaTotale * 100);
	}
	
	public Collection<String> elencoCodiciProdottoPerCorsia(String nomeCorsia){
		return corsie.get(nomeCorsia).getProdottiEsposti().stream()
														  .map(p -> p.getProdotto().getCodice())
														  .sorted()
														  .toList();
	}

	public int quantitaProdottoEsposto(Prodotto prodotto, String nomeCorsia) {
		ProdottoEsposto p = corsie.get(nomeCorsia).getProdottiEsposti().stream()
			.filter(pe -> pe.getProdotto().equals(prodotto))
			.findAny().orElse(null);
	
		if (p == null) {
			return 0; 
		}
		return p.getQuantita();
	}
	
	public int quantitaProdottoEsposto(Prodotto prodotto){
		int total = 0;
		for (Corsia c : corsie.values()) {
			ProdottoEsposto p =  c.getProdottiEsposti().stream()
													   .filter(pe -> pe.getProdotto().equals(prodotto))
													   .findAny().orElse(null);
			total += p.getQuantita();
		}
		return total;
	}
	
	public int apriScontrino() {
		Scontrino s = new Scontrino(numeroScontrino);
		scontrini.put(numeroScontrino++, s);
		return numeroScontrino;
	}
	
	public void acquistaProdotto(int codiceScontrino, Prodotto prodotto, String nomeCorsia, int quantita) throws CorsiaInesistenteException{
		if (!corsie.containsKey(nomeCorsia)) {
			throw new CorsiaInesistenteException();
		}
		ProdottoEsposto p =  corsie.get(nomeCorsia).getProdottiEsposti().stream()
																		.filter(pe -> pe.getCorsia().getName() == nomeCorsia &&
																				pe.getProdotto().equals(prodotto))
																		.findAny().orElse(null);
		
		if (scontrini.containsKey(codiceScontrino)) {
			Scontrino s = scontrini.get(codiceScontrino);
			if (p.getQuantita() < quantita) {
				Acquisto a = new Acquisto(p.getProdotto(), p.getQuantita());
				p.setQuantita(0);
				corsie.get(nomeCorsia).aumentaCapienza(p.getQuantita());
				s.aggiungiAcquisto(a);
			}
			else {
				Acquisto a = new Acquisto(p.getProdotto(), quantita);
				p.setQuantita(p.getQuantita()-quantita);
				corsie.get(nomeCorsia).aumentaCapienza(quantita);
				s.aggiungiAcquisto(a);
			}
		}
	}
	
	public String dettagliScontrino(int codiceScontrino){
		Scontrino s = scontrini.get(codiceScontrino);
		StringBuilder stringa = new StringBuilder();
		for (Acquisto a : s.getAcquisti()) {
			stringa.append(a.getProdotto().getCodice() + " " + a.getQuantita() + "\n");
		}
		stringa.deleteCharAt(stringa.lastIndexOf("\n"));
		return stringa.toString();
	}
	
	public double chiudiScontrino(int codiceScontrino) {
		double total = 0.0;
		Scontrino s = scontrini.get(codiceScontrino);
		for (Acquisto a : s.getAcquisti()) {
			Prodotto p = a.getProdotto();
			if (p.getPercentualeSconto() == 0) {
				total += p.getPrezzoListino();
			}
			else {
				double prezzoScontato = p.getPrezzoListino() - (p.getPrezzoListino()/100.0)*(double)p.getPercentualeSconto();
				total += prezzoScontato;
			}
		}
		return total;
	}
	
	public void leggiFile(String nomeFile) throws IOException {
		String line;
		FileReader fr = new FileReader(nomeFile);
		try (BufferedReader br = new BufferedReader(fr)) {
			line = br.readLine();
			while (line != null) {
				String elements[] = line.split(";");
				if (elements[0].equals("PRODOTTO_NO_FRIGO")) {
					Prodotto p = new Prodotto(elements[1], elements[2], Integer.parseInt(elements[3]), false);
					p.setPrezzoListino(Double.parseDouble(elements[4]));
					p.setPercentualeSconto(Integer.parseInt(elements[5]));
					prodotti.put(elements[1], p);
				}
				else if (elements[0].equals("PRODOTTO_DA_FRIGO")) {
					ProdottoDaFrigo p = new ProdottoDaFrigo(elements[1], elements[2], Integer.parseInt(elements[3]), true);
					p.setPrezzoListino(Double.parseDouble(elements[4]));
					p.setPercentualeSconto(Integer.parseInt(elements[5]));
					p.setTemperaturaDiConservazione(Integer.parseInt(elements[6]));
					prodotti.put(elements[1], p);
				}
				else if (elements[0].equals("ESPOSIZI_PRODOTTO")) {
					esponiProdotto(elements[2], prodotti.get(elements[1]), Integer.parseInt(elements[3]));
				}
				line = br.readLine();
			}
		}
	}	
	
}
