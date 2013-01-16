package bosbrand.controller;

import java.awt.event.*;
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
	
	/**
	 * De controller maakt ene grond object aan en initialiseerd de wereld.
	 * @param wereld de string met de input, of de random grond.
	 * De Timer staat in de constructor zodat we hem door de hele Controller kunnen starten/stoppen.
	 */
	public Controller(String[] wereld) {
		timerRunning = false;
		grond = new Grond();
		grond.initialiseer(wereld);
		listener = new ActionListener(){
			public void actionPerformed(ActionEvent event){
				doen();
			}
		};
		displayTimer = new Timer(100, listener);
		view = new View(this);
	}

	/**
	 * Laat het model weer vanaf het begin beginnen:
	 * Kavels terug naar origineel, boswachters weg, update-counter weer naar 0.
	 * Pauzeer de timer tijdens de reset om te voorkomen dat er ongelukken gebeuren als nog niet het hele veld gereset is
	 * maar het veld wel weer geupdate wordt, bijvoorbeeld.
	 */
	public void doeReset() {
		//Pauzeer timer eventueel.
		displayTimer.stop();
		
		//Kavels terug naar origineel en boswachters weg.
		grond.reset();
		
		//Timer weer starten --> Simulatie weer doorlaten gaan.
		displayTimer.start();
	}
	
	/**
	 * Methode die uitvoert wat er moet gebeuren als de Timer een Action Event gooit.
	 */
	private void doen() {
		grond.update();
		//afbeelden();
		// TODO even checken wat ie nog meer moet doen. (Veld updaten bijvoobeeld?
	}
	
	/**
	 * Methode voor de 'Simuleer'-knop.
	 */
	public void doeSimuleer() {
		if (!timerRunning) {
			displayTimer.start();
			timerRunning = true;
		}
		else if (timerRunning) {
			displayTimer.stop();
			timerRunning = false;
		}
	}

	/**
	 * Maak van ieder kavel een afbeeeldbaar object en stop die objecten in een Array.
	 */
	public void afbeelden() {
		IKavel[][] kavels = grond.getKavels();
		ArrayList<IAfbeeldbaar> afb = new ArrayList<IAfbeeldbaar>();
		
		//Haal de coordinaten uit het kavel en de kleur. Dus of ie in brand staat of leeg is.
		//De randgevallen skipt ie.
		for (int r=0;r<kavels.length;r++) {
			for (int c=0;c<kavels[r].length;c++) {
				System.out.println("In brand? "+kavels[r][c].voortBranden());
				if (kavels[r][c] instanceof LeegKavel) {
					IAfbeeldbaar af = new Afbeeldbaar(r , c, 'B');
					afb.add(af);
				}
				else {
					if (kavels[r][c].voortBranden()) {
						IAfbeeldbaar af = new Afbeeldbaar(r , c, 'R');
						afb.add(af);
					}
					else if (!(kavels[r][c].voortBranden())){
						IAfbeeldbaar af = new Afbeeldbaar(r , c, 'G');
						afb.add(af);
					}
				}
			}
		}
		IAfbeeldbaar[] afbeeldData = afb.toArray(new Afbeeldbaar[afb.size()]);
		//view.afbeelden(afbeeldData);
		IAfbeeldbaar[] afbeeldTest = new Afbeeldbaar[1];
		afbeeldTest[0] = new Afbeeldbaar(10,10,'G');
		view.afbeelden(afbeeldTest);
	}

	public void toggleBoswachter(int rij, int kolom) {
		// TODO Bepalen waar we Event handling gaan doen.
		// TODO aan de hand van de positie van de muis lokatie bepalen en toggleBoswachter in Grond aanroepen op die positie.

	}

	public void toggleVuur(int rij, int kolom) {
		// TODO Zie toggleBoswachter()

	}

}
