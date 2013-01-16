package bosbrand.view;

import java.awt.*;

public class Afbeeldbaar implements IAfbeeldbaar {
	public static final int zijde = 50;
	private int x;
	private int y;
	private char color;
	
	// TODO middelpunt moet nog in midden.
	
	public Afbeeldbaar(int x, int y, char kleur) {
		color = kleur;
		this.x = x;
		this.y = y;
	}

	/**
	 * Mehtod eom de kleur te bepalen aan de hand van de meegegeven letterwaarde.
	 */
	public Color getColor() {
		Color kleurtje;
		switch (color) {
			case 'R': 	kleurtje = Color.red; 
						break;
			case 'G': 	kleurtje = Color.green; 
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
		return x;
	}

	public int getY() {
		return y;
	}

}
