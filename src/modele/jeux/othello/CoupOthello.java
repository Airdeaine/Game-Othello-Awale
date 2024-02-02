package modele.jeux.othello;

import modele.jeux.interfaceJeu.Coup;

import java.util.Objects;

public class CoupOthello implements Coup {
    private int ligne;
    private char colonne;
    public CoupOthello(String coup){
        this.colonne = coup.charAt(0);
        this.ligne = Character.getNumericValue(coup.charAt(2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoupOthello coup = (CoupOthello) o;
        return ligne == coup.ligne && colonne == coup.colonne;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ligne, colonne);
    }

    /**
     * Renvoie la colonne du coup
     * @return char colonne
     */
    public char getColonne(){
        return colonne;
    }

    /**
     * Renvoie la ligne du coup
     * @return int ligne
     */
    public int getLigne(){
        return ligne;
    }

    public String toString() {
        return colonne + " " + ligne;
    }
}
