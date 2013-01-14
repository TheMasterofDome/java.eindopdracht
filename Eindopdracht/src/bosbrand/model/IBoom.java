package bosbrand.model;

public interface IBoom extends IKavel {
	
	/**
	 * @return nieuwe boom (die niet in brand staat)
	 * van hetzelfde soort als deze boom
	 */
	public IBoom kopie();  
}