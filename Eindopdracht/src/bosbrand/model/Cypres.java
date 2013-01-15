package bosbrand.model;
// Cypres is een compacte klasse die de abstracte boom slechts uitbreidt
// met een kopiemethode en een constructor.

public class Cypres extends Boom implements IBoom {
	public Cypres(boolean inBrand) {
		// we nemen de 'inBrand' parameter gewoon over in het corresponderende
		// veld
		this.inBrand = inBrand;
		
		// we stellen de kansen op groei en ontvlammen zo in dat een Cypres
		// zich langzaam verspreidt en bijna niet brandt.
		pGroei = 0.01d;
		pOntvlam = 0.02d;
	}
	
	public IBoom kopie() {
		// een kopie van een Cypres is een Cypres die niet in brand staat.
		return new Cypres(Boom.NIET_IN_BRAND);
	}
	
	// geef, afhankelijk van de inbrandheid van de boom, een hoofdletter of
	// een kleine letter terug.
	public String toString() {
		return (inBrand) ? "C" : "c";
	}
	
	public void doof(){
		
	}
	
	public void steekAan(){
		
	}
}
