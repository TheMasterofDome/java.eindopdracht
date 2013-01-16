package bosbrand.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class TekenPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// buffered image waarop getekend wordt
	private BufferedImage bImage;
	
	/**
	 * tekent de bufferedimage op het panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bImage, 0, 0, null);
		g.dispose();
	}
	
	/**
	 * maakt het panel leeg door gewoon een nieuwe bufferedimage aan te maken
	 */
	void leegmaken() {
		//getWidth() en getLength() werken niet!!
		bImage = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
	}
	
	/**
	 * resized door de leegmakenmethode aan te roepen, die al precies doet wat we willen
	 */
	void resize() {
		leegmaken();
	}
	
	/**
	 * tekent een rechthoek op de buffered image
	 * @param xCoordinaat x-coordinaat van de linker bovenhoek van de rechthoek
	 * @param yCoordinaat y-coordinaat van de linker bovenhoek van de rechthoek
	 * @param breedte de breedte van de rechthoek
	 * @param hoogte de hoogte van de rechthoek
	 * @param kleur de kleur van de te tekenen rechthoek
	 */
	void tekenRechthoek(int xCoordinaat, int yCoordinaat, int breedte, int hoogte, Color kleur) {
		Graphics g = bImage.createGraphics();
		
		g.setColor(kleur);
		g.fillRect(xCoordinaat, yCoordinaat, breedte, hoogte);
		g.dispose();
	}
}