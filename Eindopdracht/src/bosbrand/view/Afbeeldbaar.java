package bosbrand.view;

import java.awt.*;

public class Afbeeldbaar implements IAfbeeldbaar {
	public int zijde;
	private int x;
	private int y;
	private char color;

	public Afbeeldbaar(int x, int y, char kleur, int z) {
		zijde = z;
		color = kleur;
		this.x = x;
		this.y = y;
	}

	/**
	 * Methode om de kleur te bepalen aan de hand van de meegegeven
	 * letterwaarde. Verschillende soorten bomen hebben verschillende kleuren.
	 * Een boom met een felle kleur staat wel in brand, een boom met een donkere
	 * kleur niet.
	 */
	public Color getColor() {
		Color kleurtje;
		switch (color) {
		case 'A':
			// felrood

			kleurtje = new Color(255, 0, 0);

			break;
		case 'a':
			// donkerrood
			
			kleurtje = new Color(205, 0, 0);
			break;
		case 'B':
			// felpaars
			kleurtje = new Color(219, 45, 123);

			break;
		case 'b':
			// donkerpaars
			kleurtje = new Color(165, 29, 90);

			break;
		case 'C':
			// felgroen
			kleurtje = new Color(127, 255, 0);

			break;
		case 'c':
			// donkergroen
			kleurtje = new Color(69, 139, 0);
			break;
		case 'P':
			kleurtje = Color.cyan;
			break;
		case 'L':
			// bruin
			kleurtje = new Color(139, 69, 19);
		default:
			// bruin
			kleurtje = new Color(139, 69, 19);
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
