package bosbrand.view;

import bosbrand.controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View implements IView, ComponentListener {
	private boolean moving;
	private Integer[] start_end_coords;
	Point midden;
	private JFrame jfMainFrame;
	private TekenPanel dpTekenGebied;
	private IController controller;

	/**
	 * Constructor die het frame en de controls erop aanmaakt. Ook stelt hij het
	 * midden in en initialiseert hij de rest van de variabelen.
	 * 
	 * @param controller
	 *            die deze view en het bijbehorende model bestuurt
	 */
	public View(IController controller) {
		moving = false;
		this.controller = controller;
		midden = new Point();
		start_end_coords = new Integer[4];

		setMidden(midden);

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
	 * De breedte is de breedte van het venster minus 102 pixels, wat in de
	 * praktijk de breedte van de Box met JButtons is, gedeeld door twee. De
	 * hoogte s de hoogte van het venster gedeeld door twee.
	 * 
	 * @param een
	 *            java.awt.Point.
	 */
	public void setMidden(Point midden) {
		int width = (1024 - 102) / 2;
		int height = 768 / 2;
		midden.setLocation(width, height);
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
		// gebruikt voor resizen van het frame
		jfMainFrame.addComponentListener(this);
		// als het frame gesloten wordt, houdt het programma op met draaien
		jfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// zet de content pane van het frame in een variabele
		Container content = jfMainFrame.getContentPane();

		// definieer layout manager voor onze content pane
		content.setLayout(new BorderLayout());

		// maak een panel voor de knoppen met een boxlayout, en voeg het aan de
		// rechterkant toe aan het frame
		JPanel pButtonArea = new JPanel();
		pButtonArea.setLayout(new BoxLayout(pButtonArea, BoxLayout.Y_AXIS));
		content.add(pButtonArea, BorderLayout.LINE_END);

		// maak de reset-knop en roep de methode doeReset() aan als op deze knop
		// wordt gedrukt
		// TO DO: zorgen dat "reset" doeReset aanroept en dat "Simuleer"
		// doeSimuleer aanroept.
		// nu: bij Simuleer gebeurt niks, bij Reset worden ze allebei
		// aangeroepen.
		JButton btnGenerate = new JButton("Reset");
		pButtonArea.add(btnGenerate);
		ActionListener listenerGenerate = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.doeReset();
			}
		};
		btnGenerate.addActionListener(listenerGenerate);

		// maak de simulate-knop en roep de methode doeSimuleer() aan als op
		// deze knop wordt gedrukt.
		JButton btnSimulate = new JButton("Simuleer");
		pButtonArea.add(btnSimulate);
		ActionListener listenerSimulate = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.doeSimuleer();
			}

		};
		btnSimulate.addActionListener(listenerSimulate);
		// maak een panel om op te tekenen
		dpTekenGebied = new TekenPanel();
		content.add(dpTekenGebied, BorderLayout.CENTER);

		MouseListener muis = new MouseListener() {
			// Als op de muis wordt geklikt, controleer dan op welke muisknop
			// (rechts of links) er wordt geklikt en roep de bijbehorende
			// methode aan. De methodes worden aangeroepen op rij en kolom,
			// welke worden berekend adhv het coördinaat waarop geklikt wordt
			public void mouseClicked(MouseEvent e) {

				double kavelsInBreedte = ((Controller) controller)
						.berekenAantalKavelsBreedte();

				double kavelsInLengte = ((Controller) controller)
						.berekenAantalKavelsLengte();

				double linkerGrensVenster = midden.getX()
						- ((kavelsInBreedte / 2.0) * Afbeeldbaar.zijde);

				double bovenGrensVenster = midden.getY()
						- ((kavelsInLengte / 2.0) * Afbeeldbaar.zijde);

				int kolom = (int) ((e.getX() - linkerGrensVenster) / Afbeeldbaar.zijde);

				int rij = (int) ((e.getY() - bovenGrensVenster) / Afbeeldbaar.zijde);

				if (SwingUtilities.isLeftMouseButton(e)) {

					controller.toggleBoswachter(rij, kolom);
					controller.afbeelden();
				} else if (SwingUtilities.isRightMouseButton(e)) {
					controller.toggleVuur(rij, kolom);
					controller.afbeelden();
				}
			}

			// Deze submethode stelt het coordinaat in waar de muis is als je
			// bgint met slepen (klikken).
			public void mousePressed(MouseEvent e) {
				start_end_coords[2] = e.getX();
				start_end_coords[3] = e.getY();
			}

			// Deze submethode stelt het coordinaat in waar de muis is als je
			// ophoudt met slepen.
			// Verplaats daarna direct het beeld.
			public void mouseReleased(MouseEvent e) {
				if (moving) {
					start_end_coords[0] = e.getX();
					start_end_coords[1] = e.getY();
					berekenVerplaatsing(start_end_coords);
					moving = false;
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		};

		dpTekenGebied.addMouseListener(muis);

		// Dit object en submethode zetten moving op true als de muis aan het
		// slepen is.
		MouseMotionListener muisMove = new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				moving = true;
			}

			public void mouseMoved(MouseEvent e) {
			}
		};

		dpTekenGebied.addMouseMotionListener(muisMove);
	}

	/**
	 * Geeft het frame een initiële grootte en maakt het zichtbaar.
	 */
	private void showUI() {
		jfMainFrame.setSize(1024, 768);
		jfMainFrame.setVisible(true);
	}

	/**
	 * De methode die het veld op de nieuwe positie afbeeldt. Dit door de
	 * verplaatsing te berekenen, dit aan het startpunt te geven en opnieuw af
	 * te beelden.
	 * 
	 * @param i
	 *            een array van integers die de coordinaten voorstellen startX,
	 *            startY, eindX, eindY.
	 */
	private void berekenVerplaatsing(Integer[] i) {
		int xVerschil = start_end_coords[2] - start_end_coords[0];
		int yVerschil = start_end_coords[3] - start_end_coords[1];

		((Controller) controller).setStartpunt(xVerschil, yVerschil);
		controller.afbeelden();
	}

	/**
	 * vertelt het tekenpanel om zijn resizecode te draaien, en laat daarna de
	 * controller het beeld opnieuw afbeelden
	 */
	public void componentResized(ComponentEvent e) {
		dpTekenGebied.resize();
		controller.afbeelden();
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

	public void componentHidden(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}
}
