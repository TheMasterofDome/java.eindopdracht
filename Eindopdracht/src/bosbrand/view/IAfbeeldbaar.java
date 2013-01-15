package bosbrand.view;

import java.awt.Color;

/**
 * Deze klasse definieert de eigenschappen die een object moet hebben om af te beelden te zijn door deze
 * view. Denk bijvoorbeeld aan een kleur en een positie.
 */
public interface IAfbeeldbaar {

	/**
	 * Deze methode geeft de kleur van het af te beelden object.
	 */
	public Color getColor();
	
	/**
	 * Deze methode geeft de lengte van de zijden van het af te drukken vierkant.
	 */
	public int  getZijde();
	
	/**
	 * Deze methode geeft de x-coordinaat van het af te beelden vlak.
	 * @param rij de rij waarin het kavel zich bevindt.
	 */
	public int getX();
	
	/**
	 * Deze methode geeft de y-coordinaat van het af te beelden vlak.
	 * @param kolom de kolom waarin het kavel zich bevindt.
	 */
	public int getY();
}