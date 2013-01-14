
public class Bosbrand {
	public static void main(String[] args) {
		// maak een nieuwe 'grond' aan, met als parameters de argumenten van deze main
		IGrond grond = new Grond(args);
		
		// doe 400 updates
		for (int n = 1; n <= 400; n++) {
			grond.update();
			
			// en print de toestand af als hij direct deelbaar is door 10
			if (n % 10 == 0) System.out.println(grond);
		}
	}
}