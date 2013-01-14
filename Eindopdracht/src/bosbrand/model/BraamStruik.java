package bosbrand.model;
//Another hoi
// BraamStruik is een compacte klasse die de abstracte boom slechts uitbreidt
// met een kopiemethode en een constructor.

public class BraamStruik extends Boom implements IBoom {
	public BraamStruik(boolean inBrand) {
		// we nemen de 'inBrand' parameter gewoon over in het corresponderende
		// veld
		this.inBrand = inBrand;
		
		// we stellen de kansen op groei en ontvlammen zo in dat een BraamStruik
		// zich snel verspreidt en goed brandt.
		pGroei = 0.09d;
		pOntvlam = 0.8d;
	}
	
	public IBoom kopie() {
		// een kopie van een BraamStruik is een BraamStruik die niet in brand staat.
		return new BraamStruik(Boom.NIET_IN_BRAND);
	}
	
	public String toString() {
		// geef, afhankelijk van de inbrandheid van de boom, een hoofdletter of
		// een kleine letter terug.
		return (inBrand) ? "B" : "b";
	}
}
