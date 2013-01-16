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
	
	private Color setKleur(char c) {
		return null;
	}

	//Werkt niet. Waarom is een raadsel.
	public Color getColor() {
		Color kleurtje;
		switch (color) {
			case 'R': 	kleurtje = Color.red; 
						System.out.println("lol"); 
						break;
			case 'S': 	kleurtje = Color.green; 
						System.out.println("lol2"); 
						break;
			default: 	kleurtje = Color.yellow; 
						System.out.println("loldef");
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
