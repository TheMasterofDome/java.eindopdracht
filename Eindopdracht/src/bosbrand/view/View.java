package bosbrand.view;

import bosbrand.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View implements IView, ComponentListener {
	// het feitelijke venster
	private JFrame jfMainFrame;
	
	// het paneel waarop getekend wordt
	private TekenPanel dpTekenGebied;
	
	// de controller die deze view en het bijbehorende model bestuurt
	private IController controller;
	
	/**
	 * Constructor die het frame en de controls erop aanmaakt
	 * @param controller de controller die deze view en het bijbehorende model bestuurt
	 */
	public View(IController controller) {
		this.controller = controller;
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createUI();
				showUI();
			}
		});
	}
	
	/**
	 * middelpunt
	 * @return een java.awt.Point object dat het midden van het af te beelden gebied
	 * weergeeft.
	 */
	public Point getMidden() {
		// TODO: geef een middelpunt terug in plaats van null
		return null;
	}
	
	/**
	 * geef middenpunt
	 * @param een java.awt.Point object dat het midden van het af te beelden gebied
	 * weergeeft.
	 */
	public void setMidden(Point midden) {
		// TODO: bewaar een middelpunt
	}
	
	/**
	 * afbeelden van de meegegeven data, met het 'midden' punt in het midden van het veld en
	 * met een hoogte en breedte die leegmaken afhangen van 'zoom'
	 * @param afTeBeeldenData een array met afbeeldbare data
	 */
	public void afbeelden(IAfbeeldbaar[] afTeBeeldenData) {
		// TODO: maak het tekenpanel leeg
		
		// TODO: loop door de array met data, en beeld elk element correct af op het tekenpanel met behulp
		// van de tekenRechthoek-methode
		
		// beeld het tekenpanel opnieuw af
		dpTekenGebied.repaint();
	}
	
	/**
	 * Maakt het frame aan en voegt alle controls er aan toe
	 */
	private void createUI() {
		// maak een nieuw frame aan met de naam "Bosbrand"
		jfMainFrame = new JFrame("Bosbrand");
		// voeg deze klasse zelf toe als componentlistener, wordt alleen gebruikt voor resizen van het
		// frame
		jfMainFrame.addComponentListener(this);
		// als het frame gesloten wordt, houdt het programma op met draaien
		jfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// zet de content pane van het frame in een variabele
		Container content = jfMainFrame.getContentPane();

		// definieer layout manager voor onze content pane
		content.setLayout(new BorderLayout());
		
		// maak een panel voor de knoppen met een boxlayout, en voeg het aan de rechterkant toe aan het 
		// frame
		JPanel pButtonArea = new JPanel();
		pButtonArea.setLayout(new BoxLayout(pButtonArea, BoxLayout.Y_AXIS));
		content.add(pButtonArea, BorderLayout.LINE_END);
		
		// maak de reset-knop
		JButton btnGenerate = new JButton("Reset");
		pButtonArea.add(btnGenerate);
		
		// TODO: voeg een action listener toe voor de reset-knop
		
		// maak de simulate-knop
		JButton btnSimulate = new JButton("Simuleer");
		pButtonArea.add(btnSimulate);
		
		// TODO: voeg een action listener toe voor de simulate-knop
		
		// maak een panel om op te tekenen
		dpTekenGebied = new TekenPanel();
		content.add(dpTekenGebied, BorderLayout.CENTER);
		
		// TODO: voeg een mouseListener en een mouseMotionListener toe voor het panel om op te tekenen
	}
	
	/**
	 * Geeft het frame een initiële grootte en maakt het zichtbaar.
	 */
	private void showUI() {
		jfMainFrame.setSize(1024,768);
		jfMainFrame.setVisible(true);
	}
	
	/**
	 * vertelt het tekenpanel om zijn resizecode te draaien, en laat daarna de controller het beeld opnieuw
	 * afbeelden
	 */
	public void componentResized(ComponentEvent e) {
		dpTekenGebied.resize();
		controller.afbeelden();
	}
	public void componentHidden(ComponentEvent e){}
	public void componentShown(ComponentEvent e){}
	public void componentMoved(ComponentEvent e){}
	
	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is twee keer zo groot of twee
	 * keer zo klein, afhankelijk van de implementatie).
	 * @param zoomniveau als ratio ten opzichte van normaal
	 */
	public void setZoom(double zoom) {
		//BONUS: voeg code toe om netjes te kunnen zoomen
	}
	
	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is twee keer zo groot of twee
	 * keer zo klein, afhankelijk van de implementatie).
	 * @return zoomniveau als ratio ten opzichte van normaal
	 */
	public double getZoom() {
		//BONUS: voeg code toe om netjes te kunnen zoomen
		return 0.0d;
	}
	
	/**
	 * rotatie in radialen
	 * @return double die de rotatie in radialen geeft ten opzichte van neutraal
	 */
	public double getRotatie() {
		//BONUS: voeg code toe om netjes te kunnen roteren
		return 0.0d;
	}
	
	/**
	 * geef rotatie in radialen
	 * @param rotatie de rotatie in radialen ten opzichte van neutraal
	 */
	public void setRotatie(double dRotatie) {
		//BONUS: voeg code toe om netjes te kunnen roteren
	}
}