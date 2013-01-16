package bosbrand.view;

import bosbrand.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View implements IView, ComponentListener {

	// het punt dat het middelpunt moet worden van het af te beelden venster
	Point midden;
	// het feitelijke venster
	private JFrame jfMainFrame;

	// het paneel waarop getekend wordt
	private TekenPanel dpTekenGebied;

	// de controller die deze view en het bijbehorende model bestuurt
	private IController controller;

	/**
	 * Constructor die het frame en de controls erop aanmaakt.
	 * 
	 * @param controller
	 *            de controller die deze view en het bijbehorende model bestuurt
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
	 * middelpunt van het venster.
	 * 
	 * @return een java.awt.Point object dat het midden van het af te beelden
	 *         gebied weergeeft.
	 */
	public Point getMidden() {
		return midden;
	}

	/**
	 * stel middenpunt in
	 * 
	 * @param een
	 *            java.awt.Point object dat het midden van het af te beelden
	 *            gebied weergeeft.
	 */
	public void setMidden(Point midden) {
		this.midden = midden;
	}

	/**
	 * afbeelden van de meegegeven data, met het 'midden' punt in het midden van
	 * het veld en met een hoogte en breedte die leegmaken afhangen van 'zoom'
	 * 
	 * @param afTeBeeldenData
	 *            een array met afbeeldbare data
	 */
	public void afbeelden(IAfbeeldbaar[] afTeBeeldenData) {
		// Venster leegmaken
		dpTekenGebied.leegmaken();

		// Loop door de array en beeld elk kavel af met behulp van zijn
		// Afbeeldbaar object.
		for (IAfbeeldbaar beelden : afTeBeeldenData) {
			dpTekenGebied.tekenRechthoek(beelden.getX(), beelden.getY(),
					beelden.getZijde(), beelden.getZijde(), beelden.getColor());
		}

		// beeld het tekenpanel opnieuw af
		dpTekenGebied.repaint();
	}

	/**
	 * Maakt het frame aan en voegt alle controls er aan toe
	 */
	private void createUI() {
		// maak een nieuw frame aan met de naam "Bosbrand"
		jfMainFrame = new JFrame("Bosbrand");
		// voeg deze klasse zelf toe als componentlistener, wordt alleen
		// gebruikt voor resizen van het
		// frame
		jfMainFrame.addComponentListener(this);
		// als het frame gesloten wordt, houdt het programma op met draaien
		jfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// zet de content pane van het frame in een variabele
		Container content = jfMainFrame.getContentPane();

		// definieer layout manager voor onze content pane
		content.setLayout(new BorderLayout());

		// maak een panel voor de knoppen met een boxlayout, en voeg het aan de
		// rechterkant toe aan het
		// frame
		JPanel pButtonArea = new JPanel();
		pButtonArea.setLayout(new BoxLayout(pButtonArea, BoxLayout.Y_AXIS));
		content.add(pButtonArea, BorderLayout.LINE_END);

		// maak de reset-knop en roep de methode doeReset() aan als op deze knop
		// wordt gedrukt
		// TO DO: zorgen dat "reset" doeReset aanroept en dat "Simuleer"
		// doeSimuleer aanroept.
		// nu: bij Simuleer gebeurt niks, bij Reset worden ze allebei
		// aangeroepen
		JButton btnGenerate = new JButton("Reset");
		pButtonArea.add(btnGenerate);
		ActionListener listenerGenerate = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.doeReset();
			}
		};
		btnGenerate.addActionListener(listenerGenerate);

		// maak de simulate-knop en roep de methode doeSimuleer() aan als op
		// deze knop wordt gedrukt
		JButton btnSimulate = new JButton("Simuleer");
		pButtonArea.add(btnSimulate);
		ActionListener listenerSimulate = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.doeSimuleer();
			}

		};

		btnGenerate.addActionListener(listenerSimulate);

		// maak een panel om op te tekenen
		dpTekenGebied = new TekenPanel();
		content.add(dpTekenGebied, BorderLayout.CENTER);

		MouseListener muis = new MouseListener() {
			// Als op de muis wordt geklikt, controleer dan op welke muisknop
			// (rechts of links) er wordt geklikt en roep de bijbehorende
			// methode aan
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					// TO DO: rij en kolom uitrekenen
					controller.toggleBoswachter(1, 2);
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					// TO DO: rij en kolom uitrekenen
					controller.toggleBoswachter(1, 2);
				}
			}

			public void mousePressed(MouseEvent e) {
				// TO DO: beeld mee laten bewegen met de cursor
			}
			
			public void mouseReleased(MouseEvent e){
				// TO DO: methode schrijven die verantwoordelijk is voor het
				// verplaatsen van het venster bij slepen. Die methode hier
				// aanroepen op controller
			}
			
			public void mouseEntered(MouseEvent e){
				// volgens mij hoeven we hier niks mee
			}
			
			public void mouseExited(MouseEvent e){
				// volgens mij hoeven we hier niks mee
			}
			
		};

		dpTekenGebied.addMouseListener(muis);
		
		// TODO: voeg een mouseListener en een mouseMotionListener toe voor het
		// panel om op te tekenen
	}

	/**
	 * Geeft het frame een initiële grootte en maakt het zichtbaar.
	 */
	private void showUI() {
		jfMainFrame.setSize(1024, 768);
		jfMainFrame.setVisible(true);
	}

	/**
	 * vertelt het tekenpanel om zijn resizecode te draaien, en laat daarna de
	 * controller het beeld opnieuw afbeelden
	 */
	public void componentResized(ComponentEvent e) {
		dpTekenGebied.resize();
		controller.afbeelden();
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is
	 * twee keer zo groot of twee keer zo klein, afhankelijk van de
	 * implementatie).
	 * 
	 * @param zoomniveau
	 *            als ratio ten opzichte van normaal
	 */
	public void setZoom(double zoom) {
		// BONUS: voeg code toe om netjes te kunnen zoomen
	}

	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is
	 * twee keer zo groot of twee keer zo klein, afhankelijk van de
	 * implementatie).
	 * 
	 * @return zoomniveau als ratio ten opzichte van normaal
	 */
	public double getZoom() {
		// BONUS: voeg code toe om netjes te kunnen zoomen
		return 0.0d;
	}

	/**
	 * rotatie in radialen
	 * 
	 * @return double die de rotatie in radialen geeft ten opzichte van neutraal
	 */
	public double getRotatie() {
		// BONUS: voeg code toe om netjes te kunnen roteren
		return 0.0d;
	}

	/**
	 * geef rotatie in radialen
	 * 
	 * @param rotatie
	 *            de rotatie in radialen ten opzichte van neutraal
	 */
	public void setRotatie(double dRotatie) {
		// BONUS: voeg code toe om netjes te kunnen roteren
	}
}