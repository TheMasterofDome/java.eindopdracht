package bosbrand.model;

public class Boswachter implements IBoswachter {

	int kolom;
	int rij;
	IGrond grond;
	IKavel[][] kavels;

	Boswachter(IGrond grond, int rij, int kolom) {
		this.rij = rij;
		this.kolom = kolom;
		this.grond = grond;
		this.kavels = kavels;
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

	/**
	 * doof alle branden om de boswachter heen, en laat hem een actie uitvoeren
	 * om effectief volgende branden te bestrijden.
	 */
	public void update() {

		// in deze methode wordt het aantal brandende bomen bijgehouden. Zet
		// deze eerst op nul.
		int aantalBrandendeBomen = 0;
		// maak een nieuw IKavel[][] object aan, met de waarde van de omgeving
		// van de kavel waar de boswachter op staat.

		IKavel[][] omgevingBoswachter = grond.omgeving(rij, kolom);
		// loop over deze omgeving, op zoek naar brandende bomen
		for (int i = 0; i < omgevingBoswachter.length; i++) {
			for (int j = 0; j < omgevingBoswachter[i].length; j++) {
				// als er een brandende boom gevonden wordt, laat de boom dan
				// uitdoven dmv het aanroepen van de methode doof();
				// houd ondertussen ook het aantal brandende bomen bij.

				if (omgevingBoswachter[i][j].voortBranden()) {
					omgevingBoswachter[i][j].doof();
					aantalBrandendeBomen++;

				}

			}
		}

		// als de boswachter niet geblust heeft (en het aantal brandende bomen
		// dus nul is), gaat de boswachter lopen in de richting van de
		// dichtstbijzijnde in brand staande boom. Hiertoe wordt eerst om de
		// eerste ring om omgeving gezocht naar een brandende boom, dan om de
		// tweede, etc. Dit zoeken wordt in een andere methode gedaan, genaamd
		// zoekBoom(rij, kolom)
		// OPMERKING: MOET NOG WORDEN OPGESPLITST IN APARTE METHODES, DIT IS
		// VEEL TE LANG

		if (aantalBrandendeBomen == 0) {
			// de boswachter loopt door het veld heen, links-->rechts,
			// boven-->onder. Zodra hij een brandende boom tegenkomt die
			// dichterbij hem is dan de vorige, wordt dat het nieuwe target. Het
			// target wordt eerst op [0][0] gezet.

			int boswachterRij;
			int boswachterKolom;
			// uitzoeken hoe te initialiseren, dit klopt niet als de boswachter
			// in de linker bovenhoek staat
			int boomRij = 0;
			int boomKolom = 0;
			IKavel target = kavels[boomRij][boomKolom];

			for (boswachterRij = 0; boswachterRij < kavels.length; boswachterRij++) {
				for (boswachterKolom = 0; boswachterKolom < kavels[rij].length; boswachterKolom++) {
					if (kavels[boswachterRij][boswachterKolom].voortBranden()) {
						// controleer of de boom dichterbij staat dan de vorige
						// boom
						
						if (boswachterRij < boomRij
								|| boswachterKolom < boomKolom) {

							// target heb ik denk ik niet nodig? volgens mij
							// alleen boswachterRij en boswachterKolom nodig
							target = kavels[boswachterRij][boswachterKolom];
						}

					}
				}
			}

			// als het target is bepaald, zet de boswachter één stap in de
			// richting van dit target. Een boswachter mag schuin lopen.

			int rijVerschil = boomRij - rij;
			int kolomVerschil = boomKolom - kolom;

			// als de boom boven de boswachter is, gaat de boswachter 1 plaats
			// omhoog
			if (rijVerschil < 0) {
				rij--;

			}
			// als de boom onder de boswachter is, gaat de boswachter 1 plaats
			// omlaag
			if (rijVerschil > 0) {
				rij++;
			}

			// als de boom links van de boswachter is, gaat de boswachter 1
			// plaats naar links
			if (kolomVerschil < 0) {
				kolom--;
			}

			// als de boom rechts van de boswachter is, gaat de boswachter in
			// plaats naar rechts
			if (kolomVerschil > 0) {
				kolom++;
			}
		}

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
