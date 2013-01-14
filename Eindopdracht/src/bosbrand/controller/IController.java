package bosbrand.controller;

public interface IController {
	/**
	 * reset het model
	 */
	public void doeReset();
	
	/**
	 * begin of stop met het draaien van de simulatie
	 */
	public void doeSimuleer();
	
	/**
	 * beeld het model af op de view
	 */
	public void afbeelden();
	
	/**
	 * voeg een boswachter toe, of verwijder een boswachter in het model. Als er een
	 * boswachter op deze positie is, verwijder hem dan. Als er geen boswachter is, voeg hem
	 * dan toe.
	 * @param rij de rij in het grid waar een boswachter ge'toggled' dient te worden
	 * @param kolom de kolom in het grid waar een boswachter ge'toggled' dient te worden
	 */
	public void toggleBoswachter(int rij, int kolom);
	/**
	 * zet vuur aan- of uit op een positie in het model. Als het aan is, gaat het uit. Als
	 * het uit is, gaat het aan.
	 * @param rij de rij in het grid waar vuur ge'toggled' dient te worden
	 * @param kolom de kolom in het grid waar vuur ge'toggled' dient te worden
	 */
	public void toggleVuur(int rij, int kolom);
}