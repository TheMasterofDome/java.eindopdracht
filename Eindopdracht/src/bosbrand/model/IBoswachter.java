package bosbrand.model;

public interface IBoswachter {
	/**
	 * de rij waarop deze boswachter staat
	 * @return de rij waarop deze boswachter staat
	 */
	public int getRij();
	/**
	 * de kolom waarop deze boswachter staat
	 * @return de kolom waarop deze boswachter staat
	 */
	public int getKolom();
	
	/**
	 * doof alle branden om de boswachter heen, en laat hem een actie uitvoeren om effectief
	 * volgende branden te bestrijden.
	 */
	public void update();
	
	/**
	 * stuur een bericht aan alle boswachters. U hoeft dit niet te implementeren, maar het kan
	 * handig zijn als U voor de bonus wilt gaan.
	 * @param het bericht dat verstuurd wordt
	 */
	public void stuurBericht(String bericht);	
	/**
	 * haal alle berichten op die zijn verstuurd sinds de laatste keer dat deze actie werd
	 * uitgevoerd. U hoeft dit niet te implementeren, maar het kan handig zijn als U voor de
	 * bonus wilt gaan.
	 */
	public void ontvangBerichten();
}