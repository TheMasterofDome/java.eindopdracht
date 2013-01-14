package bosbrand.model;

import bosbrand.view.*;

public interface IBosbrandModel extends IGrond {
	/**
	 * initialiseer een wereld. De Input is zoals aangegeven in opdracht 3.
	 * @param wereld een string array die aangeeft hoe de wereld eruit ziet
	 */
	public void initialiseer(String[] wereld);
	
	/**
	 * geeft de kavels van de wereld terug
	 * @return een array van kavel-arrays die de wereld als een grid representeren
	 */
	public IKavel[][] getKavels();
	/**
	 * geeft de boswachters in de wereld terug
	 * @return een array met alle boswachters die in de wereld zijn
	 */
	public IBoswachter[] getBoswachters();
	
	/**
	 * zet vuur aan- of uit op een positie in de wereld. Als het aan is, gaat het uit. Als
	 * het uit is, gaat het aan.
	 * @param rij de rij in het grid waar vuur ge'toggled' dient te worden
	 * @param kolom de kolom in het grid waar vuur ge'toggled' dient te worden
	 */
	public void toggleVuur(int rij, int kolom);
	/**
	 * voeg een boswachter toe, of verwijder een boswachter in de wereld. Als er een
	 * boswachter op deze positie is, verwijder hem dan. Als er geen boswachter is, voeg hem
	 * dan toe.
	 * @param rij de rij in het grid waar een boswachter ge'toggled' dient te worden
	 * @param kolom de kolom in het grid waar een boswachter ge'toggled' dient te worden
	 */
	public void toggleBoswachter(int rij, int kolom);
	
	/**
	 * zet de wereld weer terug naar de begintoestand. (Verwijder ook alle boswachters)
	 */
	public void reset();
}