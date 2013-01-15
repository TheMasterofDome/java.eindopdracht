package bosbrand.model;
public class LeegKavel implements IKavel {
	/**
	 * volgende toestand (zie de opdracht) geven voor een kavel 
	 * @param grond waarin zich deze kavel bevindt
	 * @param rij,kolom coordinaten van deze kavel 
	 * @return nieuw kavel, afhankelijk van deze kavel en diens omgeving in de grond
	 * (hint: gebruik de methode omgeving)
	 */
	public IKavel volgendeToestand(IGrond grond,int rij,int kolom) {
		// maak een array aan van 8 mogelijke volgende toestanden
		IKavel[] mogelijkeVolgenden = new IKavel[8];
		
		// vul de array met lege kavels
		for (int n = 0; n < mogelijkeVolgenden.length; n++) {
			mogelijkeVolgenden[n] = new LeegKavel();
		}
		
		// een int om als cursor te gebruiken
		int cursor = 0;
		
		// neem de omgeving van dit kavel en zet het in de 'omgeving' array
		IKavel[][] omgeving = grond.omgeving(rij, kolom);
		
		// loop door elke positie in de omgeving heen
		for (int dRij = 0; dRij < 3; dRij++) {
			for (int dKolom = 0; dKolom < 3; dKolom++) {
				// laat elk veld zichzelf 'voortbomen', als hier een boom staat en
				// hij geeft een 'stekje' terug dan krijgen we een boom terug,
				// anders null
				IBoom mogelijkeBoom = omgeving[dRij][dKolom].voortBomen();
				
				// als het niet null is, zetten we de boom in de array met mogelijke
				// volgende toestanden. We zetten hem op de positie van de cursor en
				// verhogen de cursor met 1. We hoeven dit kavel zelf niet specifiek
				// af te vangen omdat het leeg is en dus altijd null teruggeeft.
				if (mogelijkeBoom != null) {
					mogelijkeVolgenden[cursor] = mogelijkeBoom;
					cursor++;
				}
			}
		}
		// we hebben nu een mogelijkeVolgenden-array waar elke boom in de omgeving
		// een kans heeft gehad om een stekje te leveren. We kiezen hier nu een
		// random element uit om alle bomen gelijke kansen te geven om zich voort
		// te planten. Als er meer bomen staan zal de kans groter zijn dat er hier
		// een nieuwe boom komt.
		int keuze = new java.util.Random().nextInt(8);
		
		return mogelijkeVolgenden[keuze];
	}

	/**
	 * @return geeft true desda deze kavel in brand staat 
	 */
	public boolean voortBranden() {
		// een leeg kavel staat nooit in brand
		return false;
	}

	/**
	 * @return als deze methode voor een kavel aangeroepen wordt
	 * en deze kavel is begroeid met een soort boom en 
	 * en de boom staat niet in brand, dan geeft dit met
	 * een kans pGroei een nieuwe boom van hetzelfde soort terug
	 * in alle andere gevallen null
	 */
	public IBoom voortBomen() {
		// een leeg kavel plant zich nooit voort
		return null;
	}
	
	// de representatie van een leeg kavel is een punt.
	public String toString() {
		return ".";
	}
	
	public void doof(){
		
	}
	
	public void steekAan(){
		
	}
}