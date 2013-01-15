package bosbrand.controller;

import java.util.*;

import bosbrand.view.*;
import bosbrand.model.*;

public class Controller implements IController {

	private IBosbrandModel grond;
	
	/**
	 * De controller maakt ene grond object aan en initialiseerd de wereld.
	 * @param wereld de string met de input, of de random grond.
	 */
	public Controller(String[] wereld) {
		grond = new Grond();
		grond.initialiseer(wereld);
		afbeelden();
	}

	public void doeReset() {
		// TODO Auto-generated method stub

	}

	public void doeSimuleer() {
		// TODO Auto-generated method stub

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
				if (kavels[r][c] instanceof LeegKavel) {
					IAfbeeldbaar af = new Afbeeldbaar(r , c, 'B');
					afb.add(af);
				}
				else {
					if (kavels[r][c].voortBranden()) {
						IAfbeeldbaar af = new Afbeeldbaar(r , c, 'R');
						afb.add(af);
					}
					else if (!kavels[r][c].voortBranden()){
						IAfbeeldbaar af = new Afbeeldbaar(r , c, 'G');
						afb.add(af);
					}
				}
			}
		}
		IAfbeeldbaar[] afbeeldData = afb.toArray(new Afbeeldbaar[afb.size()]);
		//afbeelden in view aanroepen.
		System.out.println(afbeeldData[0].getZijde());
	}

	public void toggleBoswachter(int rij, int kolom) {
		// TODO Auto-generated method stub

	}

	public void toggleVuur(int rij, int kolom) {
		// TODO Auto-generated method stub

	}

}
