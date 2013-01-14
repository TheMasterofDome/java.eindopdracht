package bosbrand.model;
// BraamStruik is een compacte klasse die de abstracte boom slechts uitbreidt
// met een kopiemethode en een constructor.

public class AppelBoom extends Boom implements IBoom {
	public AppelBoom(boolean inBrand) {
		// we nemen de 'inBrand' parameter gewoon over in het corresponderende
		// veld
		this.inBrand = inBrand;
		
		// we stellen de kansen op groei en ontvlammen zo in dat een BraamStruik
		// zich snel verspreidt en goed brandt.
		pGroei = 0.07d;
		pOntvlam = 0.6d;
	}
	
	public IBoom kopie() {
		// een kopie van een BraamStruik is een BraamStruik die niet in brand staat.
		return new AppelBoom(Boom.NIET_IN_BRAND);
	}
	
	public String toString() {
		// geef, afhankelijk van de inbrandheid van de boom, een hoofdletter of
		// een kleine letter terug.
		return (inBrand) ? "A" : "a";
	}
}