package modele.jeux.othello;

import modele.jeux.interfaceJeu.Coup;
import modele.jeux.interfaceJeu.Joueur;
import modele.jeux.othello.CoupOthello;
import modele.jeux.othello.Pion;

public class JoueurOthello implements Joueur {
    private CoupOthello coupPrecedent;
    private String nom;
    private Pion pion;
    public int nbPartieGagne;
    private static int initJoueur = 0;
    public JoueurOthello(String nom){
        this.nom = nom;
        this.nbPartieGagne = 0;
        this.pion = new Pion(initJoueur);
        this.initJoueur++;
    }

    /**
     * Permet de récupérer le pion attribut au joueur
     * @return String pion
     */
    public String recupererPionJoueur(){
        return pion.getPion();
    }


    /**
     * Retourne le nom du joueur
     * @return
     */
    public String getNom(){
        return nom;
    }
    public void setCoupPrecedent(Coup coup){
        this.coupPrecedent = (CoupOthello) coup;
    }
    public CoupOthello getCoupPrecedent(){
        return this.coupPrecedent;
    }

    /**
     * Retourne les informations du joueur
     * @return String
     */
    public String toString (){
        return nom + "a gagné " + nbPartieGagne + " partie(s)";
    }

    public void partieGagner(){
        this.nbPartieGagne++;
    }
}
