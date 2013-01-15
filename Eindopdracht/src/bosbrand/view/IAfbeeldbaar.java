package bosbrand.view;

/**
 * Deze klasse definieert de eigenschappen die een object moet hebben om af te beelden te zijn door deze
 * view. Denk bijvoorbeeld aan een kleur en een positie.
 */
public interface IAfbeeldbaar {

	/**
	 * Deze methode geeft de kleur van het af te beelden object.
	 */
	public void getColor();
	
	/**
	 * Deze methode geeft de af te drukken letter.
	 */
	public char getLetter();
	
	/**
	 * Deze methode geeft de lijndikte van het af te beelden vierkant.
	 */
	public int getLijndikte();
	
	/**
	 * Deze methode geeft true desda er een boswachter op dit kavel staat.
	 */
	public boolean isThereBoswachter();
	
	/**
	 * Deze methode geeft de x-coordinaat van het af te beelden vlak.
	 * @param rij de rij waarin het kavel zich bevindt.
	 */
	public int getX(int rij);
	
	/**
	 * Deze methode geeft de y-coordinaat van het af te beelden vlak.
	 * @param kolom de kolom waarin het kavel zich bevindt.
	 */
	public int getY(int kolom);
}