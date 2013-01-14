package bosbrand.view;

import java.awt.*;

public interface IView {
	/**
	 * middelpunt
	 * @return een java.awt.Point object dat het midden van het af te beelden gebied weergeeft.
	 */
	public Point getMidden();
	
	/**
	 * geef middelpunt
	 * @param een java.awt.Point object dat het midden van het af te beelden gebied weergeeft.
	 */
	public void setMidden(Point midden);
	
	/**
	 * afbeelden van de meegegeven data, met het 'midden' punt in het midden van het veld en met een hoogte
	 * en breedte die leegmaken afhangen van 'zoom'
	 * @param afTeBeeldenData een array met afbeeldbare data
	 */
	public void afbeelden(IAfbeeldbaar[] afTeBeeldenData);
	
	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is twee keer zo groot of twee
	 * keer zo klein, afhankelijk van de implementatie).
	 * @return zoomniveau als ratio ten opzichte van normaal
	 */
	public double getZoom();
	
	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is twee keer zo groot of twee
	 * keer zo klein, afhankelijk van de implementatie).
	 * @param zoomniveau als ratio ten opzichte van normaal
	 */
	public void setZoom(double zoom);
	
	/**
	 * rotatie in radialen
	 * @return double die de rotatie in radialen geeft ten opzichte van neutraal
	 */
	public double getRotatie();
	
	/**
	 * geef rotatie in radialen
	 * @param rotatie de rotatie in radialen ten opzichte van neutraal
	 */
	public void setRotatie(double rotatie);
}