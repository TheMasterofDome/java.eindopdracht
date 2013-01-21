package bosbrand.controller;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;

import bosbrand.view.*;
import bosbrand.model.*;

public class Controller implements IController {

	private IBosbrandModel grond;
	private IView view;
	private ActionListener listener;
	private Timer displayTimer;
	private boolean timerRunning;
	private IAfbeeldbaar af;
	private Point startpunt;
	//private Point midden;

	/**
	 * De controller maakt ene grond object aan en initialiseerd de wereld.
	 * 
	 * @param wereld
	 *            de string met de input, of de random grond. De Timer staat in
	 *            de constructor zodat we hem door de hele Controller kunnen
	 *            starten/stoppen.
	 */
	public Controller(String[] wereld) {
		timerRunning = false;
		af = new Afbeeldbaar(0, 0, ' ');
		grond = new Grond();
		grond.initialiseer(wereld);
		listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				doen();
			}
		};
		displayTimer = new Timer(100, listener);
		view = new View(this);
		setStartpunt();
		startpunt = new Point(getStartpunt());
	}

	/**
	 * Laat het model weer vanaf het begin beginnen:
	 * Kavels terug naar origineel, boswachters weg, update-counter weer naar 0.
	 * We stoppen de timer omdat ons dat het meest wenselijke lijkt als je op reset drukt.
	 */
	public void doeReset() {
		// Kavels terug naar origineel en boswachters weg, en opnieuw afbeelden.
		displayTimer.stop();
		view.setMidden(new Point(((1024-102)/2), (768/2)));
		grond.reset();
		afbeelden();
	}

	/**
	 * Methode die uitvoert wat er moet gebeuren als de Timer een Action Event
	 * gooit.
	 */
	public void doen() {
		// De grond naar de volgende toestand updaten volgens dew regels van
		// Opdracht 3.
		grond.update();

		// Alle boswachters hun ding laten doen
		IBoswachter[] boswachters = grond.getBoswachters();
		for (IBoswachter bw : boswachters) {
			bw.update();
		}

		// De nieuwe grond afbeelden
		afbeelden();
	}

	/**
	 * Methode voor de 'Simuleer'-knop.
	 */
	public void doeSimuleer() {
		if (!timerRunning) {
			displayTimer.start();
			timerRunning = true;
		} else if (timerRunning) {
			displayTimer.stop();
			timerRunning = false;
		}
	}

	/**
	 * Maak van ieder kavel en boswachter een afbeeeldbaar object en stop die
	 * objecten in een Array.
	 */
	public void afbeelden() {
		setStartpunt();
		IKavel[][] kavels = grond.getKavels();
		IBoswachter[][] boswachters = ((Grond) grond).getBoswachterPos();
		ArrayList<IAfbeeldbaar> afb = new ArrayList<IAfbeeldbaar>();
		//Hij pakt de zijde van het vierkant nog niet
		int zijde = (int) (af.getZijde() * view.getZoom());
		af = new Afbeeldbaar(0,0,' ', zijde);
		System.out.println(zijde);

		// Haal de coordinaten uit het kavel en de kleur. Dus of ie in brand
		// staat of leeg is.
		// De randgevallen skipt ie.
		// Opties voor kleuren zijn Rood, Groen en B voor standaard.
		// Van linksboven naar rechtsonder de kavels omzetten en de bijbehorende
		// coordinaten meegeven.
		int y = (int) getStartpunt().getY();
		for (int r = 0; r < kavels.length; r++) {
			int x = (int) getStartpunt().getX();
			for (int c = 0; c < kavels[r].length; c++) {
				if (kavels[r][c] instanceof LeegKavel) {
					af = new Afbeeldbaar(x, y, 'L', zijde);
				}

				if (kavels[r][c] instanceof AppelBoom) {
					if (kavels[r][c].voortBranden()) {
						af = new Afbeeldbaar(x, y, 'A', zijde);
					} else {
						af = new Afbeeldbaar(x, y, 'a', zijde);
					}
				}

				if (kavels[r][c] instanceof BraamStruik) {
					if (kavels[r][c].voortBranden()) {
						af = new Afbeeldbaar(x, y, 'B', zijde);
					}
					else {
						af = new Afbeeldbaar(x, y, 'b', zijde);
					}
				}

				if (kavels[r][c] instanceof Cypres) {
					if (kavels[r][c].voortBranden()) {
						af = new Afbeeldbaar(x, y, 'C', zijde);
					}
					else {
						af = new Afbeeldbaar(x, y, 'c', zijde);
					}
				}
				afb.add(af);
				x = x + zijde;
			}
			y = y + zijde;
		}

		//We bepalen de positie van de boswachters op dezelfde manier als de kavels.
		//We wilden dit eerst mbv de getBoswachters() methode doen, maar dit bleek na een boel rekenwerk
		// en hoofdpijn onmogelijk, terwijl dit in 10 seconden vlekkeloos werkte.		
		y = (int) getStartpunt().getY();
		for (int r = 0; r < boswachters.length; r++) {
			int x = (int) getStartpunt().getX();
			for (int c = 0; c < boswachters[r].length; c++) {
				if (boswachters[r][c] instanceof Boswachter) {
					af = new Afbeeldbaar(x + (af.getZijde()/4), y + (af.getZijde()/4), 'P', zijde);
				}
				afb.add(af);
				x = x + zijde;
			}
			y = y + zijde;
		}

		IAfbeeldbaar[] afbeeldData = afb.toArray(new Afbeeldbaar[afb.size()]);
		// view.afbeelden(afbeeldData);
		view.afbeelden(afbeeldData);
	}

	/**
	 * Deze methode berekent op welk punt moet worden begonnen met afdrukken om
	 * de grond mooi in het midden te hebben. In de praktijk werkt dit nog niet
	 * in verticale richting door een onbekende fout in een van de tekenmethodes
	 * (die niet van ons zijn).
	 * 
	 * Midden verandert natuurlijk als je de grond versleept.
	 */
	public void setStartpunt() {
		Point midden = view.getMidden();
		// De afstand van het midden van kavels[][] tot de grens van grond.
		double xOffsetKavel = (grond.getKavels()[0].length / 2.0)
				* af.getZijde();
		double yOffsetKavel = (grond.getKavels().length / 2.0) * af.getZijde();

		// Het nieuwe startpunt
		Double startX = midden.x - xOffsetKavel;
		Double startY = midden.y - yOffsetKavel;
		
		startpunt = new Point (startX.intValue(), startY.intValue());
	}

	public Point getStartpunt() {
		return startpunt;
	}

	/**
	 * Deze methode doet niets anders dan de toggleBoswachtermethode in Grond
	 * aanroepen.
	 * 
	 * @param rij
	 *            de rij van het kavel, die hij meekrijgt uit View.
	 * @param kolom
	 *            de kolom van het kavel, die hij meekrijgt van View
	 */
	public void toggleBoswachter(int rij, int kolom) {
		grond.toggleBoswachter(rij, kolom);
	}

	/**
	 * Deze methode doet niets anders dan de toggleVuurmethode in Grond
	 * aanroepen.
	 * 
	 * @param rij
	 *            de rij van het kavel, die hij meekrijgt uit View.
	 * @param kolom
	 *            de kolom van het kavel, die hij meekrijgt van View
	 */
	public void toggleVuur(int rij, int kolom) {
		grond.toggleVuur(rij, kolom);
	}

	public int berekenAantalKavelsBreedte() {
		int aantalKavels = grond.getKavels()[0].length;
		return aantalKavels;

	}

	public int berekenAantalKavelsLengte() {
		int aantalKavels = grond.getKavels().length;
		return aantalKavels;
	}

}
