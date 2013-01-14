package bosbrand.model;
// dit is de ijzige leegte aan de rand van de wereld. Er groeit niks, er brandt niks,
// er is slechts stilte en de leegste leegheid die je je maar voor kunt stellen. In
// de praktijk is het een neutraal object dat als rand kan dienen, het wordt gebruikt
// in de omgeving-methode van Grond.

public class Leegte implements IKavel {
	/**
	 * volgende toestand (zie de opdracht) geven voor een kavel 
	 * @param grond waarin zich deze kavel bevindt
	 * @param rij,kolom coordinaten van deze kavel 
	 * @return nieuw kavel, afhankelijk van deze kavel en diens omgeving in de grond
	 * (hint: gebruik de methode omgeving)
	 */
	public IKavel volgendeToestand(IGrond grond,int rij,int kolom) {
		// dit zou nooit aangeroepen moeten worden, want de leegte is geen onderdeel
		// van het grid.
		return null;
	}
	
	/**
	 * @return geeft true desda deze kavel in brand staat 
	 */
	public boolean voortBranden() {
		// de leegte brandt nooit.
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
		// de leegte plant zich nooit voort.
		return null;
	}
	
	// de representatie van de ijzige leegte is een ijzige bergtop.
	public String toString() {
		return "^";
	}
}