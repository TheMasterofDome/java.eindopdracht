package bosbrand.model;
public abstract class Boom implements IBoom {
	// twee constanten om inbrandheid uit te drukken. Het keyword 'final' zegt dat
	// deze waarden niet veranderd kunnen worden tijdens het draaien van het
	// programma.
	public static final boolean IN_BRAND = true;
	public static final boolean NIET_IN_BRAND = false;
	
	// een int die bijhoudt hoe lang een boom al in brand staat
	private int tijdInBrand;
	
	// boolean die bijhoudt of een boom in brand staat.
	protected boolean inBrand;
	
	// double met de kans op groei van een boom. Krijgt een waarde in de constructor
	// van de specifieke boom.
	protected double pGroei;
	
	// double met de kans dat een boom in brand vliegt. Krijgt een waarde in de
	// constructor van de specifieke boom.
	protected double pOntvlam;
	
	/**
	 * volgende toestand (zie de opdracht) geven voor een kavel 
	 * @param grond waarin zich deze kavel bevindt
	 * @param rij,kolom coordinaten van deze kavel 
	 * @return nieuw kavel, afhankelijk van deze kavel en diens omgeving in de grond
	 * (hint: gebruik de methode omgeving)
	 */
	public IKavel volgendeToestand(IGrond grond,int rij,int kolom) {
		// we splitsen dit even in twee methoden. Als een boom in brand staat geven
		// we het resultaat van de volgendeBrandend-methode terug, anders het
		// resultaat van volgendeNormaal.
		if (inBrand) return volgendeBrandend();
		else return volgendeNormaal(grond, rij, kolom);
	}
	
	// volgendeBrandend bekijkt wat de volgende toestand is van een brandende boom
	private IKavel volgendeBrandend() {
		// de kans dat een boom uitgaat is 1/40 * het aantal beurten dat hij al in
		// brand staat
		double pUitGaan = 0.025d * tijdInBrand;
		
		// we testen of de boom uit gaat
		if (Math.random() < pUitGaan) {
			// zo ja, doen we nog een test. Als de boom binnen 5 beurten nadat hij
			// in brand ging weer uit is, dan overleeft hij het. Anders wordt dit
			// een leeg kavel.
			if (Math.random() <0.1d) return kopie();
			else return new LeegKavel();
		} else {
			// zo nee, verhogen we het aantal beurten dat de boom in brand staat
			// met 1 en geven we de boom zelf terug als zijn volgende toestand.
			tijdInBrand++;
			return this;
		}
	}
	
	private IKavel volgendeNormaal(IGrond grond, int rij, int kolom) {
		// we maken een kopie van onszelf om terug te gaan geven. We casten
		// deze naar Boom om hem in brand te kunnen steken. Dit is een veilige
		// cast omdat we weten dat dit een object is van type Boom, en de kopie
		// dat dus ook wel gaat zijn.
		Boom volgendeBoom = (Boom) kopie();
		
		// neem de omgeving van dit kavel en zet het in de 'omgeving' array
		IKavel[][] omgeving = grond.omgeving(rij, kolom);
		
		// loop door elke positie in de omgeving heen
		for (int dRij = 0; dRij < 3; dRij++) {
			for (int dKolom = 0; dKolom < 3; dKolom++) {
				// laat elk veld aangeven of het in brand staat.
				boolean staatInBrand = omgeving[dRij][dKolom].voortBranden();
				
				// als het in brand staat, is er een kans dat dit kavel ook
				// in brand vliegt. We testen daar op en, als het lot het zo
				// wil, zetten we de kopie ook in brand.
				if (staatInBrand && Math.random() < pOntvlam) {
					volgendeBoom.inBrand = IN_BRAND;
				}
			}
		}
		
		// we geven de kopie terug.
		return volgendeBoom;
		
	}
	
	/**
	 * @return geeft true desda deze kavel in brand staat 
	 */
	public boolean voortBranden() {
		// het resultaat staat in de boolean inBrand.
		return inBrand;
	}
	
	/**
	 * @return als deze methode voor een kavel aangeroepen wordt
	 * en deze kavel is begroeid met een soort boom en 
	 * en de boom staat niet in brand, dan geeft dit met
	 * een kans pGroei een nieuwe boom van hetzelfde soort terug
	 * in alle andere gevallen null
	 */
	public IBoom voortBomen() {
		// we testen tegen de kans pGroei, als dat goed gaat geven we een
		// kopie terug, anders null.
		if (Math.random() < pGroei) return kopie();
		else return null;
	}
	
	/**
	 * @return nieuwe boom (die niet in brand staat)
	 * van hetzelfde soort als deze boom
	 */
	public abstract IBoom kopie(); // implementeren we in de specifieke boomimplementaties
}
