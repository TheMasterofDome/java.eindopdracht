package bosbrand.view;

import java.awt.*;

public class Afbeeldbaar implements IAfbeeldbaar {
	private int zijde;
	private int rij;
	private int kolom;
	private Color kleur;
	
	// TODO middelpunt moet nog in midden.
	
	public Afbeeldbaar(int rij, int kolom, char kleur) {
		this.kleur = setKleur(kleur);
		zijde = 10;
		this.rij = rij;
		this.kolom = kolom;
	}
	
	private Color setKleur(char c) {
		if (c == 'R') {
			return Color.red;
		}
		else if (c == 'G') {
			return Color.green;
		}
		return Color.yellow;
	}

	public Color getColor() {
		return kleur;
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
