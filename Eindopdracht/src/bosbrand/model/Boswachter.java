package bosbrand.model;

public class Boswachter implements IBoswachter {

    int kolom;
    int rij;
    IBosbrandModel grond;
    IKavel[][] kavels;
    int targetRij;
    int targetKolom;

    Boswachter(IBosbrandModel grond, int rij, int kolom) {
        this.rij = rij;
        this.kolom = kolom;
        this.grond = grond;
    }

    /**
     * de rij waarop deze boswachter staat
     *
     * @return de rij waarop deze boswachter staat
     */
    public int getRij() {
        return rij;
    }

    /**
     * de kolom waarop deze boswachter staat
     *
     * @return de kolom waarop deze boswachter staat
     */
    public int getKolom() {
        return kolom;
    }

    /**
     * doof alle branden om de boswachter heen, en laat hem een actie uitvoeren
     * om effectief volgende branden te bestrijden.
     */
    public void update() {

        // het aantal gebluste bomen wordt opgevraagd mbv de methode
        // aantalGeblusteBomen()
        int aantalGeblusteBomen = aantalGeblusteBomen();

        // als de boswachter niet geblust heeft (en het aantal gebluste bomen
        // dus nul is), zet de boswachter 1 stap in de richting van de
        // dichtstbijzijnde in brand staande boom

        if (aantalGeblusteBomen == 0) {

            findClosestTree();

            rij = veranderRij();
            kolom = veranderKolom();

        }

    }

    // in deze methode worden bomen in de omgeving geblust.
    // tevens wordt het aantal gebluste bomen bijgehouden.
    private int aantalGeblusteBomen() {

        // het aantal gebluste bomen wordt eerst op nul gezet.
        int aantalGeblusteBomen = 0;
        // maak een nieuw IKavel[][] object aan, met de waarde van de omgeving
        // van de kavel waar de boswachter op staat.

        IKavel[][] omgevingBoswachter = grond.omgeving(rij, kolom);
        // loop over deze omgeving, op zoek naar brandende bomen
        for (int i = 0; i < omgevingBoswachter.length; i++) {
            for (int j = 0; j < omgevingBoswachter[i].length; j++) {
                // als er een brandende boom gevonden wordt, laat de boom dan
                // uitdoven dmv het aanroepen van de methode doof();
                // houd ondertussen ook het aantal brandende bomen bij.

                if (omgevingBoswachter[i][j].voortBranden()) {
                    omgevingBoswachter[i][j].doof();
                    aantalGeblusteBomen++;

                }

            }
        }

        return aantalGeblusteBomen;
    }
    
    private void findClosestTree() {

        int boomRij;
        int boomKolom;
        // bepaal de maximumafstand die een boswachter tot een brandende
        // boom kan hebben

        int maxAantalStappen = berekenAantalStappen(grond.getKavels().length,
                grond.getKavels()[0].length);

        for (boomRij = 0; boomRij < kavels.length; boomRij++) {
            for (boomKolom = 0; boomKolom < kavels[rij].length; boomKolom++) {
                if (kavels[boomRij][boomKolom].voortBranden()) {
                    // bereken het aantal stappen dat een boswachter
                    // verwijderd is van de brandende boom

                    int verschilRij = Math.abs(rij - boomRij);
                    int verschilKolom = Math.abs(kolom - boomKolom);
                    int aantalStappen = berekenAantalStappen(verschilRij,
                            verschilKolom);

                    // controleer of de boom in minder stappen te bereiken
                    // is dan de vorige boom

                    if (aantalStappen < maxAantalStappen) {
                        targetRij = boomRij;
                        targetKolom = boomKolom;
                    }

                }
            }
        }

    }
    
    // deze methode bepaalt de afstand tussen een
    // boswachter en een brandende boom
    private int berekenAantalStappen(int lengte, int breedte) {

        int AantalStappen = Math.max(lengte, breedte) - 1;
        return AantalStappen;
    }

    // bepaal wat de nieuwe rij is voor de boswachter
    private int veranderRij() {
        int rijVerschil = berekenRijVerschil();

        if (rijVerschil < 0) {
            rij--;
        }
        if (rijVerschil > 0) {
            rij++;
        }
        return rij;
    }

    // bepaal wat de nieuwe kolom is voor de boswachter
    private int veranderKolom() {

        int kolomVerschil = berekenKolomVerschil();
        if (kolomVerschil < 0) {
            kolom--;
        }

        if (kolomVerschil > 0) {
            kolom++;
        }
        return kolom;
    }

    

    // deze methode bepaalt de afstand in rijen tot de dichtstbijzijnde
    // brandende boom
    private int berekenRijVerschil() {
        int rijVerschil = rij - targetRij;
        return rijVerschil;
    }

    // deze methode bepaalt de afstand in kolommen tot de dichtstbijzijnde
    // brandende boom
    private int berekenKolomVerschil() {
        int kolomVerschil = kolom - targetKolom;
        return kolomVerschil;
    }



    /**
     * stuur een bericht aan alle boswachters. U hoeft dit niet te
     * implementeren, maar het kan handig zijn als U voor de bonus wilt gaan.
     *
     * @param het
     *            bericht dat verstuurd wordt
     */
    public void stuurBericht(String bericht) {
    }

    /**
     * haal alle berichten op die zijn verstuurd sinds de laatste keer dat deze
     * actie werd uitgevoerd. U hoeft dit niet te implementeren, maar het kan
     * handig zijn als U voor de bonus wilt gaan.
     */
    public void ontvangBerichten() {
    }

}