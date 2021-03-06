package bosbrand.model;

public class Boswachter implements IBoswachter {

	int kolom;
	int rij;
	IBosbrandModel grond;
	IKavel[][] kavels;
	int targetRij;
	int targetKolom;
	int aantalBrandendeBomen;
	double pZetStap = 0.50;
	int aantalGegetenStruiken;
	boolean misselijk;
	int overgeven = 0;

	Boswachter(IBosbrandModel grond, int rij, int kolom,
			int aantalGegetenStruiken, boolean misselijk) {
		this.rij = rij;
		this.kolom = kolom;
		this.grond = grond;
		kavels = grond.getKavels();
		this.aantalGegetenStruiken = aantalGegetenStruiken;
		this.misselijk = misselijk;
	}

	/**
	 * de rij waarop deze boswachter staat
	 * 
	 * @return de rij waarop deze boswachter staat
	 */
	public int getRij() {
		return rij;
	}

	/**
	 * de kolom waarop deze boswachter staat
	 * 
	 * @return de kolom waarop deze boswachter staat
	 */
	public int getKolom() {
		return kolom;
	}

	// In deze methode dooft de boswachter alle brandende bomen om hem heen.
	// Vervolgens wordt de brandende boom gezocht die in het minst aantal
	// stappen te bereiken is. Daarna zet de boswachter 1 stap in de richting
	// van deze brandende boom.
	public void update() {

		// de boswachter kan misselijk worden van het eten van braamstruiken.
		// Als hij misselijk is, gaat hij kotsen en blust hij voor 5 seconden
		// niet.
		if (pZetStap <= 0) {

			misselijk = true;
			System.out.println("*KOTS*");
		}

		// zodra de boswachter 5 seconden heeft gekotst, is hij niet meer
		// misselijk en gaat hij weer door met het blussen van bomen
		if (misselijk) {
			overgeven++;
			if (overgeven >= 50) {
				misselijk = false;
				pZetStap = 0.50;
			}

		}

		// als de boswachter niet misselijk is, dooft hij alles uit zijn
		// omgeving en looopt naar de dichtstbijzijnde brandende boom
		if (misselijk == false) {

			doofOmgeving();

			findClosestTree();

			// kijk of de kavel waar de boswachter op staat een
			// bramenstruik bevat. Als dit zo is, dan eet de boswachter deze
			// bramen op, en wordt het kavel een leeg kavel. Van de eerste 5
			// bramenstruiken krijgt hij meer energie en wordt hij sneller,
			// daarna wordt hij alleen maar langzamer door zijn dikke buik.
			// Op een gegeven moment is hij te misselijk om te lopen dus blijft
			// hij even stil staan om te kotsen.

			if (grond.getKavels()[rij][kolom] instanceof BraamStruik) {

				grond.getKavels()[rij][kolom] = new LeegKavel();
				aantalGegetenStruiken++;

				if (aantalGegetenStruiken <= 5) {
					pZetStap += 0.1;
				} else if (aantalGegetenStruiken > 5) {
					pZetStap -= 0.2;
				}

			}

			// eerst wordt gecontroleerd of er �berhaupt wel brandende bomen op
			// de
			// grond staan. Als dit het geval is worden de nieuwe rij en kolom
			// bepaald. Als dit niet het geval is veranderen rij en kolom niet
			// en
			// blijft de boswachter dus staan waar hij stond.

			if (aantalBrandendeBomen != 0) {

				// daarna wordt gecontroleerd of de boswachter de stap wel kan
				// zetten. Het is een moeilijk begaanbaar bos, dus dit lukt niet
				// altijd.
				if (Math.random() < pZetStap) {
					// eerst wordt de boswachter weggehaald op de plek waar hij
					// stond
					grond.toggleBoswachter(rij, kolom);
					// vervolgens worden de nieuwe rij en kolom bepaald
					rij = veranderRij();

					kolom = veranderKolom();
					// dan wordt de boswachter neergezet op de plek waar hij
					// moet
					// komen te staan.

					((Grond) grond).getBoswachterPos()[rij][kolom] = new Boswachter(
							grond, rij, kolom, aantalGegetenStruiken, false);

				}

			}
		}
	}

	// In deze methode worden alle bomen in de omgeving van de boswachter
	// geblust.
	private void doofOmgeving() {

		// maak een nieuw IKavel[][] object aan, met de waarde van de omgeving
		// van de kavel waar de boswachter op staat.

		IKavel[][] omgevingBoswachter = grond.omgeving(rij, kolom);
		// loop over deze omgeving, op zoek naar brandende bomen
		for (int i = 0; i < omgevingBoswachter.length; i++) {
			for (int j = 0; j < omgevingBoswachter[i].length; j++) {
				// voor elke boom in de omgeving wordt gekeken of deze in brand
				// staat. Zo ja, laat de boom dan
				// uitdoven dmv het aanroepen van de methode doof();

				if (omgevingBoswachter[i][j].voortBranden()) {
					omgevingBoswachter[i][j].doof();

				}

			}
		}

	}

	// Deze methode gaat op zoek naar de brandende boom die in het minst aantal
	// stappen te bereiken is.
	private void findClosestTree() {

		int boomRij;
		int boomKolom;
		// bepaal de maximumafstand die een boswachter tot een brandende
		// boom kan hebben.

		int maxAantalStappen = berekenAantalStappen(grond.getKavels().length,
				grond.getKavels()[0].length);

		// roep de 2D-array representatie van grond op, en stop deze in de
		// variabele kavelsBoswachter, van het type IKavel.

		IKavel[][] kavelsBoswachter = grond.getKavels();

		for (boomRij = 0; boomRij < kavelsBoswachter.length; boomRij++) {
			for (boomKolom = 0; boomKolom < kavelsBoswachter[boomRij].length; boomKolom++) {
				// controleer of de betreffende boom in brand staat en tel 1 op
				// bij het aantal brandende bomen
				if (kavelsBoswachter[boomRij][boomKolom].voortBranden()) {
					aantalBrandendeBomen++;
					// bereken het aantal stappen dat een boswachter
					// verwijderd is van de brandende boom. Hiertoe moet eerst
					// als het ware een vierkant worden getekend op het kavel,
					// met als buitenste hoeken de boom en de boswachter. Een
					// lijn tussen de boom en de boswachter vormt de diagonaal
					// van dit vierkant.

					int verschilRij = Math.abs(rij - boomRij);
					int verschilKolom = Math.abs(kolom - boomKolom);
					// het aantal stappen dat een boswachter verwijderd is van
					// de brandende boom wordt berekend door
					// berekenAantalStappen(int lengte, int breedte).
					int aantalStappen = berekenAantalStappen(verschilRij,
							verschilKolom);

					// controleer of de boom in minder stappen te bereiken
					// is dan de vorige boom

					if (aantalStappen < maxAantalStappen) {
						targetRij = boomRij;
						targetKolom = boomKolom;
						maxAantalStappen = aantalStappen;
					}

				}
			}
		}

	}

	// deze methode bepaalt de afstand tussen een
	// boswachter en een brandende boom
	private int berekenAantalStappen(int lengte, int breedte) {

		int AantalStappen = Math.max(lengte, breedte) - 1;
		return AantalStappen;
	}

	// bepaal wat de nieuwe rij is voor de boswachter
	private int veranderRij() {
		int rijVerschil = berekenRijVerschil();

		if (rijVerschil < 0) {
			rij++;
		}
		if (rijVerschil > 0) {
			rij--;
		}
		return rij;
	}

	// bepaal wat de nieuwe kolom is voor de boswachter
	private int veranderKolom() {

		int kolomVerschil = berekenKolomVerschil();
		if (kolomVerschil < 0) {
			kolom++;
		}

		if (kolomVerschil > 0) {
			kolom--;
		}
		return kolom;
	}

	// deze methode bepaalt de afstand in rijen tot de dichtstbijzijnde
	// brandende boom
	private int berekenRijVerschil() {
		int rijVerschil = rij - targetRij;
		return rijVerschil;
	}

	// deze methode bepaalt de afstand in kolommen tot de dichtstbijzijnde
	// brandende boom
	private int berekenKolomVerschil() {
		int kolomVerschil = kolom - targetKolom;
		return kolomVerschil;
	}

	/**
	 * stuur een bericht aan alle boswachters. U hoeft dit niet te
	 * implementeren, maar het kan handig zijn als U voor de bonus wilt gaan.
	 * 
	 * @param het
	 *            bericht dat verstuurd wordt
	 */
	public void stuurBericht(String bericht) {
	}

	/**
	 * haal alle berichten op die zijn verstuurd sinds de laatste keer dat deze
	 * actie werd uitgevoerd. U hoeft dit niet te implementeren, maar het kan
	 * handig zijn als U voor de bonus wilt gaan.
	 */
	public void ontvangBerichten() {
	}

}