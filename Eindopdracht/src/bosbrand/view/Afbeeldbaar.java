package bosbrand.view;

import java.awt.*;

public class Afbeeldbaar implements IAfbeeldbaar {
	private int zijde;
	private int rij;
	private int kolom;
	private char color;
	
	// TODO middelpunt moet nog in midden.
	
	public Afbeeldbaar(int rij, int kolom, char kleur) {
		color = kleur;
		zijde = 10;
		this.rij = rij;
		this.kolom = kolom;
	}

	/**
	 * Mehtod eom de kleur te bepalen aan de hand van de meegegeven letterwaarde.
	 */
	public Color getColor() {
		Color kleurtje;
		switch (color) {
			case 'R': 	kleurtje = Color.red; 
						break;
			case 'S': 	kleurtje = Color.green; 
						break;
			default: 	kleurtje = Color.yellow; 
						break;
		}
		return kleurtje;
	}
	
	public int getZijde() {
		return zijde;
	}

	public int getX() {
		// TODO: shit
		return rij;
	}

	public int getY() {
		// TODO: shit
		return kolom;
	}

}
