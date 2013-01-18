package bosbrand.model;

import java.util.*;

public class Grond implements IBosbrandModel {
	// het grid van kavels. Deze zijn public zodat ze in de class boswachter
	// gebruikt kunnen worden
	IKavel[][] kavels;
	IKavel[][] kavels_original;
	IBoswachter[][] posBoswachters;

	/**
	 * Methode die de grond opstelt uit de input. En die de positie-array van de boswachters maakt. Deze arrays zijn even groot en de x, y coordinaten komen
	 * dus overeen. 
	 * @param String[] wereld: De input van de commandline
	 */
	public void initialiseer(String[] wereld) {
		int lengte = wereld.length;
		int breedte = wereld[0].length();
		// we initialiseren het grid op de goede grootte: aantal rijen en aantal
		// kolommen van wereld.
		kavels = new IKavel[lengte][breedte];
		kavels_original = kavels;

		// We initialiseren het boswachter grid emt dezelfde grootte als kavels.
		posBoswachters = new IBoswachter[lengte][breedte];

		// we lopen door alle rijen en kolommen heen
		for (int rij = 0; rij < kavels.length; rij++) {
			for (int kolom = 0; kolom < kavels[rij].length; kolom++) {
				// we zetten op elke positie in het grid het type kavel
				// dat door het character op die plek in de string
				// gecodeerd wordt. Daarvoor gebruiken we de
				// haalKavelUitChar-methode.
				kavels[rij][kolom] = haalKavelUitChar(wereld[rij].charAt(kolom));
			}
		}
	}

	/**
	 * Methode om alle boswachters in de wereld in een array van Boswachters te
	 * stoppen.
	 */
	public IBoswachter[] getBoswachters() {
		// ArrayList gebruiken voor de variabele grootte.
		ArrayList<IBoswachter> al = new ArrayList<IBoswachter>();
		for (int r = 0; r < posBoswachters.length; r++) {
			for (int c = 0; c < posBoswachters[r].length; c++) {
				if (posBoswachters[r][c] instanceof IBoswachter) {
					al.add(posBoswachters[r][c]);
				}
			}
		}
		// ArrayList omzetten in een array van het type IBoswachter.
		IBoswachter[] bw = al.toArray(new Boswachter[al.size()]);

		return bw;
	}
	
	public IBoswachter[][] getBoswachterPos() {
		return posBoswachters;
	}

	// Spreekt voor zich.
	public IKavel[][] getKavels() {
		return kavels;
	}

	/**
	 * Methode die het vuur op een kavel toggled. Als de boom in brand staat zet
	 * hij de boom in brand en andersom. Als de kavel leeg is gebeurt er niets.
	 */
	public void toggleVuur(int rij, int kolom) {
		if (kavels[rij][kolom].voortBranden()) {
			if (kavels[rij][kolom] instanceof AppelBoom) {
				kavels[rij][kolom] = new AppelBoom(Boom.NIET_IN_BRAND);
			} else if (kavels[rij][kolom] instanceof BraamStruik) {
				kavels[rij][kolom] = new BraamStruik(Boom.NIET_IN_BRAND);
			} else if (kavels[rij][kolom] instanceof Cypres) {
				kavels[rij][kolom] = new Cypres(Boom.NIET_IN_BRAND);
			}
		} else {
			if (kavels[rij][kolom] instanceof AppelBoom) {
				kavels[rij][kolom] = new AppelBoom(Boom.IN_BRAND);
			} else if (kavels[rij][kolom] instanceof BraamStruik) {
				kavels[rij][kolom] = new BraamStruik(Boom.IN_BRAND);
			} else if (kavels[rij][kolom] instanceof Cypres) {
				kavels[rij][kolom] = new Cypres(Boom.IN_BRAND);
			}
		}
	}

	/**
	 * Methode om de boswachter op een positie te plaatsen of weg te halen.
	 */
	public void toggleBoswachter(int rij, int kolom) {
		if (posBoswachters[rij][kolom] instanceof Boswachter) {
			posBoswachters[rij][kolom] = null;
		} else
			posBoswachters[rij][kolom] = new Boswachter(this, rij, kolom);
	}

	/**
	 * Methode die de wereld naar de originele toestand terugzet.
	 */
	public void reset() {
		kavels = kavels_original;
		posBoswachters = new Boswachter[0][0];
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
	 * (alle kavels van) grond naar de volgende toestand updaten (waarbij kavels
	 * gebruik kunnen maken van de methode omgeving, zie onder, om `zichzelf' te
	 * kunnen updaten)
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
				volgendeKavels[rij][kolom] = kavels[rij][kolom]
						.volgendeToestand(this, rij, kolom);
			}
		}

		// update het grid door de 'volgende toestand' de huidige toestand
		// te maken.
		kavels = volgendeKavels;
	}

	/**
	 * @param rij
	 *            ,kolom een coordinaat in de grond
	 * @return tweedimensionaal array van kavels die de omgeving van de kavel op
	 *         coordinaat rij,kolom vormen (zie de opdracht) met de kavel zelf
	 *         in het midden
	 */
	public IKavel[][] omgeving(int rij, int kolom) {
		// maak een leeg 3x3 grid aan
		IKavel[][] omgeving = new IKavel[3][3];

		// loop door elke positie van de omgeving heen
		for (int dRij = 0; dRij < 3; dRij++) {
			for (int dKolom = 0; dKolom < 3; dKolom++) {
				// de rij op het grid die op een bepaalde plek in het
				// omgevings-grid komt
				// is de rij van het kavel in het midden, plus de plek in het
				// omgeving-grid, min 1. Idem voor de kolom.
				int rijOmTeChecken = dRij + rij - 1;
				int kolomOmTeChecken = dKolom + kolom - 1;

				// we gebruiken de checkBuitenGrid-methode om te kijken of een
				// positie
				// buiten het grid valt.
				boolean buitenGrid = checkBuitenGrid(rijOmTeChecken,
						kolomOmTeChecken);

				// als een positie buiten het grid valt zetten we daar een
				// 'Leegte' kavel
				// neer (waar nooit iets groeit of brandt, en dus neutraal is
				// wat betreft
				// voortBomen / voortBranden). Anders zetten we het goede kavel
				// daar neer.
				omgeving[dRij][dKolom] = (buitenGrid) ? new Leegte()
						: kavels[rijOmTeChecken][kolomOmTeChecken];
			}
		}

		// we hebben het grid nu ingevuld en geven het terug
		return omgeving;
	}

	// checkt of een positie (rij, kolom) buiten het grid valt.
	// IK HEB DEZE EVEN PUBLIC GEMAAKT ZODAT IK ER VANUIT BOSWACHTER OOK BIJ KAN
	public boolean checkBuitenGrid(int rij, int kolom) {
		// een kavel valt buiten het grid als:
		// -het grid leeg is
		// -de rij of kolom kleiner is dan 0
		// -de rij of kolom groter is dan het aantal rijen/kolommen
		return (kavels.length == 0 || rij < 0 || rij >= kavels.length
				|| kolom < 0 || kolom >= kavels[0].length);
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