// BONUS: Je kunt inzoomen met het muiswiel. N.b.: hij pakt niet altijd de richting van het scrollwiel, dus soms moet je wat extra klooien.
// BONUS: Boswachter AI: De boswachters gaan op de brand af die ze het meest efficient kunnen blussen.
// bovendien is het bramentijd: als de boswachter niets te doen heeft en op een bramenkavel staat eet hij de brmaen op. Hierdoor wordt hij 
// eerst sneller (energie), maar als hij meer eet wordt hij langzamer. Als hij teveel eet wordt hij misselijk en moet hij overgeven, 
// en moet vervolgens even bijkomen. Daarna blust hij weer op z'n normale tempo.

import java.util.Random;

import bosbrand.controller.*;

public class Bosbrand {

	private static String[] s;

	public static void main(String[] args) {
		// Start het programma

		// Als er geen argumenten zijn, random grond maken en daarmee verder
		// gaan.
		// Anders de input testen en als die juist is daarmee verder gaan.
		if (args.length == 0) {
			s = maakRandomGrond();
		} else {
			testInput(args);
			s = args;
		}
		//Controller object aanmaken, waarmee het programma gestart wordt.
		@SuppressWarnings("unused")
		IController control = new Controller(s);
	}

	/**
	 * 
	 * @param param
	 *            : De String[] met daarin de input.
	 */
	private static void testInput(String[] param) {
		boolean inputJuist = true;
		String melding = "";
		// Rijtje met acceptabele karakters.
		String juisteInput = "ABCabc.";

		// Testen of 'ie rechthoekig is (als er parameters zijn) door voor alle
		// rijen, de lengte van de rij met de volgende te vergelijken.
		if (inputJuist) {

			// Declareer variabele lengte. Waarde is de lengte van een argument,
			// bedoeld om de lengte van de argumenten te vergelijken.
			int lengte = param[0].length();

			/*
			 * Vergelijk lengte met lengte van het volgende argument. Print
			 * foutmelding en sluit programma af zodra lengte != lengte van het
			 * i-de argument.
			 */
			for (int i = 0; i < param.length; i++) {

				if (lengte != param[i].length()) {
					melding = "De input levert geen (volledige) rechthoek op.";
				}


			}
			// Als input rechthoekigis, testen of de juiste karakters worden
			// gebruikt.
			// Voor elk argument test hij of elk karakter uit dat argument
			// voorkomt in het rijtje juisteInput.
			// Als dat niet zo is dan geeft indexOf() -1, dus dan inputJuist =
			// false.
			if (inputJuist) {
				for (int i = 0; i < param.length; i++) {
					for (int c = 0; c < param[i].length(); c++) {
						if (juisteInput.indexOf(param[i].charAt(c)) < 0) {
							inputJuist = false;
							melding = "Onjuiste karakters gebruikt.";
						}
					}
				}
			}

			// Testen of de invoer juist is, zo ja groen licht geven, anders
			// stoppen met melding.
			if (!inputJuist) {
				System.out.printf("De invoer was onjuist met foutmelding: %s",
						melding);
				System.exit(0);

			}
		}
	}

	/**
	 * Methode om een random grond te maken in de vorm van een String[].
	 * 
	 * @return de String[] met de random grond.
	 */
	private static String[] maakRandomGrond() {
		// Random nummer generator maken.
		Random generator = new Random();
		// Deze karakters mogen gebruikt worden.
		String chars = "ABCabc.";
		// Als er geen argumenten zijn meegegeven maken we een willekeurig bos
		// in de vorm van een String array.
		// Ja, dat is een beetje dubbelop (String[] maken en straks weer
		// ontleden), maar anders moest ik teveel herschijven.
		// Willekeurige lengte en breedte van het bos instellen. Maximaal 50,
		// anders wordt het te gek.
		int lengte = maakLengte();
		int breedte = maakBreedte();

		// Nieuwe String array maken en iedere rij (lengte) opvullen met een
		// String.
		// Deze string wordt gemaakt door voor breedte een karakter van een
		// willekeurige positie uit chars er bij te doen.
		String[] rand = new String[lengte];
		for (int r = 0; r < rand.length; r++) {
			StringBuilder sb = new StringBuilder();
			for (int c = 0; c < breedte; c++) {
				sb.append(chars.charAt(generator.nextInt(7)));
			}
			rand[r] = String.format("%s", sb);
		}
		return rand;
	}

	/**
	 * Methode die een random lengte geeft tussen 0 en 50
	 * 
	 * @return de willekeurige lengte
	 */
	private static int maakLengte() {
		Random generator = new Random();
		int lengte = generator.nextInt(50);
		return (lengte > 0) ? lengte : maakLengte();
	}

	/**
	 * Methode die een random lengte geeft tussen 0 en 50
	 * 
	 * @return de willekeurige lengte
	 */
	private static int maakBreedte() {
		Random generator = new Random();
		int breedte = generator.nextInt(50);
		if (breedte > 0) {
			return breedte;
		} else
			maakLengte();
		return breedte;
	}

}
