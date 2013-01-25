package bosbrand.view;

import bosbrand.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View implements IView, ComponentListener {
	Point midden;
	private JFrame jfMainFrame;
	private TekenPanel dpTekenGebied;
	private IController controller;
	private double zoom;

	/**
	 * Constructor die het frame en de controls erop aanmaakt. Ook stelt hij het
	 * midden in en initialiseert hij de rest van de variabelen.
	 * 
	 * @param controller
	 *            die deze view en het bijbehorende model bestuurt
	 */
	public View(IController controller) {
		zoom = 1.0;
		this.controller = controller;
		midden = new Point((1024 - 102) / 2, (768 / 2));

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
	public void setMidden(Point nieuwMid) {
		midden = new Point((int) nieuwMid.getX(), (int) nieuwMid.getY());
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

		/*
		 * JButton btnStep = new JButton("Step"); pButtonArea.add(btnStep);
		 * ActionListener listenerStep = new ActionListener() { public void
		 * actionPerformed(ActionEvent event) { ((Controller)
		 * controller).doen(); }
		 * 
		 * }; btnSimulate.addActionListener(listenerStep);
		 */

		// maak een panel om op te tekenen
		dpTekenGebied = new TekenPanel();
		content.add(dpTekenGebied, BorderLayout.CENTER);

		MouseListener muis = new MouseListener() {
			// Als op de muis wordt geklikt, controleer dan op welke muisknop
			// (rechts of links) er wordt geklikt en roep de bijbehorende
			// methode aan. De methodes worden aangeroepen op rij en kolom,
			// welke worden berekend adhv het coördinaat waarop geklikt wordt
			public void mouseClicked(MouseEvent e) {
				// het aantal kavels in de breedte en lengte hebben we nodig om
				// te berekenen hoe lang de grond is vanaf het midden. Vanaf het
				// midden, omdat de kavels vanuit het midden worden afgebeeld.
				double kavelsInBreedte = ((Controller) controller)
						.berekenAantalKavelsBreedte();

				double kavelsInLengte = ((Controller) controller)
						.berekenAantalKavelsLengte();

				// aan de hand van het aantal kavels in lengte/breedte en de
				// lengte en breedte van een kavel (beiden ) kunnen de
				// coördinaten van de linker-, rechter-, onder- en bovengrens
				// worden berekend.
				double linkerGrensVenster = midden.getX()
						- ((kavelsInBreedte / 2.0) * ((Controller) controller)
								.getZijdeGrootte());

				double rechterGrensVenster = midden.getX()
						+ ((kavelsInBreedte / 2.0) * ((Controller) controller)
								.getZijdeGrootte());

				double bovenGrensVenster = midden.getY()
						- ((kavelsInLengte / 2.0) * ((Controller) controller)
								.getZijdeGrootte());

				double onderGrensVenster = midden.getY()
						+ ((kavelsInLengte / 2.0) * ((Controller) controller)
								.getZijdeGrootte());

				// aan de hand van deze coördinaten wordt getest of de muisklik
				// wel binnen de getekende grond valt. Als dit niet het geval
				// is, dan wordt de huidige grond opnieuw afgebeeld; er
				// verandert dus niets.

				if (e.getX() < linkerGrensVenster
						|| e.getX() > rechterGrensVenster
						|| e.getY() < bovenGrensVenster
						|| e.getY() > onderGrensVenster) {
					controller.afbeelden();
				}

				else {

					// Als blijkt dat er wel binnen het veld geklikt is, dan
					// worden de rij en kolom berekend van het kavel waarop is
					// geklikt.
					int kolom = (int) ((e.getX() - ((Controller) controller)
							.getStartpunt().getX()) / ((Controller) controller)
							.getZijdeGrootte());
					int rij = (int) ((e.getY() - ((Controller) controller)
							.getStartpunt().getY()) / ((Controller) controller)
							.getZijdeGrootte());

					// vervolgens wordt gecontroleerd met welke muisknop er op
					// deze kavel is geklikt en wordt afhankelijk hiervan
					// toggleBoswachter of toggleVuur aangeroepen met de juiste
					// rij en kolom
					if (SwingUtilities.isLeftMouseButton(e)) {

						controller.toggleBoswachter(rij, kolom);
						controller.afbeelden();
					} else if (SwingUtilities.isRightMouseButton(e)) {
						controller.toggleVuur(rij, kolom);
						controller.afbeelden();
					}
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
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
				setMidden(new Point(e.getX(), e.getY()));
				controller.afbeelden();
			}

			public void mouseMoved(MouseEvent e) {
			}
		};

		dpTekenGebied.addMouseMotionListener(muisMove);

		// maak een MouseWheelListener aan om ervoor te zorgen dat er kan worden
		// in- en uitgezoomd mbv de scrolwiel. Voor het zoomen wordt gebruik
		// gemaakt van de methode setZoom. Na het zoomen wordt de nieuwe grond
		// afgebeeld.
		MouseWheelListener wheel = new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				int richting = e.getWheelRotation();
				// als richting kleiner is dan nul, betekent dat er 'van de
				// gebruiker af' wordt gescrolld. Dan moet er dus ingezoomd
				// worden.
				if (richting < 0) {
					setZoom(0.1);
				}

				// als richting groter is dan nul, wordt er 'naar de gebruiker
				// toe' gezoomd, en dus moet er worden uitgezoomd.
				else if (richting > 0) {
					setZoom(-0.1);
				}

				controller.afbeelden();
			}
		};

		dpTekenGebied.addMouseWheelListener(wheel);
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

	public void setZoom(double z) {
		zoom = zoom + z;
		((Controller) controller)
				.setZijdeGrootte((int) (((Controller) controller)
						.getZijdeGrootte() * zoom));
	}

	/**
	 * zoom als een ratio ten opzichte van normaal (dus 1.0 is normaal, 2.0 is
	 * twee keer zo groot of twee keer zo klein, afhankelijk van de
	 * implementatie).
	 * 
	 * @return zoomniveau als ratio ten opzichte van normaal
	 */
	public double getZoom() {
		return zoom;
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