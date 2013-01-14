package bosbrand.model;
public class Grond implements IBosbrandModel {
	// het grid van kavels
	IKavel[][] kavels;
	
	public void initialiseer(String[] wereld) {
		// het aantal rijen is de lengte van de array
			int rijen = wereld.length;
			// als er geen rijen zijn zijn er ook geen kolommen, anders zijn
			// er net zoveel kolommen als er characters in de eerste string
			// zitten
			int kolommen = (rijen == 0) ? 0 : wereld[0].length();
			
			// we initialiseren het grid op de goede grootte
			kavels = new IKavel[rijen][kolommen];
			
			// we lopen door alle rijen heen
			for (int rij = 0; rij < rijen; rij++) {
				// als de lengte van de rij niet overeen komt met de lengte
				// die we verwachten is de input geen rechthoek, printen we
				// dat dat een probleem is, en stoppen we met het programma.
				if (wereld[rij].length() != kolommen) {
					System.out.println("de lengte van de inputstrings komt niet overeen");
					System.exit(0);
				}
				// anders lopen we door alle kolommen heen
				for (int kolom = 0; kolom < kolommen; kolom++) {
					// we zetten op elke positie in het grid het type kavel
					// dat door het character op die plek in de string
					// gecodeerd wordt. Daarvoor gebruiken we de
					// haalKavelUitChar-methode.
					kavels[rij][kolom] = haalKavelUitChar(wereld[rij].charAt(kolom));
				}
			}
		}
	
	public IBoswachter[] getBoswachters() {
		return null;
	}
	
	public IKavel[][] getKavels() {
		return null;
	}
	
	public void toggleVuur(int rij, int kolom) {
		
	}
	
	public void toggleBoswachter(int rij, int kolom) {
		
	}
	
	
	public void reset() {
		
	}

	// haalKavelUitChar maakt een kavel van een char
	private IKavel haalKavelUitChar(char input) {
		// we willen verschillende dingen doen afhankelijk van de
		// waarde van de input, daar gebruiken we een string voor
		switch (input) {
			case 'a':
				// als het een 'a' is, geven we een appelboom terug die
				// niet in brand staat.
				return new AppelBoom(Boom.NIET_IN_BRAND);
			case 'A':
				// als het een 'A' is, geven we een appelboom terug die
				// in brand staat.
				return new AppelBoom(Boom.IN_BRAND);
			// idem voor braamstruiken en cypressen
			case 'b':
				return new BraamStruik(Boom.NIET_IN_BRAND);
			case 'B':
				return new BraamStruik(Boom.IN_BRAND);
			case 'c':
				return new Cypres(Boom.NIET_IN_BRAND);
			case 'C':
				return new Cypres(Boom.IN_BRAND);
			default:
				// als het niet een boom is, is het een leeg kavel. Dit
				// zorgt ervoor dat het programma 'iets' doet met alle
				// input.
				return new LeegKavel();
				
		}
	}
	
	/**
	 * (alle kavels van) grond naar de volgende toestand updaten 
	 * (waarbij kavels gebruik kunnen maken van de methode omgeving,
	 *  zie onder, om `zichzelf' te kunnen updaten)
	 */
	public void update() {		
		// het aantal rijen is de lengte van de array
		int rijen = kavels.length;
		// het aantal kolommen is 0 als er geen rijen zijn,
		// anders is het het aantal elementen van de eerste rij.
		int kolommen = (rijen == 0) ? 0 : kavels[0].length;
		
		// we maken een grid aan waar we de volgende toestand in gaan
		// zetten
		IKavel[][] volgendeKavels = new IKavel[rijen][kolommen];
		
		// loop door alle kavels heen
		for (int rij = 0; rij < rijen; rij++) {
			for (int kolom = 0; kolom < kolommen; kolom++) {
				// elke kavel in de volgende toestand kunnen we krijgen
				// door de volgendeToestand-methode van het kavel in die
				// positie aan te roepen.
				volgendeKavels[rij][kolom] = kavels[rij][kolom].volgendeToestand(this, rij, kolom);
			}
		}
		
		// update het grid door de 'volgende toestand' de huidige toestand
		// te maken.
		kavels = volgendeKavels;
	}
	
	/**
	 * @param rij,kolom een coordinaat in de grond
	 * @return tweedimensionaal array van kavels die 
	 * de omgeving van de kavel op coordinaat rij,kolom vormen
	 * (zie de opdracht) met de kavel zelf in het midden
	 */  
	public IKavel[][] omgeving(int rij,int kolom) {
		// maak een leeg 3x3 grid aan
		IKavel[][] omgeving = new IKavel[3][3];
		
		// loop door elke positie van de omgeving heen
		for (int dRij = 0; dRij < 3; dRij++) {
			for (int dKolom = 0; dKolom < 3; dKolom++) {
				// de rij op het grid die op een bepaalde plek in het omgevings-grid komt
				// is de rij van het kavel in het midden, plus de plek in het 
				// omgeving-grid, min 1. Idem voor de kolom.
				int rijOmTeChecken = dRij + rij -1;
				int kolomOmTeChecken = dKolom + kolom - 1;
				
				// we gebruiken de checkBuitenGrid-methode om te kijken of een positie
				// buiten het grid valt.
				boolean buitenGrid = checkBuitenGrid(rijOmTeChecken, kolomOmTeChecken);
				
				// als een positie buiten het grid valt zetten we daar een 'Leegte' kavel
				// neer (waar nooit iets groeit of brandt, en dus neutraal is wat betreft
				// voortBomen / voortBranden). Anders zetten we het goede kavel daar neer.
				omgeving[dRij][dKolom] = (buitenGrid) ? new Leegte() : kavels[rijOmTeChecken][kolomOmTeChecken];
			}
		}
		
		// we hebben het grid nu ingevuld en geven het terug
		return omgeving;
	}
	
	// checkt of een positie (rij, kolom) buiten het grid valt.
	private boolean checkBuitenGrid(int rij, int kolom) {
		// een kavel valt buiten het grid als:
		//	-het grid leeg is
		//	-de rij of kolom kleiner is dan 0
		//	-de rij of kolom groter is dan het aantal rijen/kolommen
		return (kavels.length == 0 ||
				rij < 0 || rij >= kavels.length ||
				kolom < 0 || kolom >= kavels[0].length);
	}
	
	// afbeelden naar een string
	public String toString() {
		// het aantal rijen is de lengte van de array
		int rijen = kavels.length;
		// het aantal kolommen is 0 als er geen rijen zijn,
		// anders is het het aantal elementen van de eerste rij.
		int kolommen = (rijen == 0) ? 0 : kavels[0].length;
		
		// we beginnen met lege output
		String output = "";
		
		// loop door alle kavels heen
		for (int rij = 0; rij < rijen; rij++) {
			for (int kolom = 0; kolom < kolommen; kolom++) {
				// voeg de stringrepresentatie van elk kavel toe
				output += kavels[rij][kolom];
			}
			// en een lege regel aan het einde van elke rij
			output += "\n";
		}
		
		// geef de output terug
		return output;
	}
}