package bosbrand.model;
public interface IKavel {
  
  /**
   * volgende toestand (zie de opdracht) geven voor een kavel 
   * @param grond waarin zich deze kavel bevindt
   * @param rij,kolom coordinaten van deze kavel 
   * @return nieuw kavel, afhankelijk van deze kavel en diens omgeving in de grond
   * (hint: gebruik de methode omgeving)
   */
  public IKavel volgendeToestand(IGrond grond,int rij,int kolom);

  /**
   * @return geeft true desda deze kavel in brand staat 
   */
  public boolean voortBranden();
  
  /**
   * @return als deze methode voor een kavel aangeroepen wordt
   * en deze kavel is begroeid met een soort boom en 
   * en de boom staat niet in brand, dan geeft dit met
   * een kans pGroei een nieuwe boom van hetzelfde soort terug
   * in alle andere gevallen null
   */
  public IBoom voortBomen();

}
