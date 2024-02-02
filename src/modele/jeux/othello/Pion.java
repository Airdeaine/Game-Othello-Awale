package modele.jeux.othello;

import java.util.Objects;

public class Pion {
    private final String pionNoir = "\u26AB";
    private final String pionBlanc = "\u26AA";
    private String pion;
    public Pion(int i){
        if (i == 0){
            this.pion = pionNoir;
        }
        else{
            this.pion = pionBlanc;
        }
    }

    /**
     * Retour le pion sous la forme d'une chaîne de caractère
     * @return String pion
     */
    public String getPion(){
        return pion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pionNoir, pionBlanc, pion);
    }

    /**
     * Retour le pion
     * @return String
     */
    public String toString(){
        return pion;
    }
}
