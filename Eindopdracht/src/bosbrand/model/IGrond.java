package bosbrand.model;
public interface IGrond {
  
  /**
   * (alle kavels van) grond naar de volgende toestand updaten 
   * (waarbij kavels gebruik kunnen maken van de methode omgeving,
   *  zie onder, om `zichzelf' te kunnen updaten)
   */
  public void update();
  
  /**
   * @param rij,kolom een coordinaat in de grond
   * @return tweedimensionaal array van kavels die 
   * de omgeving van de kavel op coordinaat rij,kolom vormen
   * (zie de opdracht) met de kavel zelf in het midden
   */  
  public IKavel[][] omgeving(int rij,int kolom);

}
